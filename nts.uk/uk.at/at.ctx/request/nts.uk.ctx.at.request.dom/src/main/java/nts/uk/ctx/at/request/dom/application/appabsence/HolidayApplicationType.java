package nts.uk.ctx.at.request.dom.application.appabsence;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * 休暇申請の種類
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum HolidayApplicationType {
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
	DIGESTION_TIME(6, "時間消化"),
	/**
	 * 【廃止】振休
	 */
	REST_TIME(7, "【廃止】振休");
	
	public final int value;
	
	public final String name;
}
