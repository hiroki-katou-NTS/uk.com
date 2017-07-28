/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailyGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetDaily;

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
     * ExternalBudgetDailyGetMemento#getCompanyId()
     */
    @Override
    public String getCompanyId() {
        return this.entity.getKbddtExtBudgetDailyPK().getCid();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getActualValue()
     */
    @Override
    public ExternalBudgetVal<T> getActualValue() {
        return new ExternalBudgetVal<T>(this.entity.getActualVal());
//        
//        
//        ExternalBudgetVal<T> a = new ExternalBudgetVal<T>();
//        a.set
//        
//        switch (budgetAtr) {
//        case TIME:
//            ExtBudgetTime rawTimeVal = new ExtBudgetTime(this.entity.getActualVal());
//            return new ExternalBudgetVal<ExtBudgetTime>(rawTimeVal);
//        case PEOPLE:
//            ExtBudgetNumberPerson rawPersonVal = new ExtBudgetNumberPerson(this.entity.getActualVal().intValue());
//            return new ExternalBudgetVal<ExtBudgetNumberPerson>(rawPersonVal);
//        case MONEY:
//            ExtBudgetMoney rawMoneyVal = new ExtBudgetMoney(this.entity.getActualVal().intValue());
//            return new ExternalBudgetVal<ExtBudgetMoney>(rawMoneyVal);
//        case NUMERICAL:
//            ExtBudgetNumericalVal rawNumericalVal = new ExtBudgetNumericalVal(this.entity.getActualVal().intValue());
//            return new ExternalBudgetVal<ExtBudgetNumericalVal>(rawNumericalVal);
//        case PRICE:
//            ExtBudgetUnitPrice rawPriceVal = new ExtBudgetUnitPrice(this.entity.getActualVal().intValue());
//            return new ExternalBudgetVal<ExtBudgetUnitPrice>(rawPriceVal);
//        default:
//            throw new RuntimeException("Not external budget attribute");
//        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getExtBudgetCode()
     */
    @Override
    public ExternalBudgetCd getExtBudgetCode() {
        return new ExternalBudgetCd(this.entity.getExtBudgetCd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getProcessDate()
     */
    @Override
    public Date getProcessDate() {
        return this.entity.getProcessD().date();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetDailyGetMemento#getWorkplaceId()
     */
    @Override
    public String getWorkplaceId() {
        return this.entity.getKbddtExtBudgetDailyPK().getWkpid();
    }

}
