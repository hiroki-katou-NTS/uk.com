/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import java.util.Date;

import lombok.Builder;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDaily;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetDailyGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetVal;

/**
 * The Class ExternalBudgetDailyDto.
 */
@Builder
public class ExternalBudgetDailyDto {

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
        @Override
        public ExternalBudgetVal<T> getActualValue() {
            return new ExternalBudgetVal<T>(this.dto.actualValue);
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
