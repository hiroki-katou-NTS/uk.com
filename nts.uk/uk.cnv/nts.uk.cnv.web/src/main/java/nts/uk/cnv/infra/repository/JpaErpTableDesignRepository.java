package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.ErpTableDesign;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.infra.entity.tabledesign.NemTdSnapshotColumn;
import nts.uk.cnv.infra.entity.tabledesign.NemTdSnapshotColumnPk;
import nts.uk.cnv.infra.entity.tabledesign.NemTdSnapshotTable;
import nts.uk.cnv.infra.entity.tabledesign.NemTdSnapshotTablePk;
import nts.uk.cnv.infra.impls.JpaSourceOrutaRepository;

@Stateless
public class JpaErpTableDesignRepository extends JpaSourceOrutaRepository implements ErpTableDesignRepository {

	@Override
	public void insert(ErpTableDesign tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(ErpTableDesign tableDesign) {
		this.commandProxy().update(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		val result = find(tableName);
		return result.isPresent();
	}

	private String getLatestSnapshotId() {
		String sql = "SELECT sss.SNAPSHOT_ID FROM NEM_TD_SNAPSHOT_SCHEMA sss"
				+ " ORDER BY sss.GENERATED_AT DESC";
		return this.jdbcProxy().query(sql).getList(rec -> rec.getString("SNAPSHOT_ID")).stream()
			.findFirst().orElse("");
	}

	private NemTdSnapshotTable toEntity(ErpTableDesign tableDesign) {
		NemTdSnapshotTablePk pk = new NemTdSnapshotTablePk(
				tableDesign.getTableId(),
				tableDesign.getSnapshotId());
		List<NemTdSnapshotColumn> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(pk, cd))
				.collect(Collectors.toList());

		return new NemTdSnapshotTable(
				pk,
				tableDesign.getName(),
				tableDesign.getJpName(),
				columns);
	}

	private NemTdSnapshotColumn toEntity(NemTdSnapshotTablePk pk, ColumnDesign columnDesign) {
		return new NemTdSnapshotColumn(
					new NemTdSnapshotColumnPk(
							pk.tableId,
							pk.snapshotId,
							columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getJpName(),
					columnDesign.getType(),
					columnDesign.getMaxLength(),
					columnDesign.getScale(),
					(columnDesign.isNullable() ? 1 : 0),
					columnDesign.getDefaultValue(),
					columnDesign.getComment(),
					columnDesign.getCheckConstraint(),
					columnDesign.getDispOrder(),
					null
				);
	}

	@Override
	public Optional<ErpTableDesign> find(String tablename) {
		String ssid = getLatestSnapshotId();

		String sql = "SELECT td FROM NemTdSnapshotTable td"
				+ " WHERE td.pk.snapshotId = :snapshotId"
				+ " AND td.name = :name";
		Optional<NemTdSnapshotTable> parent = this.queryProxy().query(sql, NemTdSnapshotTable.class)
				.setParameter("snapshotId", ssid)
				.setParameter("name", tablename)
				.getSingle();
		if(!parent.isPresent()) return Optional.empty();

		Optional<NemTdSnapshotTable> result = this.queryProxy().find(
				parent.get().getPk(), NemTdSnapshotTable.class);
		if(!result.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}

	@Override
	public List<String> getAllTableList() {
		String ssid = getLatestSnapshotId();

		String sql = "SELECT td"
				+ " FROM NemTdSnapshotTable td"
				+ " WHERE td.pk.snapshotId = :snapshotId"
				+ " ORDER BY td.name ASC";

		return this.queryProxy().query(sql, NemTdSnapshotTable.class)
			.setParameter("snapshotId", ssid)
			.getList(td -> td.getName());
	}

	@Override
	public List<String> getPkColumns(String tableName) {
		String ssid = getLatestSnapshotId();
		ErpTableDesign parent = this.find(tableName)
				.orElseThrow(() -> new RuntimeException("テーブルの定義情報がありません:" + tableName));
		List<String> pkColumnIds = getPkColumnIds(parent.getTableId(), ssid);

		String sql = "SELECT cd FROM NemTdSnapshotColumn cd"
				+ " WHERE cd.pk.snapshotId = :snapshotId"
				+ " AND cd.pk.tableId = :tableId";

		return this.queryProxy().query(sql, NemTdSnapshotColumn.class)
				.setParameter("snapshotId", ssid)
				.setParameter("tableId", parent.getTableId())
				.getList()
				.stream()
				.filter(col -> pkColumnIds.contains(col.pk.id))
				.map(col -> col.name)
				.collect(Collectors.toList());
	}

	private List<String> getPkColumnIds(String tableId, String ssid) {

		String sql = "SELECT idxcol.COLUMN_ID"
				+ " FROM NEM_TD_SNAPSHOT_TABLE_INDEX_COLUMNS idxcol"
				+ " WHERE idxcol.TABLE_ID = @tableId"
				+ " AND idxcol.SNAPSHOT_ID = @snapshotId"
				+ " AND idxcol.TYPE = 'PRIMARY KEY'";
		return this.jdbcProxy().query(sql)
			.paramString("tableId", tableId)
			.paramString("snapshotId", ssid)
			.getList(rec -> rec.getString("COLUMN_ID"));
	}
}
