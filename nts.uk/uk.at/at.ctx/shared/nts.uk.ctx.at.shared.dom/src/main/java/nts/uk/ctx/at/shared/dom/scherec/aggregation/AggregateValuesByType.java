package nts.uk.ctx.at.shared.dom.scherec.aggregation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;

/**
 * 種類ごとに値を集計する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).集計処理.種類ごとに値を集計する
 * @author kumiko_otake
 */
@Stateless
public class AggregateValuesByType {

	/**
	 * 合計する
	 * @param values 値リスト
	 * @return 種類ごとの合計結果
	 */
	public static <T> Map<T, BigDecimal> totalize(List<Map<T, BigDecimal>> values) {

		return values.stream()
				.flatMap( e -> e.entrySet().stream() )
				.collect(Collectors.toMap(
								Map.Entry::getKey
							,	Map.Entry::getValue
							,	( v1, v2 ) -> v1.add( v2 )
						));

	}

	/**
	 * 合計する
	 * @param targets 集計対象リスト
	 * @param values 値リスト
	 * @return 種類ごとの合計結果(集計対象のみ)
	 */
	public static <T> Map<T, BigDecimal> totalize(List<T> targets, List<Map<T, BigDecimal>> values) {

		// 集計対象のみ合計する
		val filterdValues = values.stream()
				.filter( e -> targets.contains( e ) )
				.collect(Collectors.toList());
		val results = AggregateValuesByType.totalize(filterdValues);

		// 集計対象に対する合計値を返す
		return targets.stream()
				.collect(Collectors.toMap( e -> e , e -> results.getOrDefault( e , BigDecimal.ZERO ) ));

	}


	/**
	 * カウントする
	 * @param values 値リスト
	 * @return 種類ごとのカウント結果
	 */
	public static <T> Map<T, BigDecimal> count(List<T> values) {

		return values.stream()
					.collect(Collectors.groupingBy( e -> e, Collectors.counting() )).entrySet().stream()
					.collect(Collectors.toMap( Map.Entry::getKey, e -> BigDecimal.valueOf( e.getValue() ) ));

	}
}
