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
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstCalcResultRange;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItem;
import nts.uk.ctx.at.record.infra.entity.optitem.KrcstOptionalItemPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaOptionalItemSetMemento.
 */
public class JpaOptionalItemSetMemento implements OptionalItemSetMemento {

	/** The type value. */
	private KrcstOptionalItem typeValue;

	/**
	 * Instantiates a new jpa optional item set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaOptionalItemSetMemento(KrcstOptionalItem typeValue) {

		KrcstOptionalItemPK krcstOptionalItemPK = typeValue.getKrcstOptionalItemPK();

		// Check PK exist
		if (krcstOptionalItemPK == null) {
			krcstOptionalItemPK = new KrcstOptionalItemPK();
		}

		typeValue.setKrcstOptionalItemPK(krcstOptionalItemPK);

		this.typeValue = typeValue;
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
		KrcstOptionalItemPK krcstOptionalItemPK = typeValue.getKrcstOptionalItemPK();
		krcstOptionalItemPK.setCid(comId.v());
		this.typeValue.setKrcstOptionalItemPK(krcstOptionalItemPK);
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
		KrcstOptionalItemPK krcstOptionalItemPK = typeValue.getKrcstOptionalItemPK();
		krcstOptionalItemPK.setOptionalItemNo(optionalItemNo.v());
		this.typeValue.setKrcstOptionalItemPK(krcstOptionalItemPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemName(nts.uk.ctx.at.record.dom.optitem.OptionalItemName)
	 */
	@Override
	public void setOptionalItemName(OptionalItemName optionalItemName) {
		this.typeValue.setOptionalItemName(optionalItemName.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setOptionalItemAtr(nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr)
	 */
	@Override
	public void setOptionalItemAtr(OptionalItemAtr optionalItemAtr) {
		this.typeValue.setOptionalItemAtr(optionalItemAtr.value);
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
		this.typeValue.setUsageAtr(optionalItemUsageAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento#
	 * setEmpConditionAtr(nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr)
	 */
	@Override
	public void setEmpConditionAtr(EmpConditionAtr empConditionAtr) {
		this.typeValue.setEmpConditionAtr(empConditionAtr.value);
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
		this.typeValue.setPerformanceAtr(performanceAtr.value);
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
		KrcstCalcResultRange krcstCalcResultRange = this.typeValue.getKrcstCalcResultRange();
		if (krcstCalcResultRange == null) {
			krcstCalcResultRange = new KrcstCalcResultRange();
		}
		calculationResultRange
				.saveToMemento(new JpaCalcResultRangeSetMemento(krcstCalcResultRange));
		this.typeValue.setKrcstCalcResultRange(krcstCalcResultRange);
	}

}
