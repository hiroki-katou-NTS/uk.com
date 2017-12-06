package nts.uk.ctx.at.schedule.app.find.budget.schedulevertical.verticalsetting;

import lombok.AllArgsConstructor;

/**
 * TanLV
 *
 */
@AllArgsConstructor
public enum DailyAttendanceAtr {
	/** コード */
	CODE(0),
	/** マスタを参照する */
	REFER_TO_MASTER(1),
	/** 回数*/
	NUMBER_OF_TIME(2),
	/** 金額*/
	AMOUNT_OF_MONEY(3),
	/** 区分 */
	CLASSIFICATION(4),
	/** 時間 */
	TIME(5),
	/** 時刻*/
	TIME_OF_DAY(6),
	/** 文字 */
	CHARACTER(7);
	
	public final int value;
}
