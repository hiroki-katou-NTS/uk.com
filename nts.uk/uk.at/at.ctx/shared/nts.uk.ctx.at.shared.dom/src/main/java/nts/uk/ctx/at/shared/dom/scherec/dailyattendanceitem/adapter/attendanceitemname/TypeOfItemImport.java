package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname;

import lombok.AllArgsConstructor;

@AllArgsConstructor
// 勤怠項目の種類
public enum TypeOfItemImport {
	
	/* スケジュール */
	Schedule(0),
	/* 日次 */
	Daily(1),
	/* 月次 */
	Monthly(2),
	/* 週次 */
	Weekly(3),
	/* 任意期間 */
	AnyPeriod(4);
	
	public final int value;
}