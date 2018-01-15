/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.Builder;
import nts.uk.ctx.at.schedule.dom.budget.external.BudgetAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetMoney;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumberPerson;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetNumericalVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetUnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgTimeZoneGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgTimeZoneValGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExtBudgetTime;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZone;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.timeunit.ExternalBudgetTimeZoneVal;

/**
 * The Class ExternalBudgetTimeDto.
 */
@Builder
public class ExternalBudgetTimeDto {

    /** The ext budget code. */
    public String extBudgetCode;

    /** The actual date. */
    public Date actualDate;

    /** The workplace id. */
    public String workplaceId;

    /** The budget atr. */
    private BudgetAtr budgetAtr;
    
    /** The map value. */
    public Map<Integer, Long> mapValue;

    /**
     * To domain.
     *
     * @param <T>
     *            the generic type
     * @return the external budget time zone
     */
    public <T> ExternalBudgetTimeZone<T> toDomain() {
        return new ExternalBudgetTimeZone<T>(new ExtBudgTimeZoneGetMementoImpl<T>(this));
    }

    /**
     * The Class ExtBudgTimeZoneGetMementoImpl.
     *
     * @param <T>
     *            the generic type
     */
    private class ExtBudgTimeZoneGetMementoImpl<T> implements ExtBudgTimeZoneGetMemento<T> {

        /** The dto. */
        private ExternalBudgetTimeDto dto;

        /**
         * Instantiates a new ext budg time zone get memento impl.
         *
         * @param dto
         *            the dto
         */
        public ExtBudgTimeZoneGetMementoImpl(ExternalBudgetTimeDto dto) {
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneGetMemento#getActualValues()
         */
        @Override
        public List<ExternalBudgetTimeZoneVal<T>> getActualValues() {
            List<ExternalBudgetTimeZoneVal<T>> lstValue = new ArrayList<>();
            for (Entry<Integer, Long> entry : this.dto.mapValue.entrySet()) {
                int periodTime = entry.getKey();
                Long value = entry.getValue();
                lstValue.add(new ExternalBudgetTimeZoneVal<T>(new ExternalBudgetTimeZoneValImpl<T>(this.dto.budgetAtr,
                        periodTime, value)));
            }
            return lstValue;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneGetMemento#getExtBudgetCode()
         */
        @Override
        public ExternalBudgetCd getExtBudgetCode() {
            return new ExternalBudgetCd(this.dto.extBudgetCode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneGetMemento#getActualDate()
         */
        @Override
        public Date getActualDate() {
            return this.dto.actualDate;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneGetMemento#getWorkplaceId()
         */
        @Override
        public String getWorkplaceId() {
            return this.dto.workplaceId;
        }

    }

    /**
     * The Class ExternalBudgetTimeZoneValImpl.
     *
     * @param <T>
     *            the generic type
     */
    private class ExternalBudgetTimeZoneValImpl<T> implements ExtBudgTimeZoneValGetMemento<T> {

        /** The budget atr. */
        private BudgetAtr budgetAtr;
        
        /** The period time. */
        private Integer periodTime;

        /** The value. */
        private Long value;

        /**
         * Instantiates a new external budget time zone val impl.
         *
         * @param periodTime
         *            the period time
         * @param value
         *            the value
         */
        public ExternalBudgetTimeZoneValImpl(BudgetAtr budgetAtr, Integer periodTime, Long value) {
            this.budgetAtr = budgetAtr;
            this.periodTime = periodTime;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneValGetMemento#getTimePeriod()
         */
        @Override
        public int getTimePeriod() {
            return this.periodTime;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExtBudgTimeZoneValGetMemento#getActualValue()
         */
        @SuppressWarnings("unchecked")
        @Override
        public ExternalBudgetVal<T> getActualValue() {
            switch (this.budgetAtr) {
            case TIME:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetTime(this.value.intValue()));
            case PEOPLE:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetNumberPerson(this.value.intValue()));
            case MONEY:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetMoney(this.value.intValue()));
            case NUMERICAL:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetNumericalVal(this.value.intValue()));
            case PRICE:
                return new ExternalBudgetVal<T> ((T) new ExtBudgetUnitPrice(this.value.intValue()));
            default:
                throw new RuntimeException("Not budget atr suitable.");
            }
        }

    }
}
