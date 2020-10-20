/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums;

import lombok.AllArgsConstructor;

/**
 * The Enum FrameCategory.
 */
@AllArgsConstructor
// 枠カテゴリ
public enum FrameCategory {

	/** The Over time. */
	// 残業
	OverTime(0),

	/** The Over time tranfer. */
	// 残業振替
	OverTimeTranfer(1),

	/** The Rest. */
	// 休出
	Rest(2),

	/** The Rest tranfer. */
	// 休出振替
	RestTranfer(3),

	/** The Extra item. */
	// 割増項目
	ExtraItem(4),

	/** The Addtion time item. */
	// 加給時間項目
	AddtionTimeItem(5),

	/** The Specific addtion time item. */
	// 特定加給時間項目
	SpecificAddtionTimeItem(6),

	/** The Divergence time item. */
	// 乖離時間項目
	DivergenceTimeItem(7),

	/** The Any item. */
	// 任意項目
	AnyItem(8),

	/** The Go out. */
	// 外出
	GoOut(9),

	/** The Specific date. */
	// 特定日
	SpecificDate(10),

	/** The Excess time. */
	// 超過時間
	ExcessTime(11),

	/** The Absence. */
	// 欠勤
	Absence(12),

	/** The Special holiday frame. */
	// 特別休暇枠
	SpecialHolidayFrame(13),

	/** The Total count. */
	// 回数集計
	TotalCount(14),

	/** The Special holiday. */
	// 特別休暇
	SpecialHoliday(15),

	/** 分類 */
	Classification(16),

	/** 職場 */
	Workplace(17),

	/** 雇用 */
	Employment(18),

	/** 職位 */
	JobTittle(19),

	/** 応援・作業 */
	SupportWork(20),

	/** 会社 */
	Company(21),

	/** 予約 */
	Reservation(22),

	/** 週次超過時間 */
	Week_ExcessTime(23);

	/** The value. */
	public final int value;

}
