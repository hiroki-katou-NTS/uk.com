package nts.uk.cnv.dom.td.alteration.summary;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;

@Value
public class AlterationStatusPolicy {
	
	// 進行先の開発状況
	public DevelopmentStatus destinationStatus;
	
	/**
	 * orutaの進捗進行における制約を逸脱していないかチェックする
	 * @param require
	 * @param targetAlters
	 * @return
	 */
	public List<AlterationSummary> checkError(Require require, List<AlterationSummary> targetAlters) {

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

		final AlterationSummary latest = getLatestAlterByTable(targetAlters, tableId);


		// 対象のテーブルに対する未到達の既存orutaを取得
		val existingAltersByTable = require.getByTable(tableId, DevelopmentProgress.not(destinationStatus.necessary()));

		return existingAltersByTable.stream()
				// チェック対象のorutaを取り除く
				.filter(a -> !targetAlters.contains(a))
				// チェック対象の最新orutaより古いorutaに絞り込む
				.filter(a -> a.getTime().before(latest.getTime()))
				.collect(Collectors.toList());
	}

	private AlterationSummary getLatestAlterByTable(List<AlterationSummary> targetAlters, String tableId) {
		// チェック対象のorutaを取得
		val targetAltersByTable = targetAlters.stream()
				.filter(a -> a.getTableId().equals(tableId))
				.collect(Collectors.toList());
		// チェック対象のうち、最新のものを特定
		val latest = targetAltersByTable.stream()
				.max(Comparator.comparing(a -> a.getTime())).get();
		return latest;
	}
	
	public interface Require {
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
	}

}
