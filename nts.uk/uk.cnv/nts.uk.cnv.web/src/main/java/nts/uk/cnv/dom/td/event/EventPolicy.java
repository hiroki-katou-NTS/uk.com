package nts.uk.cnv.dom.td.event;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

@Value
public class EventPolicy {
	
	public EventType type;
	
	
	/**
	 * イベントの制約を逸脱していないかチェック
	 * @param require
	 * @param alterations
	 * @return
	 */
	public List<AlterationSummary> checkError(Require require, List<String> alterations) {
		val targetAlters = require.getByAlter(alterations);

		// チェック対象のテーブルの一覧を取得
		List<String> checkTable = targetAlters.stream()
				.map(t -> t.getTableId())
				.distinct()
				.collect(Collectors.toList());
		
		// テーブルごとに必要なイベントが欠損しているorutaがないかチェック
		return checkTable.stream()
				.map(tableId -> getNecessaryAlters(require, targetAlters, tableId))
				.flatMap(list -> list.stream())
				.collect(Collectors.toList());
	}
	
	/**
	 * 必要なイベントが欠損しているorutaを取得する
	 * @param require
	 * @param alterations
	 * @param orderingAlters
	 * @param tableId
	 * @return
	 */
	private List<AlterationSummary> getNecessaryAlters(Require require,
			//List<String> alterations,
			List<AlterationSummary> targetAlters,
			String tableId) {

		// チェック対象のorutaを取得
		val targetAltersByTable = targetAlters.stream()
				.filter(a -> a.getTableId().equals(tableId))
				.collect(Collectors.toList());
		// チェック対象のうち、最新のものを特定
		val latest = targetAltersByTable.stream()
				.max(Comparator.comparing(a -> a.getTime())).get();


		// 対象のテーブルに対する未到達の既存orutaを取得
		val existingAltersByTable = require.getByTable(tableId, DevelopmentProgress.not(type.necessary()));

		return existingAltersByTable.stream()
				// チェック対象のorutaを取り除く
				.filter(a -> !targetAlters.contains(a))
				// チェック対象の最新orutaより古いorutaに絞り込む
				.filter(a -> a.getTime().before(latest.getTime()))
				.collect(Collectors.toList());
	}
	
	public interface Require {
		List<AlterationSummary> getByAlter(List<String> alterIds);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
	}

}
