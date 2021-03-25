package nts.uk.cnv.dom.td.event.delivery;

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
 * 納品する
 * @author ai_muto
 *
 */
@Stateless
public class DeliveryService {
	public DeliveredResult delivery(Require require, String featureId, String eventName, String userName, List<String> alterations) {

		val deliveringAlters = require.getByAlter(alterations);

		// orutaの納品制約を逸脱していないかチェック
		Set<String> checkTable = new HashSet<>();
		deliveringAlters.forEach(a -> {
			checkTable.add(a.getTableId());
		});

		List<AlterationSummary> errorList = new ArrayList<>();
		checkTable.forEach(tableId -> {	
			
			// すでに納品されている必要があるorutaを取得
			val necessaris = getNecessaryAlters(require, alterations, deliveringAlters, tableId);
			// それらをエラー対象とする
			errorList.addAll(necessaris);
		});
		
		// 納品できない
		if(errorList.size() > 0) {
			return new DeliveredResult(errorList, Optional.empty());
		}

		// 納品できる
		return new DeliveredResult(errorList,
			Optional.of(
				AtomTask.of(() -> {
					require.regist(DeliveryEvent.create(require, eventName, userName, alterations));
				}
			)));
	}
	
	/**
	 * 納品されている必要があるorutaを取得する
	 * @param require
	 * @param alterations
	 * @param orderingAlters
	 * @param tableId
	 * @return
	 */
	private static List<AlterationSummary> getNecessaryAlters(Require require,
			List<String> alterations,
			List<AlterationSummary> deliveringAlters, 
			String tableId) {
		// 対象のテーブルに対する未納品の既存orutaを取得
		val existingAltersByTable = require.getByTable(tableId, DevelopmentProgress.notDeliveled());
		
		// 納品対象のorutaを取得
		val deliveringAltersByTable = deliveringAlters.stream()
				.filter(a -> a.getTableId().equals(tableId))
				.collect(Collectors.toList());
		// 納品対象のうち、最新のものを特定
		val latest = deliveringAltersByTable.stream().max(Comparator.comparing(a -> a.getTime())).get();
		
		
		return existingAltersByTable.stream()
				// 納品対象のorutaを取り除く
				.filter(a -> !alterations.contains(a.getAlterId()))
				// 納品対象の最新orutaより古いorutaに絞り込む
				.filter(a -> a.getTime().before(latest.getTime()))
				.collect(Collectors.toList());
	}


	public interface Require extends EventIdProvider.ProvideDeliveryIdRequire{
		List<AlterationSummary> getByAlter(List<String> alterIds);
		List<AlterationSummary> getByFeature(String feature, DevelopmentProgress devProgress);
		List<AlterationSummary> getByTable(String tableId, DevelopmentProgress devProgress);
		void regist(DeliveryEvent create);
	}
}
