/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit;

import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Class ExternalBudgetTimeZone.
 *
 * @param <T> the generic type
 * 外外部予算実績時間帯
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"workplaceId", "actualDate", "extBudgetCode"})
public class ExternalBudgetTimeZone<T> extends AggregateRoot {
    
    /** The actual value. */
    // 外部予算実績時間帯値
    private List<ExternalBudgetTimeZoneVal<T>> actualValues;
    
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
     * Instantiates a new external budget time table.
     *
     * @param memento the memento
     */
    public ExternalBudgetTimeZone(ExtBudgTimeZoneGetMemento<T> memento) {
        super();
        this.actualValues = memento.getActualValues();
        this.extBudgetCode = memento.getExtBudgetCode();
        this.actualDate = memento.getActualDate();
        this.workplaceId = memento.getWorkplaceId();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExtBudgTimeZoneSetMemento<T> memento) {
        memento.setActualValues(this.actualValues);
        memento.setExtBudgetCode(this.extBudgetCode);
        memento.setActualDate(this.actualDate);
        memento.setWorkplaceId(this.workplaceId);
    }
}
