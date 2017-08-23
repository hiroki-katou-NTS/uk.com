/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDateTime;

/**
 * The Class ExecutionTime.
 * 実行日時
 */
@Getter
public class ExecutionTime extends DomainObject {
    
    /** The start date. */
    //開始日時
    private GeneralDateTime startDateTime;
    
    /** The end date. */
    // 終了日時
    private GeneralDateTime endDateTime;
    
    /**
     * Instantiates a new execution time.
     *
     * @param memento the memento
     */
    public ExecutionTime(ExecutionTimeGetMemento memento) {
        super();
        this.startDateTime = memento.getStartDateTime();
        this.endDateTime = memento.getEndDateTime();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ExecutionTimeSetMemento memento) {
        memento.setStartDateTime(this.startDateTime);
        memento.setEndDateTime(this.endDateTime);
    }
}
