package nts.uk.ctx.at.record.dom.adapter.application.setting;

import lombok.AllArgsConstructor;

/**
 * 休暇申請の種類
 * 
 * @author thanhnx
 *
 */
@AllArgsConstructor
public enum HolidayAppTypeRC {
	/**
	 * 年次有休
	 */
	ANNUAL_PAID_LEAVE(0, "年次有休"),
	/**
	 * 代休
	 */
	SUBSTITUTE_HOLIDAY(1, "代休"),
	/**
	 * 欠勤
	 */
	ABSENCE(2, "欠勤"),
	/**
	 * 特別休暇
	 */
	SPECIAL_HOLIDAY(3, "特別休暇"),
	/**
	 * 積立年休
	 */
	YEARLY_RESERVE(4, "積立年休"),
	/**
	 * 休日
	 */
	HOLIDAY(5, "休日"),
	/**
	 * 時間消化
	 */
	DIGESTION_TIME(6, "時間消化");

	public final int value;

	public final String name;
}
