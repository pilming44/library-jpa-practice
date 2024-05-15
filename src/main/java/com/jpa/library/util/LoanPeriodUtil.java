package com.jpa.library.util;

public class LoanPeriodUtil {
    public static int getLoanDays(int totalQuantity) {
        if (totalQuantity <= 2) {
            return 7;
        } else if (totalQuantity <= 5) {
            return 10;
        } else {
            return 14;
        }
    }
}
