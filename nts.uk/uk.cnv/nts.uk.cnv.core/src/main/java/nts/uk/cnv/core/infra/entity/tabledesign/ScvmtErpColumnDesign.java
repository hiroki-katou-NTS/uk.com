package nts.uk.cnv.core.infra.entity.tabledesign;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;
//import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
//import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
//import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.core.dom.tabledesign.ColumnDesign;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_ERP_COLUMN_DESIGN")
public class ScvmtErpColumnDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtErpColumnDesignPk scvmtErpColumnDesignPk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "NULLABLE")
	private int nullable;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "DISPORDER")
	private int dispOrder;

	@Column(name = "PK")
	private int pk;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_NAME", referencedColumnName = "NAME")
    })
	public ScvmtErpTableDesign tabledesign;

	@Override
	protected Object getKey() {
		return scvmtErpColumnDesignPk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				scvmtErpColumnDesignPk.id,
				name,
				type,
				(nullable == 1 ? true : false),
				defaultValue,
				comment,
				dispOrder,
				(pk == 1 ? true : false)
			);
	}

}
