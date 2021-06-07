/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import lombok.Builder;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetAccDate;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetErrorContent;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetWorkplaceCode;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetError;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorGetMemento;

/**
 * The Class ExternalBudgetErrorDto.
 */
@Builder
public class ExternalBudgetErrorDto {

    /** The error content. */
    public String errorContent;

    /** The column no. */
    public int columnNo;

    /** The actual value. */
    public String actualValue;

    /** The accepted date. */
    public String acceptedDate;

    /** The workplace code. */
    public String workplaceCode;

    /** The execution id. */
    public String executionId;

    /** The line no. */
    public int lineNo;

    /**
     * To domain.
     *
     * @return the external budget error
     */
    public ExternalBudgetError toDomain() {
        return new ExternalBudgetError(new ExternalBudgetErrorGetMementoImpl(this));
    }

    /**
     * The Class ExternalBudgetErrorGetMementoImpl.
     */
    private class ExternalBudgetErrorGetMementoImpl implements ExternalBudgetErrorGetMemento {

        /** The dto. */
        private ExternalBudgetErrorDto dto;

        /**
         * Instantiates a new external budget error get memento impl.
         *
         * @param dto
         *            the dto
         */
        public ExternalBudgetErrorGetMementoImpl(ExternalBudgetErrorDto dto) {
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getErrorContent()
         */
        @Override
        public ExtBudgetErrorContent getErrorContent() {
            return new ExtBudgetErrorContent(this.dto.errorContent);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getNumberColumn()
         */
        @Override
        public int getColumnNo() {
            return this.dto.columnNo;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getActualValue()
         */
        @Override
        public ExtBudgetActualValue getActualValue() {
            return new ExtBudgetActualValue(this.dto.actualValue);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getAcceptedDate()
         */
        @Override
        public ExtBudgetAccDate getAcceptedDate() {
            return new ExtBudgetAccDate(this.dto.acceptedDate);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getWorkPlaceCode()
         */
        @Override
        public ExtBudgetWorkplaceCode getWorkPlaceCode() {
            return new ExtBudgetWorkplaceCode(this.dto.workplaceCode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getExecutionId()
         */
        @Override
        public String getExecutionId() {
            return this.dto.executionId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetErrorGetMemento#getNumberLine()
         */
        @Override
        public int getLineNo() {
            return this.dto.lineNo;
        }

    }
}
