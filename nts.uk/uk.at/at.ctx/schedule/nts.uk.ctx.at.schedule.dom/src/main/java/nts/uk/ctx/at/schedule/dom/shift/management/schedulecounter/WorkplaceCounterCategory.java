package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import nts.arc.enums.EnumAdaptor;

public enum WorkplaceCounterCategory {
	
    LABOR_COSTS_AND_TIME(0), // 人件費・時間

    EXTERNAL_BUDGET(1), // 外部予算実績

    TIMES_COUNTING(2), // 回数集計

    WORKTIME_PEOPLE(3), // 就業時間帯別の利用人数

    TIMEZONE_PEOPLE(4), // 時間帯人数

    EMPLOYMENT_PEOPLE(5), // 雇用人数
 
    CLASSIFICATION_PEOPLE(6), // 分類人数

    POSITION_PEOPLE(7); // 職位人数
	
	public int value;
	
	private WorkplaceCounterCategory(int value) {
		this.value = value;
	}
	
	public static WorkplaceCounterCategory of(int value) {
		return EnumAdaptor.valueOf(value, WorkplaceCounterCategory.class);
	}

}
