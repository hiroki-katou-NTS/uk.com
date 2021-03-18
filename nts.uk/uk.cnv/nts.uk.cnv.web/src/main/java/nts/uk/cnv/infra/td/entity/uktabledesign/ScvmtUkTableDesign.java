package nts.uk.cnv.infra.td.entity.uktabledesign;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_UK_TABLE_DESIGN")
public class ScvmtUkTableDesign extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkTableDesignPk pk;

	@Column(name = "NAME")
	private String name;

	@Column(name = "JPNAME")
	private String jpName;

	@OrderBy(value = "dispOrder asc")
	@OneToMany(targetEntity = ScvmtUkColumnDesign.class, mappedBy = "tabledesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_UK_COLUMN_DESIGN")
	private List<ScvmtUkColumnDesign> columns;

	@OneToMany(targetEntity = NemTdSnapshotTableIndex.class, mappedBy = "tabledesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_UK_INDEX_DESIGN")
	private List<NemTdSnapshotTableIndex> indexes;

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
}
