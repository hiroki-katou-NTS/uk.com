package nts.uk.ctx.at.request.dom.application.overtime;

/**
 * @author loivt
 * 勤怠項目ID
 */
public enum AttendanceID {
	
	/**
	 * 休出時間
	 */
	RESTTIME(0),
	
	/**
	 * 残業時間
	 */
	NORMALOVERTIME(1),
	
	/**
	 * 休憩時間
	 */
	BREAKTIME(2),
	
	/**
	 * 加給時間
	 */
	BONUSPAYTIME(3),
	
	/**
	 *  特定日加給時間
	 */
	BONUSSPECIALDAYTIME(4);
	
	public final int value;
	
	AttendanceID(int value){
		this.value = value;
	}
}
