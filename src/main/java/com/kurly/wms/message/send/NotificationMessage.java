package com.kurly.wms.message.send;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Push 또는 전송할 메세지객체
 *
 * @param <E>
 */
@ToString
@Getter
@Setter
public class NotificationMessage<E> {

    private E data;

    public NotificationMessage(E data) {
        this.data = data;
    }

}
