package nts.uk.cnv.dom.td.schema.prospect.definition;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;

/**
 * テーブル定義のプロスペクトを生成する
 */
public class GenerateTableProspect {
	
	public static Optional<TableProspect> generate(Require require, String tableId) {
		
		val snapshot = require.getSchemaSnapshotLatest()
				.flatMap(schema -> require.getTableSnapshot(schema.getSnapshotId(), tableId))
				.orElseGet(() -> TableSnapshot.empty());
		
		val alters = require.getAlterations(tableId, DevelopmentProgress.notAccepted());
		
		return snapshot.apply(alters);
	}

	
	public static interface Require {

		Optional<SchemaSnapshot> getSchemaSnapshotLatest();
		
		Optional<TableSnapshot> getTableSnapshot(String snapshotId, String tableId);
		
		List<Alteration> getAlterations(String tableId, DevelopmentProgress progress);
	}
}
