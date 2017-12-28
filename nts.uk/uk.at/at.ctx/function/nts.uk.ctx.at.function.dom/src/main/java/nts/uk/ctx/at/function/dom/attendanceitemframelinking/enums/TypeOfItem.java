package nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeOfItem {
	
	/* スケジュール */
	Schedule(0),
	/* 日次 */
	Monthly(1),
	/* 月次 */
	Weekly(2),
	/* 週次 */
	Daily(3),
	/* 任意期間 */
	AnyPeriod(4);
	
	public final int value;
}