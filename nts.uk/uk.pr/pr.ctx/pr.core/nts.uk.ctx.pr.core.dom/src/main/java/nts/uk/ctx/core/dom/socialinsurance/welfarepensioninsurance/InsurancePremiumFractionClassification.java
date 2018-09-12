package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;


/**
 * 保険料端数区分
 */
public enum InsurancePremiumFractionClassification {

    TRUNCATION(0, "Enum_InsuPremiumFractionClassification_TRUNCATION"),
    ROUND_UP(1, "Enum_InsuPremiumFractionClassification_ROUND_UP"),
    ROUND_4_UP_5(2, "Enum_InsuPremiumFractionClassification_ROUND_4_UP_5"),
    ROUND_5_UP_6(3, "Enum_InsuPremiumFractionClassification_ROUND_5_UP_6"),
    ROUND_SUPER_5(4, "Enum_InsuPremiumFractionClassification_ROUND_SUPER_5");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    private InsurancePremiumFractionClassification(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
