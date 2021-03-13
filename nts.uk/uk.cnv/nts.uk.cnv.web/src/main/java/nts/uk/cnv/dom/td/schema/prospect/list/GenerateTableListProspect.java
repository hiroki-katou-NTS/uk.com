package nts.uk.cnv.dom.td.schema.prospect.list;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.val;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlteration;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.schema.prospect.TableListProspect;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;

/**
 * テーブル一覧のプロスペクトを生成する
 */
public class GenerateTableListProspect {

	public static TableListProspect generate(Require require) {
		
		val snapshot = require.getSchemaSnapshotLatest()
				.map(schema -> require.getTableListSnapshot(schema.getSnapshotId()))
				.orElseGet(() -> TableListSnapshot.empty());
		
		val alters = require.getSchemaAlteration(DevelopmentStatus.notAccepted());
		
		return snapshot.apply(alters);
	}
	
	public static interface Require {
		
		Optional<SchemaSnapshot> getSchemaSnapshotLatest();
		
		TableListSnapshot getTableListSnapshot(String snapsohtId);
		
		List<SchemaAlteration> getSchemaAlteration(Set<DevelopmentStatus> status);
	}
}
