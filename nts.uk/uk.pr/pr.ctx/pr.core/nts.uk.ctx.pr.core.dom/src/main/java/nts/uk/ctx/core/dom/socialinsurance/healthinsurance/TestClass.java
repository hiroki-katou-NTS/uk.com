package nts.uk.ctx.core.dom.socialinsurance.healthinsurance;

import nts.uk.ctx.core.dom.socialinsurance.RoundCalculatedValue;
import nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance.InsurancePremiumFractionClassification;

import java.math.BigDecimal;

public class TestClass {
    private static final long money = 10000;

    public static void main(String[] arrg){
        long money = 58000;
        BigDecimal rate1 = new BigDecimal("49.550");
        BigDecimal rate2 = new BigDecimal("57.800");
        BigDecimal rate3 = new BigDecimal("18.650");
        BigDecimal rate4 = new BigDecimal("30.900");


        System.out.println(RoundCalculatedValue.calculation(money, rate1, InsurancePremiumFractionClassification.ROUND_4_UP_5, 0));
        System.out.println(RoundCalculatedValue.calculation(money, rate2, InsurancePremiumFractionClassification.ROUND_4_UP_5, 0));
        System.out.println(RoundCalculatedValue.calculation(money, rate3, InsurancePremiumFractionClassification.ROUND_4_UP_5, 2));
        System.out.println(RoundCalculatedValue.calculation(money, rate4, InsurancePremiumFractionClassification.ROUND_4_UP_5, 2));
    }
}