package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnnualHolidaySetOutput {
	/**
	 * 年休管理区分(true, false)
	 */
	private boolean yearHolidayManagerFlg;
	/**
	 * 半休上限管理区分(true, false)
	 */
	private boolean halfManageCategoryFlg;
	/**
	 * 半休上限日数(int)
	 */
	private int halfHolidayLitmit;
	/**
	 * 時間年休管理区分(true, false)
	 */
	private boolean suspensionTimeYearFlg;
	
	/**
	 * 時間年休上限管理区分
	 */
	private boolean hoursHolidayType;
	/**
	 * 時間年休上限日数(int)
	 */
	private int hoursHolidayLimit;
	/**
	 * 時間年休消化単位(int)
	 */
	private int timeYearRest;
}
