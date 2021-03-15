package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.td.schema.TableIdentity;
import nts.uk.cnv.dom.td.schema.snapshot.SchemaSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.SnapshotRepository;
import nts.uk.cnv.dom.td.schema.snapshot.TableListSnapshot;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.UkTableDesignRepository;

@Stateless
public class JpaSnapshotRepository extends JpaRepository implements SnapshotRepository {

	// 仮実装用
	@Inject
	UkTableDesignRepository tableRepo;
	
	@Override
	public Optional<SchemaSnapshot> getSchemaLatest() {
		
		String sql = "select * from NEM_TD_SNAPSHOT_SCHEMA"
				+ " order by GENERATED_AT desc";
		
		return this.jdbcProxy()
				.query(sql)
				.getList(rec -> new SchemaSnapshot(
						rec.getString("SNAPSHOT_ID"),
						rec.getGeneralDateTime("GENERATED_AT"),
						rec.getString("SOURCE_EVENT_ID")))
				.stream()
				.findFirst();
	}

	@Override
	public TableListSnapshot getTableList(String snapshotId) {

		String sql = "select * from SCVMT_UK_TABLE_DESIGN"
				+ " where SNAPSHOT_ID = @ssid";
		
		val tables = this.jdbcProxy()
				.query(sql)
				.paramString("ssid", snapshotId)
				.getList(rec -> new TableIdentity(
						rec.getString("TABLE_ID"),
						rec.getString("NAME")));
		
		return new TableListSnapshot(snapshotId, tables);
	}

	@Override
	public Optional<TableSnapshot> getTable(String snapshotId, String tableId) {
		
		return tableRepo.findByKey(tableId, snapshotId, "00000000")
				.map(td -> new TableSnapshot(snapshotId, td));
	}

}
