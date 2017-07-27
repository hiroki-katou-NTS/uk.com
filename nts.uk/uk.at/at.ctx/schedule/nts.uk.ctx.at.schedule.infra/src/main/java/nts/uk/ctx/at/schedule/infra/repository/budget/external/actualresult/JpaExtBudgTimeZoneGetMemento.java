/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgTimeZoneGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KbtdtExtBudgetTime;

/**
 * The Class JpaExtBudgTimeZoneGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExtBudgTimeZoneGetMemento<T> implements ExtBudgTimeZoneGetMemento<T> {

    /** The entity. */
    private KbtdtExtBudgetTime entity;

    /**
     * Instantiates a new jpa ext budg time zone get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExtBudgTimeZoneGetMemento(KbtdtExtBudgetTime entity) {
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entity.getKbtdtExtBudgetTimePK().getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getActualValues()
     */
    @Override
    public List<ExternalBudgetTimeZoneVal<T>> getActualValues() {
        return this.entity.getListValue().stream()
                .map(entity -> new ExternalBudgetTimeZoneVal<T>(new JpaExtBudgTimeZoneValGetMemento<T>(entity)))
                .collect(Collectors.toList());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getExtBudgetCode()
     */
    @Override
    public ExternalBudgetCd getExtBudgetCode() {
        return new ExternalBudgetCd(this.entity.getExtBudgetCd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getProcessDate()
     */
    @Override
    public Date getProcessDate() {
        return this.entity.getProcessD().date();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getWorkplaceId()
     */
    @Override
    public String getWorkplaceId() {
        return this.entity.getKbtdtExtBudgetTimePK().getWkpid();
    }

}
