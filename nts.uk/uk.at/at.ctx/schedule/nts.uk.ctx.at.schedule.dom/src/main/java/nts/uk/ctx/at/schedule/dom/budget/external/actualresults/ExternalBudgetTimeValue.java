/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * The Class ExtBudgetTime.
 * 外部予算実績時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.予算.外部予算.外部予算実績.外部予算実績時間
 */
@TimeRange(min = "00:00", max = "999:59")
public class ExternalBudgetTimeValue extends TimeDurationPrimitiveValue<ExternalBudgetTimeValue> implements ExternalBudgetValues {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new ext budget time.
     *
     * @param rawValue the raw value
     */
    public ExternalBudgetTimeValue(int rawValue) {
        super(rawValue);
    }

}
