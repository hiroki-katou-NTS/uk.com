package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_ADD_TABLE_INDEX")
public class NemTdAltAddTableIndex extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableIndexPk pk;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	public PrimaryKey toPrimaryKey(List<NemTdAltAddTableIndexColumns> columns) {
		return new PrimaryKey(columnIds(columns), this.clustered);
	}

	public UniqueConstraint toUniqueConstraint(List<NemTdAltAddTableIndexColumns> columns) {
		return new UniqueConstraint(this.pk.suffix, columnIds(columns), this.clustered);
	}

	public TableIndex toTableIndex(List<NemTdAltAddTableIndexColumns> columns) {
		return new TableIndex(this.pk.suffix, columnIds(columns), this.clustered);
	}

	public static TableConstraints toDomain(List<NemTdAltAddTableIndex> entities, Map<String, List<String>> columns) {

		val pk = entities.stream()
			.filter(e -> e.pk.isPK())
			.map(e -> new PrimaryKey(columns.get(e.pk.suffix), e.clustered))
			.findFirst()
			.get();

		val uks = entities.stream()
			.filter(e -> e.pk.isUK())
			.map(e -> new UniqueConstraint(e.pk.suffix, columns.get(e.pk.suffix), e.clustered))
			.collect(Collectors.toList());

		val indexes = entities.stream()
			.filter(e -> e.pk.isIndex())
			.map(e -> new TableIndex(e.pk.suffix, columns.get(e.pk.suffix), e.clustered))
			.collect(Collectors.toList());

		return new TableConstraints(pk, uks, indexes);
	}

	public static List<JpaEntity> toEntity(NemTdAltContentPk pk, AddTable domain) {

		List<JpaEntity> entities = new ArrayList<>();

		entities.addAll(toEntityPk(pk, domain.getTableDesign().getConstraints().getPrimaryKey()));
		entities.addAll(toEntityUk(pk, domain.getTableDesign().getConstraints().getUniqueConstraints()));
		entities.addAll(toEntityIndex(pk, domain.getTableDesign().getConstraints().getIndexes()));

		return entities;
	}

	private static List<JpaEntity> toEntityPk(NemTdAltContentPk pk, PrimaryKey domain) {
		List<JpaEntity> result = new ArrayList<>();
		val parentPk = NemTdAltAddTableIndexPk.asPK(pk);
		val parent = new NemTdAltAddTableIndex(
				parentPk,
				domain.isClustered()
			);
		result.add(parent);
		result.addAll(domain.getColumnIds().stream()
			.map(columnId -> NemTdAltAddTableIndexColumns.create(
					parent,
					domain.getColumnIds().indexOf(columnId),
					columnId
				))
			.collect(Collectors.toList())
		);

		return result;
	}

	private static List<JpaEntity> toEntityUk(NemTdAltContentPk pk, List<UniqueConstraint> domains) {
		List<JpaEntity> result = new ArrayList<>();
		domains.forEach(domain -> {
			val parentPk = NemTdAltAddTableIndexPk.asUK(pk, domain.getSuffix());
			val parent = new NemTdAltAddTableIndex(
				parentPk,
				domain.isClustered()
			);
			result.add(parent);
			result.addAll(domain.getColumnIds().stream()
				.map(columnId -> NemTdAltAddTableIndexColumns.create(
						parent,
						domain.getColumnIds().indexOf(columnId),
						columnId
					))
				.collect(Collectors.toList())
			);
		});
		return result;
	}

	private static List<JpaEntity> toEntityIndex(NemTdAltContentPk pk, List<TableIndex> domains) {
		List<JpaEntity> result = new ArrayList<>();
		domains.forEach(domain -> {
			val parentPk = NemTdAltAddTableIndexPk.asIndex(pk, domain.getSuffix());
			val parent = new NemTdAltAddTableIndex(
				parentPk,
				domain.isClustered()
			);
			result.add(parent);
			result.addAll(domain.getColumnIds().stream()
				.map(columnId -> NemTdAltAddTableIndexColumns.create(
						parent,
						domain.getColumnIds().indexOf(columnId),
						columnId
					))
				.collect(Collectors.toList())
			);
		});
		return result;
	}

	@Override
	protected Object getKey() {
		return pk;
	}

	private static List<String> columnIds(List<NemTdAltAddTableIndexColumns> columns) {
		return columns.stream()
				.sorted(Comparator.comparing(e -> e.pk.getColumnOrder()))
				.map(c -> c.getColumnId())
				.collect(Collectors.toList());
	}
}
