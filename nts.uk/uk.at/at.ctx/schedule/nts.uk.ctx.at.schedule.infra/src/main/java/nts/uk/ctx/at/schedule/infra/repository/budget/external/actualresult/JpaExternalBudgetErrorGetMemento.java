/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import nts.uk.ctx.at.schedule.dom.budget.external.ExtBudgetWorkplaceCode;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetAccDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetActualValue;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetErrorContent;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetErrorGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetError;

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
    public int getNumberColumn() {
        return this.entity.getColumnNo();
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
        return this.entity.getExeId();
    }

    /**
     * Gets the number line.
     *
     * @return the number line
     */
    @Override
    public int getNumberLine() {
        return this.entity.getLineNo();
    }

}
