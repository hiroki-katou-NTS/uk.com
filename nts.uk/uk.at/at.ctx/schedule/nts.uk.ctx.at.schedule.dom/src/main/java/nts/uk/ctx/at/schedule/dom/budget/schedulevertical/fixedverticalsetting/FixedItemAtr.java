package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.fixedverticalsetting;

import lombok.AllArgsConstructor;
/**
 * 固定縦計設定
 * @author phongtq
 *
 */
@AllArgsConstructor
public enum FixedItemAtr {
	/** 0- 時間帯 **/
	TIME_ZONE(0, "KML002_85", ""),
	
	/** 1- 回数集計 **/
	TOTAL_COUNT(1, "KML002_86", ""),

	
	/** 2- 雇用 **/
	EMPLOYMENT(2, "KML002_87", "Com_Employment"),
	
	/** 3- 分類 **/
	CLASSIFICATION(3, "KML002_87", "Com_Class"),
	
	/** 4- 職位 **/
	POSITION(4, "KML002_87", "Com_Jobtitle"),
	
	/** 5- シフト **/
	SHIFT(5, "KML002_88", ""),
	
	/** 6- 役割 **/
	ROLE(6, "KML002_89", ""),
	
	/** 7- 作業1 *KML002_101*/
	TASK1(7, "KML002_101", ""),
	
	/** 8- 作業2 **/
	TASK2(8, "KML002_102", ""),
	
	/** 9- 作業3 **/
	TASK3(9, "KML002_103", ""),
	
	/** 10- 作業4 **/
	TASK4(10, "KML002_104", ""),
	
	/** 11- 作業5 **/
	TASK5(11, "KML002_105", ""),
	
	/** 12- ランク **/
	RANK(12, "KML002_148", ""),;

	public final int value;
	public final String nameId;
	public final String paramNameId;
}
