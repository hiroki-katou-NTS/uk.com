package nts.uk.ctx.at.request.dom.application.overtime;
/**
 * Refactor5
 * @author hoangnd
 *
 */
// 勤怠種類
public enum AttendanceType_Update {

	/**
	 * 残業時間
	 */
	NORMALOVERTIME(0),
	
	/**
	 * 休出時間
	 */
	BREAKTIME(1),
	
	/**
	 * 加給時間
	 */
	BONUSPAYTIME(2),
	
	/**
	 *  特定日加給時間
	 */
	BONUSSPECIALDAYTIME(3);
	
	public final int value;
	
	AttendanceType_Update(int value) {
		this.value = value;
	}
}
