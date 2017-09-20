/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.optitem;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.optitem.CalcResultRange;
import nts.uk.ctx.at.record.dom.optitem.EmpConditionAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemName;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemSetMemento;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemUsageAtr;
import nts.uk.ctx.at.record.dom.optitem.PerformanceAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OptionalItemDto.
 */
@Getter
@Setter
public class OptionalItemDto implements OptionalItemSetMemento {

	/** The optional item no. */
	// 任意項目NO
	private String optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private String optionalItemName;

	/** The optional item atr. */
	// 属性
	private int optionalItemAtr;

	/** The usage classification. */
	// 任意項目利用区分
	private int usageAtr;

	/** The emp condition classification. */
	// 雇用条件区分
	private int empConditionAtr;

	/** The performance classification. */
	// 実績区分
	private int performanceAtr;

	/** The calculation result range. */
	// 計算結果の範囲
	private CalcResultRangeDto calcResultRange;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId comId) {
		// Not used.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setOptionalItemNo(nts.uk.ctx.at.shared.dom.timeitemmanagement.
	 * OptionalItemNo)
	 */
	@Override
	public void setOptionalItemNo(OptionalItemNo optionalItemNo) {
		this.optionalItemNo = optionalItemNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setOptionalItemName(nts.uk.ctx.at.shared.dom.timeitemmanagement.
	 * OptionalItemName)
	 */
	@Override
	public void setOptionalItemName(OptionalItemName optionalItemName) {
		this.optionalItemName = optionalItemName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setOptionalItemAttribute(nts.uk.ctx.at.shared.dom.timeitemmanagement.
	 * OptionalItemAttribute)
	 */
	@Override
	public void setOptionalItemAtr(OptionalItemAtr optionalItemAttribute) {
		this.optionalItemAtr = optionalItemAttribute.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setOptionalItemUsageClassification(nts.uk.ctx.at.shared.dom.
	 * timeitemmanagement.OptionalItemUsageClassification)
	 */
	@Override
	public void setOptionalItemUsageAtr(OptionalItemUsageAtr optionalItemUsageClassification) {
		this.usageAtr = optionalItemUsageClassification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setEmpConditionClassification(nts.uk.ctx.at.shared.dom.timeitemmanagement
	 * .EmpConditionClassification)
	 */
	@Override
	public void setEmpConditionAtr(EmpConditionAtr empConditionClassification) {
		this.empConditionAtr = empConditionClassification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setPerformanceClassification(nts.uk.ctx.at.shared.dom.timeitemmanagement.
	 * PerformanceClassification)
	 */
	@Override
	public void setPerformanceAtr(PerformanceAtr performanceClassification) {
		this.performanceAtr = performanceClassification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.timeitemmanagement.OptionalItemSetMemento#
	 * setCalculationResultRange(nts.uk.ctx.at.shared.dom.timeitemmanagement.
	 * CalculationResultRange)
	 */
	@Override
	public void setCalculationResultRange(CalcResultRange calculationResultRange) {
		calculationResultRange.saveToMemento(this.calcResultRange);
	}
}
