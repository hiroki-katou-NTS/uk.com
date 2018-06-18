package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten;

import lombok.Data;

@Data
public class SubstitutionHolidayOutput {
	/**
	 * 代休管理区分(true, false)
	 */
	private boolean substitutionFlg;
	
	/**
	 * 時間代休管理区分(true, false)
	 */
	private boolean timeOfPeriodFlg;
	/**
	 * 時間代休消化単位(int)
	 */
	private int digestiveUnit;
	/**
	 * 代休使用期限
	 */
	private int expirationOfsubstiHoliday;
}
