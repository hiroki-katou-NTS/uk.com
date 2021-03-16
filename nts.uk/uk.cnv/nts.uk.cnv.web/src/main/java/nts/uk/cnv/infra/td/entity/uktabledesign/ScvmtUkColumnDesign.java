package nts.uk.cnv.infra.td.entity.uktabledesign;

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
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SCVMT_UK_COLUMN_DESIGN")
public class ScvmtUkColumnDesign extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public ScvmtUkColumnDesignPk scvmtUkColumnDesignPk;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	@Column(name = "DATA_TYPE")
	private String dataType;

	@Column(name = "MAX_LENGTH")
	private int maxLength;

	@Column(name = "SCALE")
	private int scale;

	@Column(name = "NULLABLE")
	private int nullable;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "COMMENT")
	private String comment;

	@Column(name = "CHECK_CONSTRAINT")
	private String check;

	@Column(name = "DISPORDER")
	private int dispOrder;

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "TABLE_ID", referencedColumnName = "TABLE_ID"),
    	@PrimaryKeyJoinColumn(name = "SNAPSHOT_ID", referencedColumnName = "SNAPSHOT_ID")
    })
	public ScvmtUkTableDesign tabledesign;

	@Override
	protected Object getKey() {
		return scvmtUkColumnDesignPk;
	}

	public ColumnDesign toDomain() {
		return new ColumnDesign(
				scvmtUkColumnDesignPk.id,
				name,
				"",
				new DefineColumnType(
						DataType.valueOf(dataType),
						maxLength,
						scale,
						(nullable == 1),
						defaultValue,
						check),
				comment,
				dispOrder);
	}

}
