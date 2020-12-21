/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import lombok.Builder;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class YearVacationTimeMaxDay.
 */
@Builder
public class TimeAnnualMaxDay implements Serializable{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The manage max day vacation. */
    // 管理区分
	public ManageDistinct manageType;

	/** The reference. */
	// 参照先
	public MaxDayReference reference;

	/** The max time day. */
	// 会社一律上限日数
	public MaxTimeDay maxNumberUniformCompany;
}
