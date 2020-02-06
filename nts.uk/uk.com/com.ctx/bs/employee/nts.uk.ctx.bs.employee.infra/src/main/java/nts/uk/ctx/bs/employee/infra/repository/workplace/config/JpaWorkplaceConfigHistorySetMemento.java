/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.workplace.config;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistorySetMemento;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkplaceConfigHistorySetMemento.
 */
public class JpaWorkplaceConfigHistorySetMemento implements WorkplaceConfigHistorySetMemento {

    /** The entity. */
    private BsymtWkpConfig entity;

    /**
     * Instantiates a new jpa workplace config history set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaWorkplaceConfigHistorySetMemento(BsymtWkpConfig entity) {
        this.entity = entity;
    }

    /**
     * Sets the history id.
     *
     * @param historyId
     *            the new history id
     */
    @Override
    public void setHistoryId(String historyId) {
        entity.getBsymtWkpConfigPK().setHistoryId(historyId);
    }

    /**
     * Sets the period.
     *
     * @param period
     *            the new period
     */
    @Override
    public void setPeriod(DatePeriod period) {
        entity.setStrD(period.start());
        entity.setEndD(period.end());
    }

}
