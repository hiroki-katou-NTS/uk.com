package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;
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
@Table(name = "NEM_TD_ALT_ADD_TABLE_INDEX")
public class NemTdAltAddTableIndex extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableIndexPk pk;

	@Column(name = "SUFFIX")
	public String suffix;

	@Column(name = "TYPE")
	public String type;

	@Column(name = "IS_CLUSTERED")
	public boolean clustered;

	@OrderBy(value = "pk.id asc")
	@OneToMany(targetEntity = NemTdAltAddTableIndexColumns.class, mappedBy = "addTableIndex", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_INDEX_COLUMN")
	public List<NemTdAltAddTableIndexColumns> columns;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID"),
    	@PrimaryKeyJoinColumn(name = "SEQ_NO", referencedColumnName = "SEQ_NO")
    })
	public NemTdAltAddTable addTable;

	@Override
	protected Object getKey() {
		return pk;
	}

	public static TableConstraints toDomain(List<NemTdAltAddTableIndex> entities) {
		PrimaryKey pk = entities.stream()
			.filter(entity -> entity.isPK())
			.map(entity -> new PrimaryKey(
					entity.getPk().getIndexId(),
					entity.getColumns().stream()
						.map(col -> col.getPk().getId())
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
						.map(col -> col.getPk().getId())
						.collect(Collectors.toList()),
					entity.isClustered()))
			.collect(Collectors.toList());

		List<TableIndex> indexes = entities.stream()
			.filter(entity -> entity.isIndex())
			.map(entity -> new TableIndex(
					entity.getPk().getIndexId(),
					entity.getSuffix(),
					entity.getColumns().stream()
						.map(col -> col.getPk().getId())
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

}
