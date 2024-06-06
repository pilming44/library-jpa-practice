package com.jpa.library.util;

import java.util.ArrayList;
import java.util.List;

public class OverdueFeeCalculator {
    public record FeePolicy(int days, Long feePerDay) {}

    public static Long calculateOverdueFee(int overdueDays) {
        List<FeePolicy> feePolicies = new ArrayList<>();
        feePolicies.add(new FeePolicy(2, 100L));
        feePolicies.add(new FeePolicy(3, 200L));
        feePolicies.add(new FeePolicy(5, 300L));
        feePolicies.add(new FeePolicy(Integer.MAX_VALUE, 500L));

        Long totalFee = 0L;
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
