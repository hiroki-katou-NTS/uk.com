package nts.uk.cnv.dom.td.schema.snapshot;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public class CreateTableSnapshot {
	
	public  static List<TableDesign> create(Require require, List<Alteration> alterations){
		
		Map<String, List<Alteration>>  alterPerTable = alterations.stream().collect(Collectors.groupingBy(Alteration::getTableId));
		List<TableSnapshot> latestTableSnapshot =  require.getTablesLatest();
		
		List<TableDesign> alteredTable = applyAlter(alterPerTable, latestTableSnapshot);
		List<TableDesign> notChangeTables = getNotChangeTables(alterPerTable, latestTableSnapshot);
		
		List<TableDesign> results = new ArrayList<>();
		results.addAll(alteredTable);
		results.addAll(notChangeTables);
		return results;
	}

	private static List<TableDesign> applyAlter(
			Map<String, List<Alteration>> altersPerTable,
			List<TableSnapshot> latestTableSnapshot) {
		
		List<TableDesign> results = new ArrayList<>();
		
		for (val es : altersPerTable.entrySet()) {
			String tableId = es.getKey();
			val alters = es.getValue().stream()
					.sorted(Comparator.comparing(a -> a.getCreatedAt()))  // 日時順に適用するためソート
					.collect(toList());
			
			val targetSnapshot = latestTableSnapshot.stream()
					.filter(t -> t.getId().equals(tableId))
					.findFirst()
					.orElseGet(() -> TableSnapshot.empty()); // 新規テーブルの場合はこれ
			
			targetSnapshot.apply(alters)
				.ifPresent(t -> results.add(t)); // テーブルが削除された場合はemptyになる
		}
		
		return results;
	}
	
	private static List<TableDesign> getNotChangeTables(Map<String, List<Alteration>> altersPerTable,
			List<TableSnapshot> latestTableSnapshot){
		return latestTableSnapshot.stream()
		.filter(snapShot -> !altersPerTable.containsKey(snapShot.getId()))
		.collect(Collectors.toList());
	}
	
	public static interface Require{
		List<TableSnapshot> getTablesLatest();
	}
}
