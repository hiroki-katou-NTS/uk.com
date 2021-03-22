package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtime;
/**
 * @author thanh_nx
 *
 */
// 勤怠種類
public enum AttendanceTypeShare {

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
	
	AttendanceTypeShare(int value) {
		this.value = value;
	}
}
