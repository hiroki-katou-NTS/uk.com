package nts.uk.ctx.exio.infra.entity.input.transfer.source;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "SCVMT_ERP_TABLE_DESIGN")
public class ScvmtErpTableDesign extends JpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "NAME")
	private String name;

	@OneToMany(targetEntity = ScvmtErpColumnDesign.class, mappedBy = "tabledesign", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("dispOrder ASC")
    @JoinTable(name = "SCVMT_ERP_COLUMN_DESIGN")
	private List<ScvmtErpColumnDesign> columns;

	@Override
	protected Object getKey() {
		return name;
	}

	public ErpTableDesign toDomain() {
		List<ColumnDesign> cols = columns.stream()
				.map(col -> col.toDomain())
				.collect(Collectors.toList());

		return new ErpTableDesign(name, cols);
	}
}
