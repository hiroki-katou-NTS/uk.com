/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;

/**
 * The Class FormulaDispOrder.
 */
// 任意項目計算式の並び順
// 責務 : 計算式で使用する順番を管理する
// Responsibility: Manage the order of use in calculation formulas
@Getter
public class FormulaDispOrder extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The optional item formula id. */
	// 任意項目計算式ID
	private FormulaId optionalItemFormulaId;

	/** The disp order. */
	// 並び順
	private DispOrder dispOrder;

	/**
	 * Instantiates a new formula disp order.
	 *
	 * @param memento the memento
	 */
	public FormulaDispOrder(FormulaDispOrderGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.optionalItemFormulaId = memento.getFormulaId();
		this.optionalItemNo = memento.getOptionalItemNo();
		this.dispOrder = memento.getDispOrder();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FormulaDispOrderSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemFormulaId(this.optionalItemFormulaId);
		memento.setOptionalItemNo(this.optionalItemNo);
		memento.setDispOrder(this.dispOrder);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((optionalItemFormulaId == null) ? 0 : optionalItemFormulaId.hashCode());
		result = prime * result + ((optionalItemNo == null) ? 0 : optionalItemNo.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormulaDispOrder other = (FormulaDispOrder) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (optionalItemFormulaId == null) {
			if (other.optionalItemFormulaId != null)
				return false;
		} else if (!optionalItemFormulaId.equals(other.optionalItemFormulaId))
			return false;
		if (optionalItemNo == null) {
			if (other.optionalItemNo != null)
				return false;
		} else if (!optionalItemNo.equals(other.optionalItemNo))
			return false;
		return true;
	}

}
