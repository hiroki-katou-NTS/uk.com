/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.acceptance.dailyunit;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDailyGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.dailyunit.KscdtExtBudgetDaily;

/**
 * The Class JpaExternalBudgetDailyGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExternalBudgetDailyGetMemento<T> implements ExternalBudgetDailyGetMemento<T> {

    /** The entity. */
    private KscdtExtBudgetDaily entity;

    /**
     * Instantiates a new jpa external budget daily get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetDailyGetMemento(KscdtExtBudgetDaily entity) {
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getActualValue()
     */
    @Override
    public ExternalBudgetVal<T> getActualValue() {
        // TODO: Not find data master at KSU006 --> return null;
//        return new ExternalBudgetVal<T>(this.entity.getActualVal());
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getExtBudgetCode()
     */
    @Override
    public ExternalBudgetCd getExtBudgetCode() {
        return new ExternalBudgetCd(this.entity.getKscdtExtBudgetDailyPK().getExtBudgetCd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getProcessDate()
     */
    @Override
    public Date getActualDate() {
        return this.entity.getKscdtExtBudgetDailyPK().getActualDate().date();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getWorkplaceId()
     */
    @Override
    public String getWorkplaceId() {
        return this.entity.getKscdtExtBudgetDailyPK().getWkpid();
    }

}
