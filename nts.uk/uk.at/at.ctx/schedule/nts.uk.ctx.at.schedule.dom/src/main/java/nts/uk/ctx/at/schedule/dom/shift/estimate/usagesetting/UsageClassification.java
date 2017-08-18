/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageClassification.
 */
// 目安利用区分
@Getter
public class UsageClassification extends DomainObject {

	/** The company id. */
	// 会社ID
	private CompanyId companyId;

	/** The employment setting. */
	// 雇用利用設定
	private UseClassification employmentSetting;

	/** The personal usage setting. */
	// 個人利用設定
	private UseClassification personalSetting;
}
