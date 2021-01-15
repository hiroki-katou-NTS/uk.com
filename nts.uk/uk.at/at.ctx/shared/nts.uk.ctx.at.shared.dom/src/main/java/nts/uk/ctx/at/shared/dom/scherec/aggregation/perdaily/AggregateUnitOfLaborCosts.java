package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

/**
 * 人件費・時間の集計単位
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.人件費・時間の集計単位
 * @author dan_pv
 *
 */
@RequiredArgsConstructor
public enum AggregateUnitOfLaborCosts {

	/** 合計 **/
	TOTAL( 0 ),

	/** 就業時間 **/
	WORKING_HOURS( 1 ),

	/** 時間外時間 **/
	OVERTIME( 2 ),
	;


	public final int value;


	public static AggregateUnitOfLaborCosts of(int value) {
		return EnumAdaptor.valueOf(value, AggregateUnitOfLaborCosts.class);
	}

}
