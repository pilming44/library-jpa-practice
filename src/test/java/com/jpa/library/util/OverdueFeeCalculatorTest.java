package com.jpa.library.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class OverdueFeeCalculatorTest {
    @ParameterizedTest
    @CsvSource({"0, 0", "1, 100", "2, 200", "3, 400", "5, 800", "6, 1100", "10, 2300", "15, 4800"})
    @DisplayName("연체기간에따른 연체비 계산")
    void 연체비(int overdueDays, Long overdueFee) {
        assertThat(OverdueFeeCalculator.calculateOverdueFee(overdueDays)).isEqualTo(overdueFee);
    }
}