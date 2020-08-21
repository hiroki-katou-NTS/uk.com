package nts.uk.cnv.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.Indexes;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.TableDesignRepository;
import nts.uk.cnv.infra.entity.ScvmtColumnDesign;
import nts.uk.cnv.infra.entity.ScvmtColumnDesignPk;
import nts.uk.cnv.infra.entity.ScvmtIndexColumns;
import nts.uk.cnv.infra.entity.ScvmtIndexColumnsPk;
import nts.uk.cnv.infra.entity.ScvmtIndexDesign;
import nts.uk.cnv.infra.entity.ScvmtIndexDesignPk;
import nts.uk.cnv.infra.entity.ScvmtTableDesign;

public class JpaTableDesignRepository extends JpaRepository implements TableDesignRepository {

	@Override
	public void insert(TableDesign tableDesign) {
		this.commandProxy().insert(toEntity(tableDesign));
	}

	@Override
	public void update(TableDesign tableDesign) {
		this.commandProxy().update(toEntity(tableDesign));
	}

	@Override
	public void delete(TableDesign tableDesign) {
		this.commandProxy().remove(toEntity(tableDesign));
	}

	@Override
	public boolean exists(String tableName) {
		String sql = "SELECT td FROM ScvmtTableDesign td WHERE td.name = :name";
		Optional<ScvmtTableDesign> result = this.queryProxy().query(sql, ScvmtTableDesign.class)
				.setParameter("name", tableName)
				.getSingle();
		return result.isPresent();
	}

	@Override
	public void rename(String befor, String after) {
		// TODO 自動生成されたメソッド・スタブ
	}

	private ScvmtTableDesign toEntity(TableDesign tableDesign) {
		List<ScvmtColumnDesign> columns = tableDesign.getColumns().stream()
				.map(cd -> toEntity(tableDesign.getId(), cd))
				.collect(Collectors.toList());
		
		List<ScvmtIndexDesign> indexes = new ArrayList<>();
		for (Indexes idx: tableDesign.getIndexes()) {
			List<ScvmtIndexColumns> indexcolumns = idx.getColmns().stream()
				.map(col -> new ScvmtIndexColumns(
						new ScvmtIndexColumnsPk(tableDesign.getId(), idx.getName(), idx.getColmns().indexOf(col), col),
						null)
					)
				.collect(Collectors.toList());

			indexes.add(new ScvmtIndexDesign(
					new ScvmtIndexDesignPk(tableDesign.getId(), idx.getName()),
					idx.getConstraintType(),
					String.join(",", idx.getParams()),
					indexcolumns,
					null
			));
		}
		
		return new ScvmtTableDesign(
				tableDesign.getId(),
				tableDesign.getName(),
				tableDesign.getCreateDate(),
				tableDesign.getUpdateDate(),
				columns,
				indexes);
	}
	
	private ScvmtColumnDesign toEntity(String tableId, ColumnDesign columnDesign) {
		return new ScvmtColumnDesign(
					new ScvmtColumnDesignPk(tableId, columnDesign.getId()),
					columnDesign.getName(),
					columnDesign.getType().toString(),
					columnDesign.getMaxLength(),
					columnDesign.getScale(),
					(columnDesign.isNullable() ? 1 : 0),
					(columnDesign.isPrimaryKey() ? 1 : 0),
					columnDesign.getPrimaryKeySeq(),
					(columnDesign.isUniqueKey() ? 1 : 0),
					columnDesign.getUniqueKeySeq(),
					columnDesign.getDefaultValue(), null
				);
	}

	@Override
	public Optional<TableDesign> find(String tablename) {
		String sql = "SELECT td FROM ScvmtTableDesign td WHERE td.name = :name";
		Optional<ScvmtTableDesign> parent = this.queryProxy().query(sql, ScvmtTableDesign.class)
				.setParameter("name", tablename)
				.getSingle();
		if(!parent.isPresent()) return Optional.empty();
		
		Optional<ScvmtTableDesign> result = this.queryProxy().find(parent.get().getTableId(), ScvmtTableDesign.class);
		if(!parent.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}
}
