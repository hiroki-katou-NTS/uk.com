package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.i18n.I18NText;

/**
 * 算定区分
 */
public enum CalculationAtr {

    SCHEDULED (0, I18NText.getText("Enum_CalculationAtr_SCHEDULED")),
    CHANGE_OVER_TIME(1, I18NText.getText("Enum_CalculationAtr_CHANGE_OVER_TIME")),
    CHANGE_BEFORE_AND_AFTER_CHILDBIRTH(2, I18NText.getText("Enum_CalculationAtr_CHANGE_BEFORE_AND_AFTER_CHILDBIRTH")),
    CHANGE_AFTER_CHILDCARE_LEAVE(3, I18NText.getText("Enum_CalculationAtr_CHANGE_AFTER_CHILDCARE_LEAVE")),
    OBTAINING_QUALIFICATION(4, I18NText.getText("Enum_CalculationAtr_OBTAINING_QUALIFICATION"));

    /** The value */
    public final int value;

    /** The name id */
    public final String nameId;

    CalculationAtr(int value, String nameId){
        this.value = value;
        this.nameId = nameId;
    }

}
