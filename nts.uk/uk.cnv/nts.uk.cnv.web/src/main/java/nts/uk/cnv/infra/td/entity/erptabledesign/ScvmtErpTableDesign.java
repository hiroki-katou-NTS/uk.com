package nts.uk.cnv.infra.td.entity.erptabledesign;

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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.schema.snapshot.Snapshot;
import nts.uk.cnv.dom.td.schema.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_ERP_TABLE_DESIGN")
public class ScvmtErpTableDesign extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtErpTableDesignPk pk;

	@Column(name = "NAME")
	private String name;

	@Column(name = "JPNAME")
	private String jpName;

	@OrderBy(value = "dispOrder asc")
	@OneToMany(targetEntity = ScvmtErpColumnDesign.class, mappedBy = "tabledesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "SCVMT_ERP_COLUMN_DESIGN")
	private List<ScvmtErpColumnDesign> columns;

	@Override
	protected Object getKey() {
		return pk;
	}

	public Snapshot toDomain() {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		return new Snapshot(
					pk.getSnapshotId(),
					pk.getEventId(),
					new TableDesign(pk.getTableId(), name, jpName, cols, new ArrayList<>())
				);
	}
}
