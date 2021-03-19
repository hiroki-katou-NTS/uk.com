/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.dailyunit;

import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDailySetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.dailyunit.KscdtExtBudgetDaily;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.dailyunit.KscdtExtBudgetDailyPK;

/**
 * The Class JpaExternalBudgetDailySetMemento.
 *
 * @param <T>
 *            the generic type
 */
public class JpaExternalBudgetDailySetMemento<T> implements ExternalBudgetDailySetMemento<T> {

    /** The entity. */
    private KscdtExtBudgetDaily entity;

    /**
     * Instantiates a new jpa external budget daily set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetDailySetMemento(KscdtExtBudgetDaily entity) {
        if (entity != null && entity.getKscdtExtBudgetDailyPK() == null) {
            entity.setKscdtExtBudgetDailyPK(new KscdtExtBudgetDailyPK());
        }
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailySetMemento#setActualValue(nts.uk.ctx.at.schedule.dom.
     * budget.external.actualresult.ExternalBudgetVal)
     */
    @Override
    public void setActualValue(ExternalBudgetVal<T> actualValue) {
        this.entity.setActualVal(actualValue.getRawValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailySetMemento#setExtBudgetCode(nts.uk.ctx.at.schedule.dom
     * .budget.external.ExternalBudgetCd)
     */
    @Override
    public void setExtBudgetCode(ExternalBudgetCd extBudgetCode) {
        this.entity.getKscdtExtBudgetDailyPK().setExtBudgetCd(extBudgetCode.v());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailySetMemento#setProcessDate(java.util.Date)
     */
    @Override
    public void setActualDate(Date processDate) {
        this.entity.getKscdtExtBudgetDailyPK().setActualDate(GeneralDate.legacyDate(processDate));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailySetMemento#setWorkplaceId(java.lang.String)
     */
    @Override
    public void setWorkplaceId(String workplaceId) {
        this.entity.getKscdtExtBudgetDailyPK().setWkpid(workplaceId);
    }

}
