/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.timeunit;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgTimeZoneGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTime;

/**
 * The Class JpaExtBudgTimeZoneGetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExtBudgTimeZoneGetMemento<T> implements ExtBudgTimeZoneGetMemento<T> {

    private static final Integer FIRST_ELEMENT = 0;
    
    /** The lst entity. */
    private List<KscdtExtBudgetTime> lstEntity;

    /**
     * Instantiates a new jpa ext budg time zone get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExtBudgTimeZoneGetMemento(List<KscdtExtBudgetTime> lstEntity) {
        this.lstEntity = lstEntity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getActualValues()
     */
    @Override
    public List<ExternalBudgetTimeZoneVal<T>> getActualValues() {
        return this.lstEntity.stream()
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
        return new ExternalBudgetCd(this.lstEntity.get(FIRST_ELEMENT).getKscdtExtBudgetTimePK().getExtBudgetCd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getProcessDate()
     */
    @Override
    public Date getActualDate() {
        return this.lstEntity.get(FIRST_ELEMENT).getKscdtExtBudgetTimePK().getActualDate().date();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneGetMemento#getWorkplaceId()
     */
    @Override
    public String getWorkplaceId() {
        return this.lstEntity.get(FIRST_ELEMENT).getKscdtExtBudgetTimePK().getWkpid();
    }

}
