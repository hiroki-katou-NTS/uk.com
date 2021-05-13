/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.budget.external.actualresult.dto;

import lombok.Builder;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTimeGetMemento;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExtBudgetFileName;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLog;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLogGetMemento;

/**
 * The Class ExternalBudgetLogDto.
 */
@Builder
public class ExternalBudgetLogDto {

    /** The ext budget file name. */
    public String extBudgetFileName;

    /** The ext budget code. */
    public String extBudgetCode;

    /** The number fail. */
    public int numberFail;

    /** The completion state. */
    public CompletionState completionState;

    /** The execution id. */
    public String executionId;

    /** The start date time. */
    public GeneralDateTime startDateTime;

    /** The end date time. */
    public GeneralDateTime endDateTime;

    /** The number success. */
    public int numberSuccess;

    /** The employee id. */
    public String employeeId;
    
    /**
     * Copy.
     *
     * @param log the log
     * @return the external budget log dto
     */
    
    public static ExternalBudgetLogDto copy(ExternalBudgetLog log) {
        return ExternalBudgetLogDto.builder()
                .executionId(log.getExecutionId())
                .employeeId(log.getEmployeeId())
                .startDateTime(log.getExecuteTime().getStartDateTime())
                .endDateTime(log.getExecuteTime().getEndDateTime())
                .extBudgetCode(log.getExtBudgetCode().v())
                .extBudgetFileName(log.getExtBudgetFileName().v())
                .completionState(log.getCompletionState())
                .numberSuccess(log.getNumberSuccess())
                .numberFail(log.getNumberFail())
                .build();
    }
    
    public ExternalBudgetLog toDomain() {
        return new ExternalBudgetLog(new ExternalBudgetLogImpl(this));
    }

    /**
     * The Class ExternalBudgetLogImpl.
     */
    private class ExternalBudgetLogImpl implements ExternalBudgetLogGetMemento {

        /** The dto. */
        private ExternalBudgetLogDto dto;

        /**
         * Instantiates a new external budget log impl.
         *
         * @param dto
         *            the dto
         */
        public ExternalBudgetLogImpl(ExternalBudgetLogDto dto) {
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getExternalBudgetFileName()
         */
        @Override
        public ExtBudgetFileName getExternalBudgetFileName() {
            return new ExtBudgetFileName(this.dto.extBudgetFileName);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getExternalBudgetCode()
         */
        @Override
        public ExternalBudgetCd getExternalBudgetCode() {
            return new ExternalBudgetCd(this.dto.extBudgetCode);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getNumberFail()
         */
        @Override
        public int getNumberFail() {
            return this.dto.numberFail;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getCompletionState()
         */
        @Override
        public CompletionState getCompletionState() {
            return this.dto.completionState;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getExecutionId()
         */
        @Override
        public String getExecutionId() {
            return this.dto.executionId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getExecuteTime()
         */
        @Override
        public ExecutionTime getExecuteTime() {
            return new ExecutionTime(new ExecutionTimeImpl(this.dto));
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getNumberSuccess()
         */
        @Override
        public int getNumberSuccess() {
            return this.dto.numberSuccess;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExternalBudgetLogGetMemento#getEmployeeId()
         */
        @Override
        public String getEmployeeId() {
            return this.dto.employeeId;
        }
    }

    /**
     * The Class ExecutionTimeImpl.
     */
    private class ExecutionTimeImpl implements ExecutionTimeGetMemento {

        /** The dto. */
        private ExternalBudgetLogDto dto;

        /**
         * Instantiates a new execution time impl.
         *
         * @param dto
         *            the dto
         */
        public ExecutionTimeImpl(ExternalBudgetLogDto dto) {
            this.dto = dto;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExecutionTimeGetMemento#getStartDateTime()
         */
        @Override
        public GeneralDateTime getStartDateTime() {
            return this.dto.startDateTime;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
         * ExecutionTimeGetMemento#getEndDateTime()
         */
        @Override
        public GeneralDateTime getEndDateTime() {
            return this.dto.endDateTime;
        }

    }
}
