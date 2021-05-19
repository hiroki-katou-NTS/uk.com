/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.acceptance.error;

import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetAccDate;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetErrorContent;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetWorkplaceCode;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.error.KscdtExtBudgetError;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.error.KscdtExtBudgetErrorPK;

/**
 * The Class JpaExternalBudgetErrorSetMemento.
 */
public class JpaExternalBudgetErrorSetMemento implements ExternalBudgetErrorSetMemento {

    /** The entity. */
    private KscdtExtBudgetError entity;

    /**
     * Instantiates a new jpa external budget error set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetErrorSetMemento(KscdtExtBudgetError entity) {
        if (entity != null && entity.getKscdtExtBudgetErrorPK() == null) {
            entity.setKscdtExtBudgetErrorPK(new KscdtExtBudgetErrorPK());
        }
        this.entity = entity;
    }

    /**
     * Sets the error content.
     *
     * @param errorContent
     *            the new error content
     */
    @Override
    public void setErrorContent(ExtBudgetErrorContent errorContent) {
        this.entity.setErrContent(errorContent.v());
    }

    /**
     * Sets the number column.
     *
     * @param numberColumn
     *            the new number column
     */
    @Override
    public void setColumnNo(int numberColumn) {
        this.entity.getKscdtExtBudgetErrorPK().setColumnNo(numberColumn);
    }

    /**
     * Sets the actual value.
     *
     * @param actualValue
     *            the new actual value
     */
    @Override
    public void setActualValue(ExtBudgetActualValue actualValue) {
        this.entity.setAcceptedVal(actualValue.v());
    }

    /**
     * Sets the accepted date.
     *
     * @param acceptedDate
     *            the new accepted date
     */
    @Override
    public void setAcceptedDate(ExtBudgetAccDate acceptedDate) {
        this.entity.setAcceptedD(acceptedDate.v());
    }

    /**
     * Sets the work place code.
     *
     * @param workPlaceCode
     *            the new work place code
     */
    @Override
    public void setWorkPlaceCode(ExtBudgetWorkplaceCode workPlaceCode) {
        this.entity.setAcceptedWkpcd(workPlaceCode.v());
    }

    /**
     * Sets the execution id.
     *
     * @param executionId
     *            the new execution id
     */
    @Override
    public void setExecutionId(String executionId) {
        this.entity.getKscdtExtBudgetErrorPK().setExeId(executionId);
    }

    /**
     * Sets the number line.
     *
     * @param numberLine
     *            the new number line
     */
    @Override
    public void setLineNo(int numberLine) {
        this.entity.getKscdtExtBudgetErrorPK().setLineNo(numberLine);
    }

}
