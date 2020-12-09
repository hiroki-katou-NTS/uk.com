package nts.uk.cnv.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
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
import nts.uk.cnv.infra.entity.uktabledesign.ScvmtUkTableDesignPk;

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
				.map(cd -> toEntity(tableDesign, cd))
				.collect(Collectors.toList());

		List<ScvmtUkIndexDesign> indexes = new ArrayList<>();
		for (Indexes idx: tableDesign.getIndexes()) {
			List<ScvmtUkIndexColumns> indexcolumns = idx.getColmns().stream()
				.map(col -> new ScvmtUkIndexColumns(
						new ScvmtUkIndexColumnsPk(
								tableDesign.getId(),
								tableDesign.getVer().get().getBranch(),
								tableDesign.getVer().get().getDate(),
								idx.getName(),
								idx.getColmns().indexOf(col),
								col),
						null)
					)
				.collect(Collectors.toList());

			indexes.add(new ScvmtUkIndexDesign(
					new ScvmtUkIndexDesignPk(
							tableDesign.getId(), tableDesign.getVer().get().getBranch(), tableDesign.getVer().get().getDate(), idx.getName()),
					idx.getConstraintType(),
					String.join(",", idx.getParams()),
					idx.getClustered(),
					indexcolumns,
					null
			));
		}

		return new ScvmtUkTableDesign(
				new ScvmtUkTableDesignPk(
					tableDesign.getId(),
					tableDesign.getVer().get().getBranch(),
					tableDesign.getVer().get().getDate()),
				tableDesign.getName(),
				tableDesign.getComment(),
				tableDesign.getCreateDate(),
				tableDesign.getUpdateDate(),
				columns,
				indexes);
	}

	private ScvmtUkColumnDesign toEntity(TableDesign tableDesign, ColumnDesign columnDesign) {
		return new ScvmtUkColumnDesign(
					new ScvmtUkColumnDesignPk(
							tableDesign.getId(),
							tableDesign.getVer().get().getBranch(),
							tableDesign.getVer().get().getDate(),
							columnDesign.getId()),
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

		Optional<ScvmtUkTableDesign> result = this.queryProxy().find(parent.get().getPk(), ScvmtUkTableDesign.class);
		if(!result.isPresent()) return Optional.empty();

		return Optional.of(result.get().toDomain());
	}

	@Override
	public List<GetUkTablesDto> getAllTableList() {
		return getAll().stream()
			.map(td -> new GetUkTablesDto(td.getId(), td.getName()))
			.collect(Collectors.toList());
	}

	@Override
	public List<TableDesign> getAll() {
		String sql = "SELECT td FROM ScvmtUkTableDesign td";
		List<TableDesign> result = this.queryProxy().query(sql, ScvmtUkTableDesign.class)
				.getList(rec -> rec.toDomain());

		return result;
	}
}
