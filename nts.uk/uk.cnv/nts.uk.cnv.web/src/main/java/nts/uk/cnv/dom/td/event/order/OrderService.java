package nts.uk.cnv.dom.td.event.order;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.event.EventIdProvider;

/**
 * 発注する
 * @author ai_muto
 *
 */
public class OrderService {
	public static OrderedResult order(Require require, String featureId, String eventName, String userName, List<String> alterations) {
		
		List<AlterationSummary> errorList = checkError(require, alterations);
		
		// 発注できない
		if(errorList.size() > 0) {
			return new OrderedResult(errorList, Optional.empty());
		}

		// 発注できる
		return new OrderedResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(OrderEvent.create(require, eventName, userName, alterations));
				}
			)));
	}

	/**
	 * orutaの発注制約を逸脱していないかチェック
	 * @param require
	 * @param alterations
	 * @return
	 */
	private static List<AlterationSummary> checkError(Require require, List<String> alterations) {
		val orderingAlters = require.getByAlter(alterations);

		// チェック対象のテーブルの一覧を取得
		List<String> checkTable = orderingAlters.stream()
				.map(t -> t.getTableId())
				.distinct()
				.collect(Collectors.toList());

		List<AlterationSummary> errorList = new ArrayList<>();
		checkTable.forEach(tableId -> {	
			
			// すでに発注されている必要があるorutaを取得
			val necessaris = getNecessaryAlters(require, alterations, orderingAlters, tableId);
			// それらをエラー対象とする
			errorList.addAll(necessaris);
		});
		return errorList;
	}
	
	/**
	 * 発注されている必要があるorutaを取得する
	 * @param require
	 * @param alterations
	 * @param orderingAlters
	 * @param tableId
	 * @return
	 */
	private static List<AlterationSummary> getNecessaryAlters(Require require,
			List<String> alterations,
			List<AlterationSummary> orderingAlters, 
			String tableId) {
		// 対象のテーブルに対する未発注の既存orutaを取得
		val existingAltersByTable = require.getByTable(tableId, DevelopmentProgress.notOrdered());
		
		// 発注対象のorutaを取得
		val orderingAltersByTable = orderingAlters.stream()
				.filter(a -> a.getTableId().equals(tableId))
				.collect(Collectors.toList());
		// 発注対象のうち、最新のものを特定
		val latest = orderingAltersByTable.stream().max(Comparator.comparing(a -> a.getTime())).get();
		
		
		return existingAltersByTable.stream()
				// 発注対象のorutaを取り除く
				.filter(a -> !alterations.contains(a.getAlterId()))
				// 発注対象の最新orutaより古いorutaに絞り込む
				.filter(a -> a.getTime().before(latest.getTime()))
				.collect(Collectors.toList());
	}

	public interface Require extends EventIdProvider.ProvideOrderIdRequire{
		List<AlterationSummary> getByAlter(List<String> alterIds);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		List<AlterationSummary> getByFeature(String featureId, DevelopmentProgress devProgress);
		List<AlterationSummary> getOlder(AlterationSummary alter, DevelopmentProgress devProgress);
		void regist(OrderEvent create);
	}
}
