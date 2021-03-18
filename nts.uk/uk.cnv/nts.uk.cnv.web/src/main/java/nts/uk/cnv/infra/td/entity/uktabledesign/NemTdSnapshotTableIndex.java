package nts.uk.cnv.infra.td.entity.uktabledesign;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.Comparator;
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
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_SNAPSHOT_TABLE_INDEX")
public class NemTdSnapshotTableIndex extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdSnapshotTableIndexPk pk;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@OrderBy(value = "pk.id asc")
	@OneToMany(targetEntity = NemTdSnapshotTableIndexColumns.class, mappedBy = "indexdesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_UK_COLUMN_DESIGN")
	public List<NemTdSnapshotTableIndexColumns> columns;

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

	public static TableConstraints toDomain(List<NemTdSnapshotTableIndex> entities) {
		
		val pk = entities.stream()
				.filter(e -> e.pk.isPK())
				.map(e -> new PrimaryKey(e.columnIds(), e.clustered))
				.findFirst()
				.get();

		val uks = entities.stream()
			.filter(e -> e.pk.isUK())
			.map(e -> new UniqueConstraint(e.pk.suffix, e.columnIds(), e.clustered))
			.collect(Collectors.toList());

		val indexes = entities.stream()
			.filter(e -> e.pk.isIndex())
			.map(e -> new TableIndex(e.pk.suffix, e.columnIds(), e.clustered))
			.collect(Collectors.toList());

		return new TableConstraints(pk, uks, indexes);
	}
	
	private List<String> columnIds() {
		return columns.stream()
				.sorted(Comparator.comparing(e -> e.getColumnOrder()))
				.map(c -> c.getColumnId())
				.collect(toList());
	}
}
