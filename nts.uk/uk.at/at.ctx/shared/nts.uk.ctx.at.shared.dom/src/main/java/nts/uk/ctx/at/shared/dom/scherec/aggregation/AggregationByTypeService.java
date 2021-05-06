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
public class AggregationByTypeService {

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
			.map( m -> {
				return m.entrySet().stream()
					.filter( e -> targets.contains( e.getKey() ) )
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
			} ).filter( m -> !m.isEmpty() )
			.collect(Collectors.toList());
		val results = AggregationByTypeService.totalize(filterdValues);

		// 集計対象に対応する合計値を返す
		return AggregationByTypeService.mapping( targets, results );

	}


	/**
	 * カウントする
	 * @param attributes 属性リスト
	 * @return 属性ごとのカウント結果
	 */
	public static <T> Map<T, BigDecimal> count(List<T> attributes) {

		return attributes.stream()
				.collect(Collectors.groupingBy( e -> e, Collectors.counting() )).entrySet().stream()
				.collect(Collectors.toMap( Map.Entry::getKey, e -> BigDecimal.valueOf( e.getValue() ) ));

	}

	/**
	 * カウントする
	 * @param targets 集計対象リスト
	 * @param attributes 属性リスト
	 * @return 属性ごとのカウント結果
	 */
	public static <T> Map<T, BigDecimal> count(List<T> targets, List<T> attributes) {

		// カウント対象のみカウントする
		val filteredAttributes = attributes.stream()
				.filter( e -> targets.contains( e ) )
				.collect(Collectors.toList());
		val results = AggregationByTypeService.count(filteredAttributes);

		// 集計対象に対応するカウント結果を返す
		return AggregationByTypeService.mapping( targets, results );

	}


	/**
	 * 集計結果をマッピングする
	 * @param targets 集計対象リスト
	 * @param results 集計結果リスト
	 * @return マッピング済みの集計結果
	 */
	private static <T> Map<T, BigDecimal> mapping(List<T> targets, Map<T, BigDecimal> results) {

		// 集計対象に対応する集計結果を返す
		// ※結果が存在しない場合は「0」を返す
		return targets.stream()
				.collect(Collectors.toMap( e -> e , e -> results.getOrDefault( e , BigDecimal.ZERO ) ));

	}

}
