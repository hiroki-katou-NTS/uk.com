/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.error;

import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetAccDate;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetErrorContent;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExtBudgetWorkplaceCode;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error.KscdtExtBudgetError;

/**
 * The Class JpaExternalBudgetErrorGetMemento.
 */
public class JpaExternalBudgetErrorGetMemento implements ExternalBudgetErrorGetMemento {

    /** The entity. */
    private KscdtExtBudgetError entity;

    /**
     * Instantiates a new jpa external budget error get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetErrorGetMemento(KscdtExtBudgetError entity) {
        this.entity = entity;
    }

    /**
     * Gets the error content.
     *
     * @return the error content
     */
    @Override
    public ExtBudgetErrorContent getErrorContent() {
        return new ExtBudgetErrorContent(this.entity.getErrContent());
    }

    /**
     * Gets the number column.
     *
     * @return the number column
     */
    @Override
    public int getColumnNo() {
        return this.entity.getKscdtExtBudgetErrorPK().getColumnNo();
    }

    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    @Override
    public ExtBudgetActualValue getActualValue() {
        return new ExtBudgetActualValue(this.entity.getAcceptedVal());
    }

    /**
     * Gets the accepted date.
     *
     * @return the accepted date
     */
    @Override
    public ExtBudgetAccDate getAcceptedDate() {
        return new ExtBudgetAccDate(this.entity.getAcceptedD());
    }

    /**
     * Gets the work place code.
     *
     * @return the work place code
     */
    @Override
    public ExtBudgetWorkplaceCode getWorkPlaceCode() {
        return new ExtBudgetWorkplaceCode(this.entity.getAcceptedWkpcd());
    }

    /**
     * Gets the execution id.
     *
     * @return the execution id
     */
    @Override
    public String getExecutionId() {
        return this.entity.getKscdtExtBudgetErrorPK().getExeId();
    }

    /**
     * Gets the number line.
     *
     * @return the number line
     */
    @Override
    public int getLineNo() {
        return this.entity.getKscdtExtBudgetErrorPK().getLineNo();
    }

}
