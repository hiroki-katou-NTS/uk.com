package nts.uk.ctx.at.function.dom.attendancetype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AttendanceItemType {
	/**スケジュール	 */
	SCHEDULE(0),
	/**日次	 */
	DAILY(1),
	/**月次	 */
	MONTHLY(2),
	/**週次	 */
	WEEKLY(3),
	/**任意期間	 */
	ANYPERIOD(4);
	
	public final int value;
}
