package nts.uk.ctx.core.dom.socialinsurance.welfarepensioninsurance;


/**
 * 保険料端数区分
 */
public enum InsurancePremiumFractionClassification {

    /**
     * 切り捨て
     */
    TRUNCATION(0, "Enum_InsuPremiumFractionClassification_TRUNCATION"),

    /**
     * 切り上げ
     */
    ROUND_UP(1, "Enum_InsuPremiumFractionClassification_ROUND_UP"),

    /**
     * 四捨五入
     */
    ROUND_4_UP_5(2, "Enum_InsuPremiumFractionClassification_ROUND_4_UP_5"),

    /**
     * 五捨六入
     */
    ROUND_5_UP_6(3, "Enum_InsuPremiumFractionClassification_ROUND_5_UP_6"),

    /**
     * 五捨五超入
     */
    ROUND_SUPER_5(4, "Enum_InsuPremiumFractionClassification_ROUND_SUPER_5");

    /**
     * The value.
     */
    public final int value;

    /**
     * The name id.
     */
    public final String nameId;

    InsurancePremiumFractionClassification(int value, String nameId) {
        this.value = value;
        this.nameId = nameId;
    }
}
