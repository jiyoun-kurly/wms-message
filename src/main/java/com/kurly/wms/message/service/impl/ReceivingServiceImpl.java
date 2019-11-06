package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.client.RcvTransaction;
import com.kurly.wms.message.domain.WmsReceiving;
import com.kurly.wms.message.domain.WmsReceivingIf;
import com.kurly.wms.message.repository.ReceivingMapper;
import com.kurly.wms.message.service.MessagingService;
import com.kurly.wms.message.service.ReceivingService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service("receivingService")
public class ReceivingServiceImpl implements ReceivingService {

    @NonNull
    private final ReceivingMapper receivingMapper;

    @NonNull
    private final MessagingService messagingService;

    @Value("${slack.info.receivingIfSlackChannel}")
    private String receivingIfChannel;

    /**
     * RMS에서 전송된 입고데이터 증 이미 Inteface 테이블에 저장된 중복데이터 제거
     * @param transactionList
     * @return List<RcvTransaction>
     */
    @Override
    public List<RcvTransaction> removeDuplicatedTransaction(List<RcvTransaction> transactionList) {
        List<Long> receivingNoList = transactionList.stream()
                .map(RcvTransaction::getTransactionId).collect(Collectors.toList());
        List<WmsReceivingIf> dupReceivingList = receivingMapper.findByTransactionIdIn(receivingNoList);

        if (dupReceivingList.size() > 0) {
            log.info ( "[Duplicate receiving count]" + dupReceivingList.size ());
            sendDupReceivingSlack(dupReceivingList);
        }
        return getTransactionNotContainIds(transactionList, dupReceivingList);
    }

    /**
     * RMS에서 전송된 입고데이터 Inteface 테이블에 저장
     * @param transactionList
     * @return List<WmsReceivingIf>
     */
    @Override
    public List<WmsReceivingIf> insertReceivingIf(List<RcvTransaction> transactionList) {
        // 인터페이스 저장
        List<WmsReceivingIf> wmsReceivingIfList = WmsReceivingIf.toWmsReceivingIfList(transactionList);
        List<WmsReceivingIf> insertSuccessList = new ArrayList<>();
        StringBuilder failMessage = new StringBuilder();
        for (WmsReceivingIf receivingIf : wmsReceivingIfList) {
            try {
                receivingIf.setPurchaseOrderSubCode();
                receivingMapper.insert(receivingIf);
                insertSuccessList.add(receivingIf);
            } catch (Exception e){
                log.error ("##### InsertReceivingIf Exception :" );
                e.printStackTrace();
                failMessage.append((receivingIf.getErrorMessage(e.getMessage())));
            }
        }

        if (failMessage.length() > 0) {
            log.info("[Fail to insert WmsReceivingIf Count]" + (transactionList.size() - insertSuccessList.size()));
            sendFailReceivingSlack(getMesagesWithTemplate(failMessage), "insertReceivingIf");
        }
        return insertSuccessList;
    }

    /**
     * Interface에 저장된 입고데이터 중, WMS에 정상적으로 추가되지 않은 건 일괄 처리
     */
    @Override
    public void manualSaveReceiving() {
        List<WmsReceivingIf> receivingIfList = receivingMapper.findNotCompletedIfList();
        if(receivingIfList.size() > 0) {
            insertReceiving(receivingIfList);
        }
    }

    /**
     * Interface에 저장된 입고데이터 RECDH, RECDI 저장
     * @param receivingIfList
     */
    @Override
    public void insertReceiving(List<WmsReceivingIf> receivingIfList) {

        // dh 그룹단위 롤백 필요할 수 있음, 현재는 No Transaction
        StringBuilder failMessage = new StringBuilder();
        List<Long> successNoList = new ArrayList<Long>();
        Map<String, String> poCodeRECVKYMap = new HashMap<>();

        List<WmsReceiving> receivingList = getReceiving(receivingIfList);

        int itemCnt = 0;
        for (WmsReceiving receiving : receivingList) {
            try {
                // refdky = purchaseOrderCode
                // 발주코드가 중복되지 않도록 recdh 추가하기 위해 발주코드/recvky map 조회

                if (StringUtils.isEmpty(poCodeRECVKYMap.get(receiving.getRefdky()))) {
                    saveRecdh(receiving);
                    poCodeRECVKYMap.put(receiving.getRefdky(), receiving.getRecvky());
                    itemCnt = 0;
                }
                itemCnt += 10;
                receiving.setRecvky(poCodeRECVKYMap.get(receiving.getRefdky()));
                receiving.setRecvitFromItemCnt(itemCnt);
                receivingMapper.saveFromIfToRecdi(receiving);
                successNoList.add(Long.parseLong(receiving.getAwmsno()));
            } catch (Exception e){
                log.error ("##### InsertReceiving Exception :" );
                e.printStackTrace();
                failMessage.append((receiving.getErrorMessage(e.getMessage())));
            }
        }

        if (successNoList.size() > 0) {
            updateStatus(successNoList, "C");
        }
        
        if (failMessage.length() > 0) {
            log.info("[Fail to insert WmsReceiving Count]" + (receivingIfList.size() - successNoList.size()));
            sendFailReceivingSlack(getMesagesWithTemplate(failMessage), "insertReceiving");
        }
    }

    /**
     * RECDH, RECDI 저장을 위한 유효한 입고데이터 목록 검색
     * @param receivingIfList
     * @return List<WmsReceiving>
     */
    private List<WmsReceiving> getReceiving(List<WmsReceivingIf> receivingIfList) {

        log.info("[A - New WmsReceiving Count]" + receivingIfList.size ());
        List<WmsReceiving> selectFailReceivings = new ArrayList<>();
        List<Long> receivingIdList = receivingIfList.stream()
                .map(WmsReceivingIf::getTransactionId)
                .collect(Collectors.toList());

        // recdi, recdh insert 에 필요한 정보 추출
        List<WmsReceiving> receivingList = receivingMapper.findReceivingByTransactionIdIn(receivingIdList);

        // 이미 취소된 건, 입고된 건 리스트에서 제거 및 실패 목록에 추가
        receivingList = removeDeletedReceiving(receivingList, selectFailReceivings);
        receivingList = removeWMSReceiving(receivingList, selectFailReceivings);
        receivingList = removeBasicInfoNEReceiving(receivingList, selectFailReceivings);

        // 전체 실패 목록 슬랙메세지 전송
        if (selectFailReceivings.size() > 0) {
            log.info("[Fail to insert WmsReceiving Count]" + (receivingIfList.size() - receivingList.size()));
            sendFailReceivingSlack(getReceivingFailMeesages(selectFailReceivings), "getReceiving");
        }

        return receivingList;

    }

    /**
     * RECDI, RECDH 추가 대상건 중, 이미 취소된 발주 삭제 및 실패 목록에 추가
     * @param receivingList
     * @param selectFailReceivings
     */
    private List<WmsReceiving> removeDeletedReceiving(List<WmsReceiving> receivingList, List<WmsReceiving> selectFailReceivings) {
        List<WmsReceiving> deletedList = receivingList.stream()
                                                    .filter(rcv -> "D".equals(rcv.getStatus()))
                                                    .collect(Collectors.toList());

        if (deletedList.size() > 0) {
            List<Long> deletedIdList = deletedList.stream()
                    .map(rcv -> Long.parseLong(rcv.getAwmsno()))
                    .collect(Collectors.toList());
            updateStatus(deletedIdList, "D");
            selectFailReceivings.addAll(deletedList);
            return removeReceivings(receivingList, deletedList);
        }
        return receivingList;
    }

    /**
     * RECDI, RECDH 추가 대상건 중, 이미 입고된 항목 삭제 및 실패 목록에 추가
     * @param receivingList
     * @param selectFailReceivings
     */
    private List<WmsReceiving> removeWMSReceiving(List<WmsReceiving> receivingList, List<WmsReceiving> selectFailReceivings) {
        List<WmsReceiving> wmsReceivingList = receivingList.stream()
                                                    .filter(rcv -> "W".equals(rcv.getStatus()))
                                                    .collect(Collectors.toList());

        if (wmsReceivingList.size() > 0) {
            List<Long> wmsReceivingIdList = wmsReceivingList.stream()
                    .map(rcv -> Long.parseLong(rcv.getAwmsno()))
                    .collect(Collectors.toList());
            updateStatus(wmsReceivingIdList, "W");
            selectFailReceivings.addAll(wmsReceivingList);
            return removeReceivings(receivingList, wmsReceivingList);
        }
        return receivingList;
    }

    /**
     * RECDI, RECDH 추가 대상건 중, 상품/공급사 정보가 없는 항목 살제 및 실패 목록에 추가
     * @param receivingList
     * @param selectFailReceivings
     * @return List<WmsReceiving>
     */
    private List<WmsReceiving> removeBasicInfoNEReceiving(List<WmsReceiving> receivingList, List<WmsReceiving> selectFailReceivings) {
        List<WmsReceiving> basicInfoNEList = receivingList.stream()
                                                    .filter(rcv -> "GNE".equals(rcv.getStatus()) || "SNE".equals(rcv.getStatus()) )
                                                    .collect(Collectors.toList());

        if (basicInfoNEList.size() > 0) {
            selectFailReceivings.addAll(basicInfoNEList);
            return removeReceivings(receivingList, basicInfoNEList);
        }
        return receivingList;
    }

    /**
     * RECDH 추가, RECVKY set
     * @param receiving
     */
    private void saveRecdh(WmsReceiving receiving) {
        //set recvky
        receivingMapper.getRECVKY(receiving);
        receivingMapper.saveFromIfToRecdh(receiving);
    }

    /**
     * ReceivingIf 상태 일괇 변경
     * @param receivingIfIdList
     * @param status
     */
    private void updateStatus(List<Long> receivingIfIdList, String status) {
        HashMap receivingKeys = new HashMap();
        receivingKeys.put("ids", receivingIfIdList);
        receivingKeys.put("status", status);
        receivingMapper.changeStatusReceivingIfList(receivingKeys);
    }

    /**
     * ReceivingIf 중복건 슬랙메세지 전송
     * @param duplicateTransactionList
     */
    private void sendDupReceivingSlack (List<WmsReceivingIf> duplicateTransactionList) {
        //중복건 슬랙메세지 전송
        StringBuilder message = getReceivingIfFailMeesages(duplicateTransactionList);
        String displayName = "[RMS -> WMS 중복데이터]";
        messagingService.sendSlackMessage(displayName, receivingIfChannel, message.toString());
    }

    /**
     * ReceivingIf > RECDI, RECDH 추가 실패건 슬랙메세지 전송
     * @param failMessage
     * @param functionName
     */
    private void sendFailReceivingSlack (StringBuilder failMessage, String functionName) {
        //실패건 슬랙메세지 전송
        StringBuilder message = new StringBuilder("발생시점: ");
        message.append(functionName);
        message.append(failMessage);
        String displayName = "[RMS -> WMS 입고실패]";
        messagingService.sendSlackMessage(displayName, receivingIfChannel, message.toString());
    }

    /**
     * ReceivingIf 목록 슬랙메세지 정리 (상태값 표시)
     * @param receivingIfList
     * @return StringBuilder
     */
    private StringBuilder getReceivingIfFailMeesages (List<WmsReceivingIf> receivingIfList) {
        StringBuilder message = new StringBuilder();
        // 에러메세지
        for (WmsReceivingIf receivingIf : receivingIfList) {
            message.append(receivingIf.getErrorMessage(receivingIf.getStatusDesc()));
        }

        return getMesagesWithTemplate(message);
    }

    /**
     * ReceivingIf 목록 슬랙메세지 정리 (상태값 표시)
     * @param receivingList
     * @return StringBuilder
     */
    private StringBuilder getReceivingFailMeesages (List<WmsReceiving> receivingList) {
        StringBuilder message = new StringBuilder();
        // 에러메세지
        for (WmsReceiving receiving : receivingList) {
            message.append(receiving.getErrorMessage(receiving.getStatusDesc()));
        }

        return getMesagesWithTemplate(message);
    }

    /**
     * 슬랙메세지 Template
     * @param contents
     * @return StringBuilder
     */
    private StringBuilder getMesagesWithTemplate (StringBuilder contents) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n");
        builder.append("```");
        // 에러메세지
        builder.append(contents);
        // 마지막 줄바꿈 문자 삭제
        builder.deleteCharAt(builder.length() - 1);
        builder.append("```");

        return builder;
    }

    /**
     * 특정 Transaction Id 목록을 제외한 RcvTransaction 조회
     * @param transactionList
     * @param receivingIfList
     * @return List<RcvTransaction>
     */
    private List<RcvTransaction> getTransactionNotContainIds (List<RcvTransaction> transactionList, List<WmsReceivingIf> receivingIfList) {
        List<Long> receivingIfIdList = receivingIfList.stream().map(WmsReceivingIf::getTransactionId).collect(Collectors.toList());
        return transactionList.stream()
                .filter(rcv -> !receivingIfIdList.contains(rcv.getTransactionId()))
                .collect(Collectors.toList());
    }

    private List<WmsReceiving> removeReceivings (List<WmsReceiving> sourceReceivings, List<WmsReceiving> targetReceivings) {
        List<String> targetIdList = targetReceivings.stream().map(rcv -> rcv.getAwmsno()).collect(Collectors.toList());
        return sourceReceivings.stream()
                .filter(rcv -> !targetIdList.contains(rcv.getAwmsno()))
                .collect(Collectors.toList());
    }

}
