/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class ExtBudgetMoney.
 * 外部予算実績金額
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.予算.外部予算.外部予算実績.外部予算実績金額
 */
@IntegerRange(min = 0, max = 99999999)
public class ExternalBudgetMoneyValue extends IntegerPrimitiveValue<ExternalBudgetMoneyValue> implements ExternalBudgetValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget amount.
     *
     * @param rawValue the raw value
     */
    public ExternalBudgetMoneyValue(Integer rawValue) {
        super(rawValue);
    }

}
