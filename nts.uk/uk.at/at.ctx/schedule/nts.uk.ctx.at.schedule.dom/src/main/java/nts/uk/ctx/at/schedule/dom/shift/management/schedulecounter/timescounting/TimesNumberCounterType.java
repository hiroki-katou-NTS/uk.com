package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timescounting;

import nts.arc.enums.EnumAdaptor;

/**
 * 回数集計種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.回数集計.回数集計種類
 * @author dan_pv
 *
 */
public enum TimesNumberCounterType {

	WORKPLACE(0), // 職場計

	PERSON_1(1), // 個人計１

	PERSON_2(2), // 個人計２
	
	PERSON_3(3);; // 個人計３

	public int value;

	private TimesNumberCounterType(int value) {
	        this.value = value;
	    }

	public static TimesNumberCounterType of(int value) {
		return EnumAdaptor.valueOf(value, TimesNumberCounterType.class);
	}

}
