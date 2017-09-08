/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.timeitemmanagement;

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

	/** The emp condition classification. */
	// 雇用条件区分
	private EmpConditionClassification empConditionClassification;

	/** The optional item atr. */
	// 属性
	private OptionalItemAttribute optionalItemAtr;

	/** The optional item name. */
	// 任意項目名称
	private OptionalItemName optionalItemName;

	/** The optional item no. */
	// 任意項目NO
	private OptionalItemNo optionalItemNo;

	/** The performance classification. */
	// 実績区分
	private PerformanceClassification performanceClassification;

	/** The usage classification. */
	// 任意項目利用区分
	private OptionalItemUsageClassification usageClassification;

	/** The calculation result range. */
	// 計算結果の範囲
	private CalculationResultRange calculationResultRange;

}
