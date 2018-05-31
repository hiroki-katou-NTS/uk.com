package nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
// 勤怠項目の種類
public enum TypeOfItem {
	
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