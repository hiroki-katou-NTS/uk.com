package nts.uk.cnv.dom.td.alteration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.dom.td.devstatus.DevelopmentStatus;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

/**
 * orutaを登録する
 */
public class SaveAlteration {

	public static AtomTask save(
			Require require,
			String featureId,
			String tableId,
			AlterationMetaData meta,
			Optional<TableDesign> newDesign) {

		val snapshot = require.getSchemaSnapsohtLatest()
				.map(schema -> require.getTableSnapshot(schema.getSnapshotId(), tableId))
				.orElseGet(() -> TableSnapshot.empty());
		
		val alters = require.getAlterationsOfTable(tableId, DevelopmentStatus.notAccepted());

		val prospect = snapshot.apply(alters);

		val alter = Alteration.create(featureId, tableId, meta, prospect, newDesign)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("変更が無いよ")));

		return AtomTask.of(() ->{
			require.save(alter);
		});
	}

	public interface Require {
		
		Optional<SchemaSnapshot> getSchemaSnapsohtLatest();
		
		TableSnapshot getTableSnapshot(String snapshotId, String tableId);

		List<Alteration> getAlterationsOfTable(String tableId, Set<DevelopmentStatus> status);
		
		void save(Alteration alter);
	}
}
