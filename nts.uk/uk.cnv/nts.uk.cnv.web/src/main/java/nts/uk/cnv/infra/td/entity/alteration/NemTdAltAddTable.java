package nts.uk.cnv.infra.td.entity.alteration;

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
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.Indexes;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_TABLE")
public class NemTdAltAddTable extends JpaEntity implements Serializable {

	@EmbeddedId
	private NemTdAltContentPk pk;

	@Column(name = "NAME")
	private String name;

	@Column(name = "JPNAME")
	private String jpName;

	@OrderBy(value = "dispOrder asc")
	@OneToMany(targetEntity = NemTdAltAddTableColumn.class, mappedBy = "addTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_COLUMN")
	private List<NemTdAltAddTableColumn> columns;

	@OrderBy(value = "pk.name asc")
	@OneToMany(targetEntity = NemTdAltAddTableIndex.class, mappedBy = "addTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_INDEX")
	private List<NemTdAltAddTableIndex> indexes;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;

	public AddTable toDomain(String tableId) {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		List<Indexes> idxs = new ArrayList<>();
		for (NemTdAltAddTableIndex index :indexes) {
			 List<String> colmns = index.columns.stream()
				.map(col -> col.getColumnName())
				.collect(Collectors.toList());
			 idxs.add(new Indexes(
					 index.pk.getName(),
					 index.type,
					 index.clustered,
					 colmns
			));
		}

		return new AddTable(new TableDesign(tableId, name, jpName, cols, idxs));
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
