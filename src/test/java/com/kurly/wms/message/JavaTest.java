package com.kurly.wms.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JavaTest {

    @Test
    public void substringTest() {
        String testStr = "홍길동";
        testStr = testStr.substring(0,2);
        assertThat(testStr).isEqualTo("홍길");
    }
}
