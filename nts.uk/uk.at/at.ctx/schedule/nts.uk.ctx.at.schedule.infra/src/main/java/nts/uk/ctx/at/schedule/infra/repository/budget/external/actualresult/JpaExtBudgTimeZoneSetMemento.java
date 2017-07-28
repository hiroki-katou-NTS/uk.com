/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgTimeZoneSetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetTimeZoneVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetTimePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetTimeVal;

/**
 * The Class JpaExtBudgTimeZoneSetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExtBudgTimeZoneSetMemento<T> implements ExtBudgTimeZoneSetMemento<T> {

    /** The entity. */
    private KscdtExtBudgetTime entity;

    /**
     * Instantiates a new jpa ext budg time zone set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExtBudgTimeZoneSetMemento(KscdtExtBudgetTime entity) {
        if (entity != null && entity.getKbtdtExtBudgetTimePK() == null) {
            entity.setKbtdtExtBudgetTimePK(new KscdtExtBudgetTimePK());
        }
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.getKbtdtExtBudgetTimePK().setCid(companyId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setActualValues(java.util.List)
     */
    @Override
    public void setActualValues(List<ExternalBudgetTimeZoneVal<T>> actualValues) {
        List<KscdtExtBudgetTimeVal> listValue = this.entity.getListValue();
        if (CollectionUtil.isEmpty(listValue)) {
            listValue = actualValues.stream().map(domain -> {
                KscdtExtBudgetTimeVal entityVal = new KscdtExtBudgetTimeVal();
                JpaExtBudgTimeZoneValSetMemento<T> memento = new JpaExtBudgTimeZoneValSetMemento<T>(entityVal);
                domain.saveToMememto(memento);
                return entityVal;
            }).collect(Collectors.toList());
        } else {
            for (int i = 0; i < listValue.size(); i++) {
                ExternalBudgetTimeZoneVal<T> objectVal = actualValues.get(i);
                KscdtExtBudgetTimeVal entityVal = listValue.get(i);
                JpaExtBudgTimeZoneValSetMemento<T> memento = new JpaExtBudgTimeZoneValSetMemento<T>(entityVal);
                objectVal.saveToMememto(memento);
            }
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
        this.entity.setExtBudgetCd(extBudgetCode.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setProcessDate(java.util.Date)
     */
    @Override
    public void setProcessDate(Date processDate) {
        this.entity.setProcessD(GeneralDate.legacyDate(processDate));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExtBudgTimeZoneSetMemento#setWorkplaceId(java.lang.String)
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.entity.getKbtdtExtBudgetTimePK().setWkpid(workplaceId);
    }

}
