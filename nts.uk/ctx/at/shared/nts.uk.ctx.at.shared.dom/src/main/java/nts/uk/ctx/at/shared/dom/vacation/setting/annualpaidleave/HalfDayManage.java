/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class HalfDayManage.
 */
@Builder
public class HalfDayManage {

	/** The maximum day. */
	public Integer maximumDay; // Not mapping UI.

	/** The manage type. */
	public ManageDistinct manageType;

	/** The reference. */
	public MaxDayReference reference;

	/** The max number uniform company. */
	public AnnualNumberDay maxNumberUniformCompany;
}
