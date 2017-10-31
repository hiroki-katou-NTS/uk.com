package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.setting;

import lombok.AllArgsConstructor;

/**
 * 公休繰越期限
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum PublicHolidayCarryOverDeadline {
	// 1ヶ月
	ONE_MONTH(0),
	// 2ヶ月
	TWO_MONTHS(1),
	// 3ヶ月
	THREE_MONTHS(2),
	// 4ヶ月
	FOUR_MONTHS(3),
	// 5ヶ月
	FIVE_MONTHS(4),
	// 6ヶ月
	SIX_MONTHS(5),
	// 7ヶ月
	SEVEN_MONTHS(6),
	// 8ヶ月
	EIGHT_MONTHS(7),
	// 9ヶ月
	NINE_MONTHS(8),
	// 10ヶ月
	TEN_MONTHS(9),
	// 11ヶ月
	ELEVEN_MONTHS(10),
	// 12ヶ月
	TWELVE_MONTHS(11),
	// 年度末
	YEAR_END(12),
	// 無期限
	INDEFINITE(13),
	// 当月
	CURRENT_MONTH(14);

	public final int value;

}
