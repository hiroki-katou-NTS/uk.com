package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SCVMT_UK_INDEX_DESIGN")
public class ScvmtUkIndexDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkIndexDesignPk pk;

	@Column(name = "SUFFIX")
	private String suffix;

	@Column(name = "TYPE")
	public String type;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@OrderBy(value = "pk.id asc")
	@OneToMany(targetEntity = ScvmtUkIndexColumns.class, mappedBy = "indexdesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_UK_COLUMN_DESIGN")
	public List<ScvmtUkIndexColumns> columns;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID")
    })
	public ScvmtUkTableDesign tabledesign;

	@Override
	protected Object getKey() {
		return pk;
	}

	public static TableConstraints toDomain(List<ScvmtUkIndexDesign> entities) {
		PrimaryKey pk = entities.stream()
			.filter(entity -> entity.isPK())
			.map(entity -> new PrimaryKey(
					entity.getPk().getIndexId(),
					entity.getColumns().stream()
						.map(col -> col.getColumnId())
						.collect(Collectors.toList()),
					entity.isClustered()))
			.findFirst()
			.get();

		List<UniqueConstraint> uks = entities.stream()
			.filter(entity -> entity.isUK())
			.map(entity -> new UniqueConstraint(
					entity.getPk().getIndexId(),
					entity.getSuffix(),
					entity.getColumns().stream()
						.map(col -> col.getColumnId())
						.collect(Collectors.toList()),
					entity.isClustered()))
			.collect(Collectors.toList());

		List<TableIndex> indexes = entities.stream()
			.filter(entity -> entity.isIndex())
			.map(entity -> new TableIndex(
					entity.getPk().getIndexId(),
					entity.getSuffix(),
					entity.getColumns().stream()
						.map(col -> col.getColumnId())
						.collect(Collectors.toList()),
					entity.isClustered()))
			.collect(Collectors.toList());

		return new TableConstraints(pk, uks, indexes);
	}

	private boolean isPK() {
		return ("PRIMARY KEY".equals(this.type));
	}

	private boolean isUK() {
		return ("UNIQUE KEY".equals(this.type));
	}

	private boolean isIndex() {
		return ("INDEX".equals(this.type));
	}

	public static ScvmtUkIndexDesign toEntityFromPk(String tableId, String snapshotId, PrimaryKey domain) {
		return new ScvmtUkIndexDesign(
				new ScvmtUkIndexDesignPk(
					tableId,
					snapshotId,
					domain.getIndexId()
				),
				"_PK",
				"PRIMARY KEY",
				domain.isClustered(),
				domain.getColumnIds().stream()
					.map(columnId -> new ScvmtUkIndexColumns(
							new ScvmtUkIndexColumnsPk(
								tableId,
								snapshotId,
								domain.getIndexId(),
								0),
							columnId,
							null))
					.collect(Collectors.toList()),
				null);
	}

	public static List<ScvmtUkIndexDesign> toEntityFromUk(String tableId, String snapshotId, List<UniqueConstraint> uks) {
		List<ScvmtUkIndexDesign> result = new ArrayList<>();
		for(UniqueConstraint uk : uks) {
			ScvmtUkIndexDesign idx = new ScvmtUkIndexDesign(
					new ScvmtUkIndexDesignPk(
						tableId,
						snapshotId,
						uk.getIndexId()
					),
					uk.getSuffix(),
					"UNIQUE KEY",
					uk.isClustered(),
					uk.getColumnIds().stream()
						.map(columnId -> new ScvmtUkIndexColumns(
								new ScvmtUkIndexColumnsPk(
									tableId,
									snapshotId,
									uk.getIndexId(),
									result.size()),
								columnId,
								null))
						.collect(Collectors.toList()),
					null);
			result.add(idx);
		}

		return result;
	}

	public static List<ScvmtUkIndexDesign> toEntityFromIndex(String tableId, String snapshotId, List<TableIndex> indexes) {
		List<ScvmtUkIndexDesign> result = new ArrayList<>();
		for(TableIndex domain : indexes) {
			ScvmtUkIndexDesign idx = new ScvmtUkIndexDesign(
					new ScvmtUkIndexDesignPk(
						tableId,
						snapshotId,
						domain.getIndexId()
					),
					domain.getSuffix(),
					"INDEX",
					domain.isClustered(),
					domain.getColumnIds().stream()
						.map(columnId -> new ScvmtUkIndexColumns(
								new ScvmtUkIndexColumnsPk(
									tableId,
									snapshotId,
									domain.getIndexId(),
									result.size()),
								columnId,
								null))
						.collect(Collectors.toList()),
					null);
			result.add(idx);
		}

		return result;
	}
}
