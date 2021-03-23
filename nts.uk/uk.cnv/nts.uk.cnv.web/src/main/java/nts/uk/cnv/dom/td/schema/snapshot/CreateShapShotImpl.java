package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Stateless
public class CreateShapShotImpl implements CreateShapShot{
	
	public AtomTask create(Require require,String acceptedEventId,List<Alteration> alterations) {
		String snapShotId = IdentifierUtil.randomUniqueId();

		//テーブルスナップショット
		//適用するalterationたち
		//<tableId, List<Alteration>が作りたい
		Map<String, List<Alteration>>  altermap = alterations.stream().collect(Collectors.groupingBy(Alteration::getTableId));
		//適用のベース
		List<TableSnapshot> tableSnapshot =  require.getTablesLatest();
		
		//ベースへ適用
		List<TableProspect> alterdTableProspect = tableSnapshot.stream()
				.map(snapShot -> snapShot.apply(altermap.get(snapShot.getId())))
				.filter(prospect ->prospect.isPresent())
				.map(prospect -> prospect.get())
				.collect(Collectors.toList());
		List<TableDesign> alterdTableSnapShot = alterdTableProspect.stream().map(prospect ->new TableDesign(prospect)).collect(Collectors.toList());
		
		//スキーマスナップショット
		val alterdSchemaSnapShot = new SchemaSnapshot(snapShotId,GeneralDateTime.now(),acceptedEventId);
		
		return AtomTask.of(() ->{
			require.registSchemaSnapShot(alterdSchemaSnapShot);
			require.registTableSnapShot(snapShotId, alterdTableSnapShot);
		}) ;
	}
	
	
	public static interface Require{
		void registSchemaSnapShot(SchemaSnapshot schema);
		void registTableSnapShot(String snapshotId, List<TableDesign> tablesSnapshot);
		List<TableSnapshot> getTablesLatest();
	}
}
