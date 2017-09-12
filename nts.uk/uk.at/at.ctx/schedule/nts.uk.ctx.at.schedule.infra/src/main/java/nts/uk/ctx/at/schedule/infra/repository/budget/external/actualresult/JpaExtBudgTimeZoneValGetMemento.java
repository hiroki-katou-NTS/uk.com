/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgTimeZoneValGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetTime;

/**
 * The Class JpaExtBudgTimeZoneValGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExtBudgTimeZoneValGetMemento<T> implements ExtBudgTimeZoneValGetMemento<T> {

    /** The entity. */
    private KscdtExtBudgetTime entity;

    /**
     * Instantiates a new jpa ext budg time zone val get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExtBudgTimeZoneValGetMemento(KscdtExtBudgetTime entity) {
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneValGetMemento#getTimePeriod()
     */
    @Override
    public int getTimePeriod() {
        return this.entity.getKscdtExtBudgetTimePK().getPeriodTimeNo();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneValGetMemento#getActualValue()
     */
    @Override
    public ExternalBudgetVal<T> getActualValue() {
     // TODO: Not find data master at KSU006 --> return null;
//        return new ExternalBudgetVal<T>(this.entity.getActualVal());
        return null;
    }

}
