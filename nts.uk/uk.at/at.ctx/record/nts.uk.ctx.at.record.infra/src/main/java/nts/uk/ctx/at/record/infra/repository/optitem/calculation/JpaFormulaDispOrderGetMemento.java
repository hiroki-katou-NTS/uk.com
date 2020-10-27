/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSort;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.DispOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrderGetMemento;

/**
 * The Class JpaFormulaDispOrderGetMemento.
 */
public class JpaFormulaDispOrderGetMemento implements FormulaDispOrderGetMemento {

	/** The type value. */
	private KrcmtAnyfSort typeValue;

	/**
	 * Instantiates a new jpa formula disp order get memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaFormulaDispOrderGetMemento(KrcmtAnyfSort typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.typeValue.getKrcmtAnyfSortPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionGetMemento#
	 * getOptionalItemNo()
	 */
	@Override
	public OptionalItemNo getOptionalItemNo() {
		return new OptionalItemNo(this.typeValue.getKrcmtAnyfSortPK().getOptionalItemNo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderGetMemento#getFormulaId()
	 */
	@Override
	public FormulaId getFormulaId() {
		return new FormulaId(this.typeValue.getKrcmtAnyfSortPK().getFormulaId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderGetMemento#getDispOrder()
	 */
	@Override
	public DispOrder getDispOrder() {
		return new DispOrder(this.typeValue.getDisporder());
	}

}
