package nts.uk.cnv.infra.td.entity.alteration.table;

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
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.AddTable;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltAddTableColumn;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltAddTableIndex;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_TABLE")
public class NemTdAltAddTable extends JpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	@OrderBy(value = "dispOrder asc")
	@OneToMany(targetEntity = NemTdAltAddTableColumn.class, mappedBy = "addTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_COLUMN")
	public List<NemTdAltAddTableColumn> columns;

	@OneToMany(targetEntity = NemTdAltAddTableIndex.class, mappedBy = "addTable", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "NEM_TD_ALT_ADD_TABLE_INDEX")
	public List<NemTdAltAddTableIndex> indexes;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;

	public AddTable toDomain(String tableId) {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		TableConstraints tableConstraints = NemTdAltAddTableIndex.toDomain(indexes);

		return new AddTable(new TableDesign(tableId, new TableName(name), jpName, cols, tableConstraints));
	}
	
	public static NemTdAltAddTable toEntity(NemTdAltContentPk pk, AddTable d) {
		
		val e = new NemTdAltAddTable();
		
		e.pk = pk;
		e.name = d.getTableDesign().getName().v();
		e.jpName = d.getTableDesign().getJpName();
		e.columns = NemTdAltAddTableColumn.toEntity(e, d.getTableDesign().getColumns());
		e.indexes = NemTdAltAddTableIndex.toEntity(e, d.getTableDesign().getConstraints());
		
		return e;
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
