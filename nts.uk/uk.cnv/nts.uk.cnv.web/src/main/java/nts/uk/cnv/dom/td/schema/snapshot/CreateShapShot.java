package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public class CreateShapShot {
	
	public static AtomTask create(Require require,String acceptedEventId,List<Alteration> alterations) {
		String snapShotId = IdentifierUtil.randomUniqueId();

		//テーブルスナップショット
		List<TableDesign> alteredTableSnapshot = createTableSnapshot(require, alterations);
		//スキーマスナップショット
		val alterdSchemaSnapShot = new SchemaSnapshot(snapShotId,GeneralDateTime.now(),acceptedEventId);
		
		return AtomTask.of(() ->{
			require.registSchemaSnapShot(alterdSchemaSnapShot);
			require.registTableSnapShot(snapShotId, alteredTableSnapshot);
		}) ;
	}
	
	private static List<TableDesign> createTableSnapshot(Require require, List<Alteration> alterations){
		Map<String, List<Alteration>>  alters = alterations.stream().collect(Collectors.groupingBy(Alteration::getTableId));
		List<TableSnapshot> tableSnapshot =  require.getTablesLatest();
		//適用
		List<TableDesign> alterdTableDesign = tableSnapshot.stream()
				//applyするためにここでalterがないテーブル除外
				.filter(snapShot -> alters.containsKey(snapShot.getId()))
				.map(snapShot -> snapShot.apply(alters.get(snapShot.getId())))
				.filter(prospect ->prospect.isPresent())
				.map(prospect -> new TableDesign(prospect.get()))
				.collect(Collectors.toList());
		List<TableDesign> notTargetTables = tableSnapshot.stream()
				.filter(snapShot -> !alters.containsKey(snapShot.getId()))
				.collect(Collectors.toList());
		
		List<TableDesign> results = new ArrayList<>();
		results.addAll(alterdTableDesign);
		results.addAll(notTargetTables);
		return results;
	}
	
	public static interface Require{
		void registSchemaSnapShot(SchemaSnapshot schema);
		void registTableSnapShot(String snapshotId, List<TableDesign> tablesSnapshot);
		List<TableSnapshot> getTablesLatest();
	}
}
