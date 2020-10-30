package nts.uk.cnv.infra.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.cnv.app.dto.GetUkTablesDto;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.Indexes;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.UkTableDesignRepository;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkColumnDesign;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkColumnDesignPk;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkIndexColumns;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkIndexColumnsPk;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkIndexDesign;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkIndexDesignPk;
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkTableDesign;

public class JpaUkTableDesignRepository extends JpaRepository implements UkTableDesignRepository {

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
		String sql = "SELECT td FROM ScvmtUkTableDesign td WHERE td.name = :name";
		Optional<ScvmtUkTableDesign> result = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
				.setParameter("name", tableName)
				.getSingle();
		return result.isPresent();
	}

	private ScvmtUkTableDesign toEntity(TableDesign tableDesign) {
		List<ScvmtUkColumnDesign> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(tableDesign.getId(), cd))
				.collect(Collectors.toList());

		List<ScvmtUkIndexDesign> indexes = new ArrayList<>();
		for (Indexes idx: tableDesign.getIndexes()) {
			List<ScvmtUkIndexColumns> indexcolumns = idx.getColmns().stream()
				.map(col -> new ScvmtUkIndexColumns(
						new ScvmtUkIndexColumnsPk(tableDesign.getId(), idx.getName(), idx.getColmns().indexOf(col), col),
						null)
					)
				.collect(Collectors.toList());

			indexes.add(new ScvmtUkIndexDesign(
					new ScvmtUkIndexDesignPk(tableDesign.getId(), idx.getName()),
					idx.getConstraintType(),
					String.join(",", idx.getParams()),
					idx.getClustered(),
					indexcolumns,
					null
			));
		}

		return new ScvmtUkTableDesign(
				tableDesign.getId(),
				tableDesign.getName(),
				tableDesign.getComment(),
				tableDesign.getCreateDate(),
				tableDesign.getUpdateDate(),
				columns,
				indexes);
	}

	private ScvmtUkColumnDesign toEntity(String tableId, ColumnDesign columnDesign) {
		return new ScvmtUkColumnDesign(
					new ScvmtUkColumnDesignPk(tableId, columnDesign.getId()),
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
	@SneakyThrows
	public Optional<TableDesign> find(String tablename) {
		String sql = "SELECT td FROM ScvmtUkTableDesign td WHERE td.name = :name";
		Optional<ScvmtUkTableDesign> parent = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
				.setParameter("name", tablename)
				.getSingle();
		if(!parent.isPresent()) return Optional.empty();

		Optional<ScvmtUkTableDesign> result = this.queryProxy().find(parent.get().getTableId(), ScvmtUkTableDesign.class);
		if(!result.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}

	@Override
	public List<GetUkTablesDto> getAllTableList() {
		String sql = "SELECT td.TABLE_ID, td.NAME FROM SCVMT_UK_TABLE_DESIGN td ORDER BY NAME ASC";
		List<GetUkTablesDto> tablelist = new ArrayList<>();
		try(PreparedStatement statement = this.connection().prepareStatement(sql)){
			tablelist = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return new GetUkTablesDto(
						rec.getString("TABLE_ID"),
						rec.getString("NAME")
					);
			});
		}
		 catch (SQLException e) {
		}

		return tablelist;
	}
}
