/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageClassification;

/**
 * The Class Establishment.
 */
// 目安設定
@Getter
public class Establishment extends AggregateRoot {

	/** The person establishment. */
	// 個人目安設定
	private PersonEstablishment personEstablishment;
	
	/** The whole company establishment. */
	// 全社目安設定
	private WholeCompanyEstablishment wholeCompanyEstablishment;
	
	
	/** The usage classification. */
	// 目安利用区分
	private UsageClassification usageClassification;

	/** The employment establishment. */
	// 雇用目安設定
	private EmploymentEstablishment employmentEstablishment;
}
