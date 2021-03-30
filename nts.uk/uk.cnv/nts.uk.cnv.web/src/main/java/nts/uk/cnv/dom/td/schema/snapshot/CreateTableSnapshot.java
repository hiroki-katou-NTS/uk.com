package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.uk.cnv.dom.td.alteration.Alteration;
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

	private static List<TableDesign> applyAlter(Map<String, List<Alteration>> altersPerTable,
			List<TableSnapshot> latestTableSnapshot) {
		return  latestTableSnapshot.stream()
				//applyするためにここでalterがないテーブル除外
				.filter(snapShot -> altersPerTable.containsKey(snapShot.getId()))
				.map(snapShot -> snapShot.apply(altersPerTable.get(snapShot.getId())))
				.filter(prospect ->prospect.isPresent())
				.map(prospect -> new TableDesign(prospect.get()))
				.collect(Collectors.toList());
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
