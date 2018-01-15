/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktype.holidayset;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;

/**
 * The Class HolidaySetting.
 */
@Getter
// 休日設定
public class HolidaySetting {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The is public holiday. */
	// 公休を消化する
	private Boolean isPublicHoliday;

	/** The holiday atr. */
	// 休日区分
	private HolidayAtr holidayAtr;

	/**
	 * Instantiates a new holiday setting.
	 *
	 * @param companyId the company id
	 * @param isPublicHoliday the is public holiday
	 * @param holidayAtr the holiday atr
	 */
	public HolidaySetting(String companyId, Boolean isPublicHoliday, HolidayAtr holidayAtr) {
		super();
		this.companyId = companyId;
		this.isPublicHoliday = isPublicHoliday;
		this.holidayAtr = holidayAtr;
	}
	
}
