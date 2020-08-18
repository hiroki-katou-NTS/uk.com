package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.tabledesign.TableDesign;
import nts.uk.cnv.dom.tabledesign.TableDesignRepository;
import nts.uk.cnv.infra.entity.ScvmtColumnDesign;
import nts.uk.cnv.infra.entity.ScvmtColumnDesignPk;
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
		String sql = "SELECT td FROM ScvmtTableDesign td WHERE td.NAME = :name";
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
		
		List<ScvmtIndexDesign> indexes = tableDesign.getIndexes().stream()
			.flatMap(idx -> idx.getColmns().stream().map(col -> new ScvmtIndexDesign(
					new ScvmtIndexDesignPk(tableDesign.getId(), idx.getName(), col), null)))
			.collect(Collectors.toList());
		
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
}
