/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.Date;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class ExecutionTime.
 * 実行日時
 */
@Getter
public class ExecutionTime extends DomainObject {
    
    /** The start date. */
    //開始日時
    private Date startDate;
    
    /** The end date. */
    // 終了日時
    private Date endDate;
    
    /**
     * Instantiates a new execution time.
     *
     * @param memento the memento
     */
    public ExecutionTime(ExecutionTimeGetMemento memento) {
        super();
        this.startDate = memento.getStartDate();
        this.endDate = memento.getEndDate();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExecutionTimeSetMemento memento) {
        memento.setStartDate(this.startDate);
        memento.setEndDate(this.endDate);
    }
}
