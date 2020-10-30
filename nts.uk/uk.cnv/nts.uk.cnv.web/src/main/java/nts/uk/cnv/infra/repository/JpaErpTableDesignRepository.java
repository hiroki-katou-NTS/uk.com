package nts.uk.cnv.infra.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.ErpTableDesignRepository;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.infra.entity.erptabledesign.ScvmtErpColumnDesign;
import nts.uk.cnv.infra.entity.erptabledesign.ScvmtErpColumnDesignPk;
import nts.uk.cnv.infra.entity.erptabledesign.ScvmtErpTableDesign;

public class JpaErpTableDesignRepository extends JpaRepository implements ErpTableDesignRepository {

	@Override
	public void insert(TableDesign tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(TableDesign tableDesign) {
		this.commandProxy().update(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtErpTableDesign td WHERE td.name = :name";
		Optional<ScvmtErpTableDesign> result = this.queryProxy().query(sql, ScvmtErpTableDesign.class)
				.setParameter("name", tableName)
				.getSingle();
		return result.isPresent();
	}

	private ScvmtErpTableDesign toEntity(TableDesign tableDesign) {
		List<ScvmtErpColumnDesign> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(tableDesign.getId(), cd))
				.collect(Collectors.toList());

		return new ScvmtErpTableDesign(
				tableDesign.getId(),
				tableDesign.getName(),
				tableDesign.getComment(),
				tableDesign.getCreateDate(),
				tableDesign.getUpdateDate(),
				columns);
	}

	private ScvmtErpColumnDesign toEntity(String tableId, ColumnDesign columnDesign) {
		return new ScvmtErpColumnDesign(
					new ScvmtErpColumnDesignPk(tableId, columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getType().toString(),
					columnDesign.getMaxLength(),
					columnDesign.getScale(),
					(columnDesign.isNullable() ? 1 : 0),
					(columnDesign.isPrimaryKey() ? 1 : 0),
					columnDesign.getPrimaryKeySeq(),
					(columnDesign.isUniqueKey() ? 1 : 0),
					columnDesign.getUniqueKeySeq(),
					columnDesign.getDefaultValue(),
					columnDesign.getComment(),
					columnDesign.getCheck(),
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

		Optional<ScvmtErpTableDesign> result = this.queryProxy().find(parent.get().getTableId(), ScvmtErpTableDesign.class);
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
