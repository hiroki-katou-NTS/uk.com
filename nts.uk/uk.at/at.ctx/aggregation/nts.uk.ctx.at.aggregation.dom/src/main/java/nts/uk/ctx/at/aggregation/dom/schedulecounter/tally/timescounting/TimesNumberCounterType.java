package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timescounting;

import nts.arc.enums.EnumAdaptor;

/**
 * 回数集計種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.回数集計.回数集計種類
 * @author dan_pv
 *
 */
public enum TimesNumberCounterType {

	/** 職場計 */
	WORKPLACE(0),

	/** 個人計１ */
	PERSON_1(1),

	/** 個人計２ */
	PERSON_2(2),

	/** 個人計３ */
	PERSON_3(3);

	public int value;

	private TimesNumberCounterType(int value) {
		this.value = value;
	}

	public static TimesNumberCounterType of(int value) {
		return EnumAdaptor.valueOf(value, TimesNumberCounterType.class);
	}

}
