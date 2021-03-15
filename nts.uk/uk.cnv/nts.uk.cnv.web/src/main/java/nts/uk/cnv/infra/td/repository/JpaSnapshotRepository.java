package nts.uk.cnv.infra.td.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
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
		
		val schema = new SchemaSnapshot(
				"B8167B60-BFF3-47CD-9013-2A11DD6A8A02",
				GeneralDateTime.ymdhms(2021, 1, 7, 0, 0, 0),
				"");
		
		return Optional.of(schema);
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
