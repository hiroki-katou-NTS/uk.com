/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import java.util.Date;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Class ExternalBudgetDaily.
 * 外部予算実績日次
 */
@Getter
public class ExternalBudgetDaily<T> extends AggregateRoot {

    /** The company id. */
    // 会社ID
    private String companyId;
    
    /** The actual value. */
    // 外部予算実績値
    private ExternalBudgetVal<T> actualValue;
    
    /** The ext budget code. */
    // 外部予算実績項目コード
    private ExternalBudgetCd extBudgetCode;
    
    /** The process date. */
    // 年月日
    private Date processDate;
    
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
        this.companyId = memento.getCompanyId();
        this.actualValue = memento.getActualValue();
        this.extBudgetCode = memento.getExtBudgetCode();
        this.processDate = memento.getProcessDate();
        this.workplaceId = memento.getWorkplaceId();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExternalBudgetDailySetMemento<T> memento) {
        memento.setCompanyId(this.companyId);
        memento.setActualValue(this.actualValue);
        memento.setExtBudgetCode(this.extBudgetCode);
        memento.setProcessDate(this.processDate);
        memento.setWorkplaceId(this.workplaceId);
    }
}
