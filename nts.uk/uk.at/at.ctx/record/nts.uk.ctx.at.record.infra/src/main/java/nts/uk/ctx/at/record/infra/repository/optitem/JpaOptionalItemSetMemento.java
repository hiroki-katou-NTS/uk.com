/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem;

import nts.uk.ctx.at.record.dom.optitem.CalcResultRange;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemName;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.record.dom.optitem.UnitOfOptionalItem;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItem;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaOptionalItemSetMemento.
 */
public class JpaOptionalItemSetMemento implements OptionalItemSetMemento {

	/** The type value. */
	private KrcstOptionalItem entity;

	/**
	 * Instantiates a new jpa optional item set memento.
	 *
	 * @param entity the entity
	 */
	public JpaOptionalItemSetMemento(KrcstOptionalItem entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setCompanyId(nts.
	 * uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		this.entity.getKrcstOptionalItemPK().setCid(comId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setOptionalItemNo
	 * (nts.uk.ctx.at.record.dom.optitem.OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optionalItemNo) {
		this.entity.getKrcstOptionalItemPK().setOptionalItemNo(optionalItemNo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemName(nts.uk.ctx.at.record.dom.optitem.OptionalItemName)
	 */
	@Override
	public void setOptionalItemName(OptionalItemName optionalItemName) {
		this.entity.setOptionalItemName(optionalItemName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemAtr(nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr)
	 */
	@Override
	public void setOptionalItemAtr(OptionalItemAtr optionalItemAtr) {
		this.entity.setOptionalItemAtr(optionalItemAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemUsageAtr(nts.uk.ctx.at.record.dom.optitem.
	 * OptionalItemUsageAtr)
	 */
	@Override
	public void setOptionalItemUsageAtr(OptionalItemUsageAtr optionalItemUsageAtr) {
		this.entity.setUsageAtr(optionalItemUsageAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setEmpConditionAtr(nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr)
	 */
	@Override
	public void setEmpConditionAtr(EmpConditionAtr empConditionAtr) {
		this.entity.setEmpConditionAtr(empConditionAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setPerformanceAtr
	 * (nts.uk.ctx.at.record.dom.optitem.PerformanceAtr)
	 */
	@Override
	public void setPerformanceAtr(PerformanceAtr performanceAtr) {
		this.entity.setPerformanceAtr(performanceAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setCalculationResultRange(nts.uk.ctx.at.record.dom.optitem.
	 * CalcResultRange)
	 */
	@Override
	public void setCalculationResultRange(CalcResultRange calculationResultRange) {
		KrcstCalcResultRange entityRange = this.entity.getKrcstCalcResultRange();
		calculationResultRange.saveToMemento(new JpaCalcResultRangeSetMemento(entityRange));
		this.entity.setKrcstCalcResultRange(entityRange);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#setUnit(nts.uk.
	 * ctx.at.record.dom.optitem.UnitOfOptionalItem)
	 */
	@Override
	public void setUnit(UnitOfOptionalItem unit) {
		this.entity.setUnitOfOptionalItem(unit.v());
	}

}
