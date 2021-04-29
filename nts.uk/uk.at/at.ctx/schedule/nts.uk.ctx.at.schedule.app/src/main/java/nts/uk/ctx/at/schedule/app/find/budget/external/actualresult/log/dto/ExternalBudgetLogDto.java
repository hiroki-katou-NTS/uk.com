/******************************************************************
\ * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.log.dto;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExtBudgetFileName;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLogSetMemento;

/**
 * The Class ExternalBudgetLogDto.
 */
public class ExternalBudgetLogDto implements ExternalBudgetLogSetMemento {

    /** The execute id. */
    public String executeId;

    /** The start date. */
    public String startDate;

    /** The end date. */
    public String endDate;

    /** The ext budget name. */
    public String extBudgetName;

    /** The file name. */
    public String fileName;

    /** The status. */
    public Integer statusVal;
    
    /** The status des.
     *  Show name JP 
     * */
    public String statusDes;

    /** The number success. */
    public int numberSuccess;

    /** The number fail. */
    public int numberFail;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setExternalBudgetFileName(nts.uk.ctx.at.
     * schedule.dom.budget.external.actualresult.ExtBudgetFileName)
     */
    @Override
    public void setExternalBudgetFileName(ExtBudgetFileName externalBudgetFileName) {
        this.fileName = externalBudgetFileName.v();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setExternalBudgetCode(nts.uk.ctx.at.schedule.
     * dom.budget.external.ExternalBudgetCd)
     */
    @Override
    public void setExternalBudgetCode(ExternalBudgetCd externalBudgetCode) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setNumberFail(int)
     */
    @Override
    public void setNumberFail(int numberFail) {
        this.numberFail = numberFail;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setCompletionState(nts.uk.ctx.at.schedule.dom
     * .budget.external.actualresult.CompletionState)
     */
    @Override
    public void setCompletionState(CompletionState completionState) {
        this.statusVal = completionState.value;
        this.statusDes = completionState.description;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setExecutionId(java.lang.String)
     */
    @Override
    public void setExecutionId(String executionId) {
        this.executeId = executionId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setExecuteTime(nts.uk.ctx.at.schedule.dom.
     * budget.external.actualresult.ExecutionTime)
     */
    @Override
    public void setExecuteTime(ExecutionTime executeTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.startDate = simpleDateFormat.format(
                Date.from(executeTime.getStartDateTime().localDateTime().atZone(ZoneId.systemDefault()).toInstant()));
        this.endDate = simpleDateFormat.format(
                Date.from(executeTime.getEndDateTime().localDateTime().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setNumberSuccess(int)
     */
    @Override
    public void setNumberSuccess(int numberSuccess) {
        this.numberSuccess = numberSuccess;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetLogSetMemento#setEmployeeId(java.lang.String)
     */
    @Override
    public void setEmployeeId(String employeeId) {
    }

}
