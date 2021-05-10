/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.error.dto;

import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetAccDate;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetErrorContent;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetWorkplaceCode;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorSetMemento;

/**
 * The Class ExternalBudgetErrorDto.
 */
public class ExternalBudgetErrorDto implements ExternalBudgetErrorSetMemento {

    /** The line no. */
    public int lineNo;
    
    /** The column no. */
    public int columnNo;

    /** The workplace code. */
    public String wpkCode;

    /** The actual value. */
    public String actualValue;

    /** The accepted date. */
    public String acceptedDate;
    
    /** The error content. */
    public String errorContent;

    @Override
    public void setErrorContent(ExtBudgetErrorContent errorContent) {
        this.errorContent = errorContent.v();
    }

    @Override
    public void setColumnNo(int numberColumn) {
        this.columnNo = numberColumn;
    }

    @Override
    public void setActualValue(ExtBudgetActualValue actualValue) {
        this.actualValue = actualValue.v();
    }

    @Override
    public void setAcceptedDate(ExtBudgetAccDate acceptedDate) {
        this.acceptedDate = acceptedDate.v();
    }

    @Override
    public void setWorkPlaceCode(ExtBudgetWorkplaceCode workPlaceCode) {
        this.wpkCode = workPlaceCode.v();
    }

    @Override
    public void setExecutionId(String executionId) {
    }

    @Override
    public void setLineNo(int numberLine) {
        this.lineNo = numberLine;
    }

}
