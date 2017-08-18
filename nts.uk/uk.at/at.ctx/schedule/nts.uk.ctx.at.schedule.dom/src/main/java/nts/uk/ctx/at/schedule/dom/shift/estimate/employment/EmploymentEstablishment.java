/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.estimate.employment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.schedule.dom.shift.estimate.EstimateDetailSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.Year;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Class EmploymentEstablishment.
 */
// 雇用目安設定
@Getter
public class EmploymentEstablishment extends AggregateRoot{
	/** The company id. */
	//会社ID
	private CompanyId companyId;

	/** The target year. */
	// 対象年
	private Year targetYear;
	
	/** The advanced setting. */
	//詳細設定
	private EstimateDetailSetting advancedSetting;
	
	/** The employment code. */
	//雇用コード
	private EmploymentCode employmentCode;
}
