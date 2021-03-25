package nts.uk.cnv.infra.td.entity.snapshot.index;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;
import nts.uk.cnv.infra.td.entity.snapshot.NemTdSnapshotTablePk;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_SNAPSHOT_TABLE_INDEX")
public class NemTdSnapshotTableIndex extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final JpaEntityMapper<NemTdSnapshotTableIndex> MAPPER = new JpaEntityMapper<>(NemTdSnapshotTableIndex.class);

	@EmbeddedId
	public NemTdSnapshotTableIndexPk pk;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	public List<NemTdSnapshotTableIndexColumns> columns;

	@Override
	protected Object getKey() {
		return pk;
	}

	public static TableConstraints toDomain(List<NemTdSnapshotTableIndex> entities) {

		Optional<PrimaryKey> pk = entities.stream()
				.filter(e -> e.pk.isPK())
				.map(e -> new PrimaryKey(e.columnIds(), e.clustered))
				.findFirst();

		val uks = entities.stream()
			.filter(e -> e.pk.isUK())
			.map(e -> new UniqueConstraint(e.pk.suffix, e.columnIds(), e.clustered))
			.collect(Collectors.toList());

		val indexes = entities.stream()
			.filter(e -> e.pk.isIndex())
			.map(e -> new TableIndex(e.pk.suffix, e.columnIds(), e.clustered))
			.collect(Collectors.toList());

		return new TableConstraints((pk.isPresent() ? pk.get() : null ), uks, indexes);
	}

	private List<String> columnIds() {
		return columns.stream()
				.sorted(Comparator.comparing(e -> e.pk.columnOrder))
				.map(c -> c.getColumnId())
				.collect(toList());
	}
	public static List<NemTdSnapshotTableIndex> toEntities(NemTdSnapshotTablePk tablePK, TableConstraints contstraints) {
		List<NemTdSnapshotTableIndex> results = new ArrayList<>();
		results.add(pkToEntity(tablePK,  contstraints.getPrimaryKey()));
		results.addAll(ukToEntity(tablePK, contstraints.getUniqueConstraints()));
		results.addAll(indexToEntity(tablePK, contstraints.getIndexes()));
		return results;
	}
	
	private static NemTdSnapshotTableIndex pkToEntity(NemTdSnapshotTablePk tablePK, PrimaryKey primaryKey) {
		val pk = NemTdSnapshotTableIndexPk.asPK(tablePK);
		val columns = NemTdSnapshotTableIndexColumns.toEntities(tablePK.snapshotId, tablePK.toString(), primaryKey.getColumnIds(), pk);
		return new NemTdSnapshotTableIndex(pk,primaryKey.isClustered(), columns);
	}
	
	private static List<NemTdSnapshotTableIndex> ukToEntity(NemTdSnapshotTablePk tablePK, List<UniqueConstraint> uniqueKeys) {
		List<NemTdSnapshotTableIndex> results = new ArrayList<>();
		uniqueKeys.forEach(uniqueKey ->{
			val pk = NemTdSnapshotTableIndexPk.asUK(tablePK, uniqueKey.getSuffix());
			val indexColumns = NemTdSnapshotTableIndexColumns.toEntities(tablePK.snapshotId, 
																						  tablePK.snapshotId,
																						  uniqueKey.getColumnIds(),
																						  pk);
			results.add(new NemTdSnapshotTableIndex(pk, uniqueKey.isClustered(), indexColumns));
		});
		return results;
	}
	
	private static List<NemTdSnapshotTableIndex> indexToEntity(NemTdSnapshotTablePk tablePK, List<TableIndex> indexes) {
		List<NemTdSnapshotTableIndex> results = new ArrayList<>();
		indexes.forEach(index ->{
			val pk = NemTdSnapshotTableIndexPk.asUK(tablePK, index.getSuffix());
			val indexColumns = NemTdSnapshotTableIndexColumns.toEntities(tablePK.snapshotId, 
																						  tablePK.snapshotId,
																						  index.getColumnIds(),
																						  pk);
			results.add(new NemTdSnapshotTableIndex(pk, index.isClustered(), indexColumns));
		});
		return results;
	}
}
