package com.jpa.library.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class LoanUtilTest {

    @ParameterizedTest
    @CsvSource({"1, 7", "2, 7", "3, 10", "4, 10", "5, 10", "6, 14", "10, 14"})
    @DisplayName("총 보유 권수에따른 대여기간")
    void 대여기간(int totalQuantity, int loanDays) {
        assertThat(LoanUtil.getLoanDays(totalQuantity)).isEqualTo(loanDays);
    }
}