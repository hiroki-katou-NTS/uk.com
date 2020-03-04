package nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.i18n.I18NText;

import java.beans.ConstructorProperties;

/**
 * 算定区分
 */
public enum CalculationAtr {

    SCHEDULED (0),
    CHANGE_OVER_TIME(1),
    CHANGE_BEFORE_AND_AFTER_CHILDBIRTH(2),
    CHANGE_AFTER_CHILDCARE_LEAVE(3),
    OBTAINING_QUALIFICATION(4);

    public final int value;

    @ConstructorProperties({"value"})
    CalculationAtr(int value) {
        this.value = value;
    }
}
