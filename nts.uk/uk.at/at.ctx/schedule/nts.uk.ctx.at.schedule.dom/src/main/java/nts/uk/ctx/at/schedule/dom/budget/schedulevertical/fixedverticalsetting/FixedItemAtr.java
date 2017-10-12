package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
/**
 * 固定縦計設定
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum FixedItemAtr {
	/** 0- シフト **/
	SHIFT(0),
	/** 1- ランク **/
	RANK(1),
	/** 2- 作業1 **/
	TASK1(2),
	/** 3- 作業2 **/
	TASK2(3),
	/** 4- 作業3 **/
	TASK3(4),
	/** 5- 作業4 **/
	TASK4(5),
	/** 6- 作業5 **/
	TASK5(6),
	/** 7- 分類 **/
	CLASSIFICATION(7),
	/** 8- 回数集計 **/
	TOTAL_COUNT(8),
	/** 9- 役割 **/
	ROLE(9),
	/** 10- 時間帯 **/
	TIME_ZONE(10),
	/** 11- 職位 **/
	POSITION(11),
	/** 12- 雇用 **/
	EMPLOYMENT(12),;

	public final int value;
}
