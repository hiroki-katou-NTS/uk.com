/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import java.util.Date;

import lombok.Builder;
import nts.uk.ctx.at.schedule.dom.budget.external.BudgetAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.dailyunit.ExternalBudgetDailyGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetMoneyValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExternalBudgetTimeValue;

/**
 * The Class ExternalBudgetDailyDto.
 */
@Builder
public class ExternalBudgetDailyDto {

    /** The budget atr. */
    private BudgetAtr budgetAtr;

    /** The actual value. */
    public Long actualValue;

    /** The ext budget code. */
    public String extBudgetCode;

    /** The actual date. */
    public Date actualDate;

    /** The workplace id. */
    public String workplaceId;

    /**
     * To domain.
     *
     * @param <T>
     *            the generic type
     * @return the external budget daily
     */
    public <T> ExternalBudgetDaily<T> toDomain() {
        return new ExternalBudgetDaily<T>(new ExternalBudgetDailyGetMementoImpl<T>(this));
    }

    /**
     * The Class ExternalBudgetDailyGetMementoImpl.
     *
     * @param <T>
     *            the generic type
     */
    private class ExternalBudgetDailyGetMementoImpl<T> implements ExternalBudgetDailyGetMemento<T> {

        /** The dto. */
        private ExternalBudgetDailyDto dto;

        /**
         * Instantiates a new external budget daily get memento impl.
         *
         * @param dto
         *            the dto
         */
        public ExternalBudgetDailyGetMementoImpl(ExternalBudgetDailyDto dto) {
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetDailyGetMemento#getActualValue()
         */
        @SuppressWarnings("unchecked")
        @Override
        public ExternalBudgetVal<T> getActualValue() {
            switch (this.dto.budgetAtr) {
            case TIME:
                return new ExternalBudgetVal<T> ((T) new ExternalBudgetTimeValue(this.dto.actualValue.intValue()));
            case PEOPLE:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetNumberPerson(this.dto.actualValue.intValue()));
            case MONEY:
                return new ExternalBudgetVal<T> ((T) new ExternalBudgetMoneyValue(this.dto.actualValue.intValue()));
            case NUMERICAL:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetNumericalVal(this.dto.actualValue.intValue()));
            case PRICE:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetUnitPrice(this.dto.actualValue.intValue()));
            default:
                throw new RuntimeException("Not budget atr suitable.");
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetDailyGetMemento#getExtBudgetCode()
         */
        @Override
        public ExternalBudgetCd getExtBudgetCode() {
            return new ExternalBudgetCd(this.dto.extBudgetCode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetDailyGetMemento#getActualDate()
         */
        @Override
        public Date getActualDate() {
            return this.dto.actualDate;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetDailyGetMemento#getWorkplaceId()
         */
        @Override
        public String getWorkplaceId() {
            return this.dto.workplaceId;
        }

    }
}
