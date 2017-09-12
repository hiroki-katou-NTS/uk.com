/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord;

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
	private OptionalItemAttribute optionalItemAtr;

	/** The usage classification. */
	// 任意項目利用区分
	private OptionalItemUsageClassification usageClassification;

	/** The emp condition classification. */
	// 雇用条件区分
	private EmpConditionClassification empConditionClassification;

	/** The performance classification. */
	// 実績区分
	private PerformanceClassification performanceClassification;

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
		this.optionalItemAtr = memento.getOptionalItemAttribute();
		this.usageClassification = memento.getOptionalItemUsageClassification();
		this.empConditionClassification = memento.getEmpConditionClassification();
		this.performanceClassification = memento.getPerformanceClassification();
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
		memento.setOptionalItemAttribute(this.optionalItemAtr);
		memento.setOptionalItemName(this.optionalItemName);
		memento.setOptionalItemUsageClassification(this.usageClassification);
		memento.setEmpConditionClassification(this.empConditionClassification);
		memento.setPerformanceClassification(this.performanceClassification);
		memento.setCalculationResultRange(this.calculationResultRange);
	}

}
