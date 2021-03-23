package nts.uk.cnv.infra.td.entity.snapshot;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.infra.td.entity.snapshot.column.NemTdSnapshotColumn;
import nts.uk.cnv.infra.td.entity.snapshot.index.NemTdSnapshotTableIndex;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_SNAPSHOT_TABLE")
public class NemTdSnapshotTable extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final JpaEntityMapper<NemTdSnapshotTable> MAPPER = new JpaEntityMapper<>(NemTdSnapshotTable.class);

	@EmbeddedId
	public NemTdSnapshotTablePk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	public List<NemTdSnapshotColumn> columns;

	public List<NemTdSnapshotTableIndex> indexes;

	@Override
	protected Object getKey() {
		return pk;
	}

	public TableDesign toDomain() {
		TableConstraints tableConstraints = NemTdSnapshotTableIndex.toDomain(indexes);
		List<ColumnDesign> cd = columns.stream()
			.map(col -> col.toDomain())
			.collect(Collectors.toList());

		return new TableDesign(pk.tableId, new TableName(name), jpName, cd, tableConstraints);
	}
	
	public static List<NemTdSnapshotTable> toEntities(String snapshotId, List<TableDesign> tables){
		return tables.stream().map(table -> toEntity(snapshotId, table)).collect(Collectors.toList());
	}
	
	public static NemTdSnapshotTable toEntity(String snapshotId, TableDesign table) {
		val pk = new NemTdSnapshotTablePk(snapshotId,table.getId());
		val columns = table.getColumns().stream()
				.map(column -> NemTdSnapshotColumn.toEntity(snapshotId,table.getId(), column))
				.collect(Collectors.toList());
		val indexes = NemTdSnapshotTableIndex.toEntities(pk, table.getConstraints());
		return new NemTdSnapshotTable(pk,table.getName().toString(), table.getJpName(),columns,indexes);
	}
}
