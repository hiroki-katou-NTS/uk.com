package nts.uk.cnv.infra.td.entity.alteration.index;

import static java.util.stream.Collectors.*;

import java.io.Serializable;
import java.util.ArrayList;
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
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTable;

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

	@OrderBy(value = "pk.suffix asc")
	@OneToMany(targetEntity = NemTdAltAddTableIndexColumns.class, mappedBy = "addTableIndex", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_INDEX_COLUMN")
	public List<NemTdAltAddTableIndexColumns> columns;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID"),
    	@PrimaryKeyJoinColumn(name = "SEQ_NO", referencedColumnName = "SEQ_NO")
    })
	public NemTdAltAddTable addTable;

	public static TableConstraints toDomain(List<NemTdAltAddTableIndex> entities) {
		
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

	public static List<NemTdAltAddTableIndex> toEntity(NemTdAltAddTable parent, TableConstraints d) {
		
		List<NemTdAltAddTableIndex> entities = new ArrayList<>();
		
		entities.add(toEntity(parent, d.getPrimaryKey()));
		
		// UKとINDEXは未実装
		
		return entities;
	}
	
	private static NemTdAltAddTableIndex toEntity(NemTdAltAddTable parent, PrimaryKey d) {

		val e = new NemTdAltAddTableIndex();
		e.pk = NemTdAltAddTableIndexPk.asPK(parent.pk);
		e.clustered = d.isClustered();
		
		e.columns = new ArrayList<>();
		for (int i = 0; i < d.getColumnIds().size(); i++) {
			String colId = d.getColumnIds().get(i);
			e.columns.add(NemTdAltAddTableIndexColumns.create(e, i, colId));
		}
		
		return e;
	}
	
	@Override
	protected Object getKey() {
		return pk;
	}

	private List<String> columnIds() {
		return columns.stream()
				.sorted(Comparator.comparing(e -> e.pk.getColumnOrder()))
				.map(c -> c.getColumnId())
				.collect(toList());
	}
}
