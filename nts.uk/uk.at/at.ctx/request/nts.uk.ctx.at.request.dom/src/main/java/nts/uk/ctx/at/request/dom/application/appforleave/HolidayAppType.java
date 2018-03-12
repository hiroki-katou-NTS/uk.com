package nts.uk.ctx.at.request.dom.application.appforleave;

/**
 * @author loivt
 * 休暇申請の種類
 */
public enum HolidayAppType {
	/**
	 * 年次有休
	 */
	ANNUAL_PAID_LEAVE(0),
	/**
	 * 代休
	 */
	SUBSTITUTE_HOLIDAY(1),
	/**
	 * 欠勤
	 */
	ABSENCE(2),
	/**
	 * 特別休暇
	 */
	SPECIAL_HOLIDAY(3),
	/**
	 * 積立年休
	 */
	YEARLY_RESERVE(4),
	/**
	 * 休日
	 */
	HOLIDAY(5),
	/**
	 * 時間消化
	 */
	DIGESTION_TIME(6),
	/**
	 * 振休
	 */
	REST_TIME(7);
	
	public final int value;
	
	private HolidayAppType(int value){
		this.value = value;
	}
}
