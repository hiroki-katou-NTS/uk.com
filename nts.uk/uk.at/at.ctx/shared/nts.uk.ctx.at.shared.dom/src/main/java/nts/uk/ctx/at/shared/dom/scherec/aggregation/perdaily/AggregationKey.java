package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import lombok.Value;

/**
 * 集計用キー
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.日単位集計.集計用キー<T>
 * @author kumiko_otake
 *
 * @param <T> 集計単位
 */
@Value
public class AggregationKey<T> {

	/** 値 **/
	private final T value;

}
