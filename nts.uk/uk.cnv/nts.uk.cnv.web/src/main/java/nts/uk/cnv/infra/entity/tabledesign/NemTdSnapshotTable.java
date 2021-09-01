package nts.uk.cnv.infra.entity.tabledesign;

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
import nts.uk.cnv.core.dom.tabledesign.ColumnDesign;
import nts.uk.cnv.core.dom.tabledesign.ErpTableDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_SNAPSHOT_TABLE")
public class NemTdSnapshotTable extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NemTdSnapshotTablePk pk;

	@Column(name = "NAME")
	private String name;

	@Column(name = "JPNAME")
	private String jpName;

	@OneToMany(targetEntity = NemTdSnapshotColumn.class, mappedBy = "tabledesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("dispOrder ASC")
    @JoinTable(name = "NEM_TD_SNAPSHOT_COLUMN")
	private List<NemTdSnapshotColumn> columns;

	@Override
	protected Object getKey() {
		return name;
	}

	public ErpTableDesign toDomain() {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		return new ErpTableDesign(pk.tableId, pk.snapshotId, name, jpName, cols);
	}
}
