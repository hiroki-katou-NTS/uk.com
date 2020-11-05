package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter;

import nts.arc.enums.EnumAdaptor;

/**
 * 職場計カテゴリ
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.職場計カテゴリ
 * @author dan_pv
 *
 */
public enum WorkplaceCounterCategory {
	
    /** 人件費・時間 */
    LABOR_COSTS_AND_TIME(0),

    /** 外部予算実績 */
    EXTERNAL_BUDGET(1),

    /** 回数集計 */
    TIMES_COUNTING(2),

    /** 就業時間帯別の利用人数 */
    WORKTIME_PEOPLE(3),

    /** 時間帯人数 */
    TIMEZONE_PEOPLE(4),

    /** 雇用人数 */
    EMPLOYMENT_PEOPLE(5),
 
    /** 分類人数 */
    CLASSIFICATION_PEOPLE(6),

    /** 職位人数 */
    POSITION_PEOPLE(7);
	
	public int value;
	
	private WorkplaceCounterCategory(int value) {
		this.value = value;
	}
	
	public static WorkplaceCounterCategory of(int value) {
		return EnumAdaptor.valueOf(value, WorkplaceCounterCategory.class);
	}

}
