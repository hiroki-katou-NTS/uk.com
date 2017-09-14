/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OptionalItem.
 */
// 任意項目
// 責務 : 帳票で使用する項目を設定する
// Responsibility: Set items to be used in the form
@Getter
public class OptionalItem extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The optional item name. */
	// 任意項目名称
	private OptionalItemName optionalItemName;

	/** The optional item atr. */
	// 属性
	private OptionalItemAtr optionalItemAtr;

	/** The usage atr. */
	// 任意項目利用区分
	private OptionalItemUsageAtr usageAtr;

	/** The emp condition atr. */
	// 雇用条件区分
	private EmpConditionAtr empConditionAtr;

	/** The performance atr. */
	// 実績区分
	private PerformanceAtr performanceAtr;

	/** The calculation result range. */
	// 計算結果の範囲
	private CalculationResultRange calculationResultRange;

	/**
	 * Instantiates a new optional item.
	 *
	 * @param memento the memento
	 */
	public OptionalItem(OptionalItemGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.optionalItemNo = memento.getOptionalItemNo();
		this.optionalItemName = memento.getOptionalItemName();
		this.optionalItemAtr = memento.getOptionalItemAtr();
		this.usageAtr = memento.getOptionalItemUsageAtr();
		this.empConditionAtr = memento.getEmpConditionAtr();
		this.performanceAtr = memento.getPerformanceAtr();
		this.calculationResultRange = memento.getCalculationResultRange();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OptionalItemSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOptionalItemNo(this.optionalItemNo);
		memento.setOptionalItemAtr(this.optionalItemAtr);
		memento.setOptionalItemName(this.optionalItemName);
		memento.setOptionalItemUsageAtr(this.usageAtr);
		memento.setEmpConditionAtr(this.empConditionAtr);
		memento.setPerformanceAtr(this.performanceAtr);
		memento.setCalculationResultRange(this.calculationResultRange);
	}

}
