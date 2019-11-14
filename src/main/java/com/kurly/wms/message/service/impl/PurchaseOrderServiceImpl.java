package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.domain.MdOrderDocumentDto;
import com.kurly.wms.message.repository.PurchaseOrderRepository;
import com.kurly.wms.message.service.PurchaseOrderService;
import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    @Value("${slack.info.slackUrl}")
    private String slackUrl;

    @Value("${slack.info.errorNotiChannel}")
    private String errorNotiChannel;

    /**
     * 발주정보 등록 from new eSCM
     *
     * @param mdOrderDocumentDtos
     */
    public void savePurchaseOrder(List<MdOrderDocumentDto> mdOrderDocumentDtos) throws Exception {
        mdOrderDocumentDtos.forEach(mdOrderDocumentDto -> {
            try {
                purchaseOrderRepository.savePurchaseOrder(mdOrderDocumentDto);
            } catch (SQLException e) {
                try {
                    new Slack(slackUrl)
                            .icon(":escmlogo:")
                            .displayName("eSCM bot")
                            .sendToChannel(errorNotiChannel)
                            .push(new SlackMessage("[" + mdOrderDocumentDto.getOrder_code() + "]발주정보 연동 중 오류가 발생햇습니다."));
                } catch (Exception ignore) {

                }
                log.error("발주서 등록 error : order_code = {}, order_sub_code = {}" , mdOrderDocumentDto.getOrder_code(), mdOrderDocumentDto.getOrder_sub_code());

            }
        });

    }


}
