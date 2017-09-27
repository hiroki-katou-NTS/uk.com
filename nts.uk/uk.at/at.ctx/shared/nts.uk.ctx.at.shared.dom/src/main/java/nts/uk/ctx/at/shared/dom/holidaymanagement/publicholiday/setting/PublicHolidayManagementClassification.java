package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.setting;

import lombok.AllArgsConstructor;

/**
 * 公休管理区分
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum PublicHolidayManagementClassification {
	// 1ヵ月
	ONE_MONTHS(0),
	// 4週4休
	FOUR_WEEKS_FOUR_DAYS_OFF(1);

	public final int value;
}
