package com.kurly.wms.message.domain.modelMapper;

import com.kurly.wms.message.client.RcvTransaction;
import com.kurly.wms.message.domain.WmsReceivingIf;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class WmsReceivingIfMapper {
    @Getter
    protected static ModelMapper mapper = new ModelMapper();

    static {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //RcvTransaction -> WmsReceivingIf
        getMapper().createTypeMap(RcvTransaction.class, WmsReceivingIf.class)
                .addMappings(mapper -> {
                    // 필요 시 mapping 로직추가 (아래 예제)
                    // mapper.map(RcvTransaction::getCreatedBy, WmsReceivingIf::setCreatedBy);
                    // mapper.skip(WmsReceivingIf::setCreationDate);
                });

    }
}
