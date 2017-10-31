/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.timeunit;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgTimeZoneSetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.timeunit.KscdtExtBudgetTime;

/**
 * The Class JpaExtBudgTimeZoneSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExtBudgTimeZoneSetMemento<T> implements ExtBudgTimeZoneSetMemento<T> {

    /** The entity. */
    private List<KscdtExtBudgetTime> lstEntity;

    /**
     * Instantiates a new jpa ext budg time zone set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExtBudgTimeZoneSetMemento(List<KscdtExtBudgetTime> lstEntity) {
        this.lstEntity = lstEntity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setActualValues(java.util.List)
     */
    @Override
    public void setActualValues(List<ExternalBudgetTimeZoneVal<T>> actualValues) {
        // convert list entity to map(key: PeriodTimeNo, value: KscdtExtBudgetTime)
        Map<Integer, KscdtExtBudgetTime> mapEntity = lstEntity.stream().collect(
                Collectors.toMap(entity -> entity.getKscdtExtBudgetTimePK().getPeriodTimeNo(), entity -> entity));
        
        for (ExternalBudgetTimeZoneVal<T> object : actualValues) {
            JpaExtBudgTimeZoneValSetMemento<T> memento = new JpaExtBudgTimeZoneValSetMemento<T>(
                    mapEntity.get(object.getTimePeriod()));
            object.saveToMememto(memento);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setExtBudgetCode(nts.uk.ctx.at.schedule.dom.
     * budget.external.ExternalBudgetCd)
     */
    @Override
    public void setExtBudgetCode(ExternalBudgetCd extBudgetCode) {
        this.lstEntity.forEach(entity -> {
            entity.getKscdtExtBudgetTimePK().setExtBudgetCd(extBudgetCode.v());
        });
    }

    @Override
    public void setActualDate(Date actualDate) {
        this.lstEntity.forEach(entity -> {
            entity.getKscdtExtBudgetTimePK().setActualDate(GeneralDate.legacyDate(actualDate));
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setWorkplaceId(java.lang.String)
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.lstEntity.forEach(entity -> {
            entity.getKscdtExtBudgetTimePK().setWkpid(workplaceId);
        });
    }

}
