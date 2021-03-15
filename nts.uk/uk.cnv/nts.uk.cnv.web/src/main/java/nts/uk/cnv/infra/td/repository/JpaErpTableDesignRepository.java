package nts.uk.cnv.infra.td.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.cnv.dom.td.schema.snapshot.TableSnapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.infra.td.entity.erptabledesign.ScvmtErpColumnDesign;
import nts.uk.cnv.infra.td.entity.erptabledesign.ScvmtErpColumnDesignPk;
import nts.uk.cnv.infra.td.entity.erptabledesign.ScvmtErpTableDesign;
import nts.uk.cnv.infra.td.entity.erptabledesign.ScvmtErpTableDesignPk;

public class JpaErpTableDesignRepository extends JpaRepository implements ErpTableDesignRepository {

	@Override
	public void insert(TableSnapshot ss) {
		this.commandProxy().insert(toEntity(ss));
	}

	@Override
	public void update(TableSnapshot ss) {
		this.commandProxy().update(toEntity(ss));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtErpTableDesign td WHERE td.name = :name";
		Optional<ScvmtErpTableDesign> result = this.queryProxy().query(sql, ScvmtErpTableDesign.class)
				.setParameter("name", tableName)
				.getSingle();
		return result.isPresent();
	}

	private ScvmtErpTableDesign toEntity(TableSnapshot ss) {
		List<ScvmtErpColumnDesign> columns = ss.getColumns().stream()
				.map(cd -> toEntity(ss.getId(), ss.getSnapshotId(), "", cd))
				.collect(Collectors.toList());

		return new ScvmtErpTableDesign(
				new ScvmtErpTableDesignPk(
						ss.getId(),
						ss.getSnapshotId(),
						""
					),
				ss.getName(),
				ss.getJpName(),
				columns);
	}

	private ScvmtErpColumnDesign toEntity(String tableId, String featureId, String eventId, ColumnDesign columnDesign) {
		return new ScvmtErpColumnDesign(
					new ScvmtErpColumnDesignPk(tableId, featureId, eventId, columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getJpName(),
					columnDesign.getType().getDataType().toString(),
					columnDesign.getType().getLength(),
					columnDesign.getType().getScale(),
					(columnDesign.getType().isNullable() ? 1 : 0),
					columnDesign.getType().getDefaultValue(),
					columnDesign.getComment(),
					columnDesign.getType().getCheckConstaint(),
					columnDesign.getDispOrder(),
					null
				);
	}

	@Override
	public Optional<TableDesign> find(String tablename) {
		String sql = "SELECT td FROM ScvmtErpTableDesign td WHERE td.name = :name";
		Optional<ScvmtErpTableDesign> parent = this.queryProxy().query(sql, ScvmtErpTableDesign.class)
				.setParameter("name", tablename)
				.getSingle();
		if(!parent.isPresent()) return Optional.empty();

		Optional<ScvmtErpTableDesign> result = this.queryProxy().find(parent.get().getPk(), ScvmtErpTableDesign.class);
		if(!parent.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}

	@Override
	public List<String> getAllTableList() {
		String sql = "SELECT td.NAME FROM SCVMT_ERP_TABLE_DESIGN td ORDER BY NAME ASC";
		List<String> tablelist = new ArrayList<>();
		try(PreparedStatement statement = this.connection().prepareStatement(sql)){
			tablelist = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return rec.getString("NAME");
			});
		}
		 catch (SQLException e) {
		}

		return tablelist;
	}
}
