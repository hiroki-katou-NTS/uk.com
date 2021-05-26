/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExternalBudgetVal;

/**
 * The Class ExternalBudgetDaily.
 * 外部予算実績日次
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"workplaceId", "actualDate", "extBudgetCode"})
public class ExternalBudgetDaily<T> extends AggregateRoot {

    /** The actual value. */
    // 外部予算実績値
    private ExternalBudgetVal<T> actualValue;
    
    /** The ext budget code. */
    // 外部予算実績項目コード
    private ExternalBudgetCd extBudgetCode;
    
    /** The actual date. */
    // 年月日
    private Date actualDate;
    
    /** The workplace id. */
    // 職場ID
    private String workplaceId;

    /**
     * Instantiates a new external budget daily.
     *
     * @param memento the memento
     */
    public ExternalBudgetDaily(ExternalBudgetDailyGetMemento<T> memento) {
        super();
        this.actualValue = memento.getActualValue();
        this.extBudgetCode = memento.getExtBudgetCode();
        this.actualDate = memento.getActualDate();
        this.workplaceId = memento.getWorkplaceId();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExternalBudgetDailySetMemento<T> memento) {
        memento.setActualValue(this.actualValue);
        memento.setExtBudgetCode(this.extBudgetCode);
        memento.setActualDate(this.actualDate);
        memento.setWorkplaceId(this.workplaceId);
    }
}
