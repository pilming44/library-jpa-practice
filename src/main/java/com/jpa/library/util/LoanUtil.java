package com.jpa.library.util;

public class LoanUtil {
    public static int getLoanDays(long totalQuantity) {
        if (totalQuantity <= 2) {
            return 7;
        } else if (totalQuantity <= 5) {
            return 10;
        } else {
            return 14;
        }
    }
}
