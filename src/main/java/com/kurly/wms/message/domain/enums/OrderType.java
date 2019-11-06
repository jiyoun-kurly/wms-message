package com.kurly.wms.message.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

    KURLY_ORDER("01"),
    TC_ORDER("02");

    private String value;

}