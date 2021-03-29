package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public class CreateShapshot {
	
	public static AtomTask create(Require require,String eventId,List<String> alterationIds) {
		String snapShotId = IdentifierUtil.randomUniqueId();

		List<Alteration> alters= require.getAlterationBy(eventId, alterationIds);
		
		//テーブルスナップショット
		List<TableDesign> alteredTableSnapshot = CreateTableSnapshot.create(require, alters);
		//スキーマスナップショット
		val alterdSchemaSnapShot = new SchemaSnapshot(snapShotId,GeneralDateTime.now(),eventId);
		
		return AtomTask.of(() ->{
			require.registSchemaSnapShot(alterdSchemaSnapShot);
			require.registTableSnapShot(snapShotId, alteredTableSnapshot);
		}) ;
	}
	
	
	public static interface Require extends CreateTableSnapshot.Require{
		List<Alteration> getAlterationBy(String eventId, List<String> alterIds);
		void registSchemaSnapShot(SchemaSnapshot schema);
		void registTableSnapShot(String snapshotId, List<TableDesign> tablesSnapshot);
	}
}
