/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSort;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.disporder.KrcmtAnyfSortPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.DispOrder;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder.FormulaDispOrderSetMemento;

/**
 * The Class JpaFormulaDispOrderSetMemento.
 */
public class JpaFormulaDispOrderSetMemento implements FormulaDispOrderSetMemento {

	/** The type value. */
	private KrcmtAnyfSort typeValue;

	/**
	 * Instantiates a new jpa formula disp order set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaFormulaDispOrderSetMemento(KrcmtAnyfSort typeValue) {
		KrcmtAnyfSortPK krcmtAnyfSortPK = typeValue.getKrcmtAnyfSortPK();

		// Check PK exist
		if (krcmtAnyfSortPK == null) {
			krcmtAnyfSortPK = new KrcmtAnyfSortPK();
		}

		typeValue.setKrcmtAnyfSortPK(krcmtAnyfSortPK);

		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		KrcmtAnyfSortPK krcmtAnyfSortPK = typeValue.getKrcmtAnyfSortPK();
		krcmtAnyfSortPK.setCid(comId.v());
		this.typeValue.setKrcmtAnyfSortPK(krcmtAnyfSortPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.applicable.EmpConditionSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optNo) {
		KrcmtAnyfSortPK krcmtAnyfSortPK = typeValue.getKrcmtAnyfSortPK();
		krcmtAnyfSortPK.setOptionalItemNo(optNo.v());
		this.typeValue.setKrcmtAnyfSortPK(krcmtAnyfSortPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderSetMemento#setOptionalItemFormulaId(nts.uk.ctx.at.record.
	 * dom.optitem.calculation.FormulaId)
	 */
	@Override
	public void setOptionalItemFormulaId(FormulaId formulaId) {
		KrcmtAnyfSortPK krcmtAnyfSortPK = typeValue.getKrcmtAnyfSortPK();
		krcmtAnyfSortPK.setFormulaId(formulaId.v());
		this.typeValue.setKrcmtAnyfSortPK(krcmtAnyfSortPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.disporder.
	 * FormulaDispOrderSetMemento#setDispOrder(nts.uk.ctx.at.record.dom.optitem.
	 * calculation.disporder.DispOrder)
	 */
	@Override
	public void setDispOrder(DispOrder dispOrder) {
		this.typeValue.setDisporder(dispOrder.v());
	}

}
