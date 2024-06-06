package com.jpa.library.util;

import java.util.ArrayList;
import java.util.List;

public class OverdueFeeCalculator {
    public record FeePolicy(int days, int feePerDay) {}

    public static int calculateOverdueFee(int overdueDays) {
        List<FeePolicy> feePolicies = new ArrayList<>();
        feePolicies.add(new FeePolicy(2, 100));
        feePolicies.add(new FeePolicy(3, 200));
        feePolicies.add(new FeePolicy(5, 300));
        feePolicies.add(new FeePolicy(Integer.MAX_VALUE, 500));

        int totalFee = 0;
        int remainingDays = overdueDays;

        for (FeePolicy policy : feePolicies) {
            if (remainingDays <= 0) {
                break;
            }
            int daysInPolicy = Math.min(remainingDays, policy.days());
            totalFee += daysInPolicy * policy.feePerDay();
            remainingDays -= daysInPolicy;
        }

        return totalFee;
    }
}
