package nts.uk.cnv.infra.td.entity.alteration.column;

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
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_COLUMN")
public class NemTdAltAddColumn extends JpaEntity implements Serializable {

	@EmbeddedId
	private NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	private String columnId;

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
	private boolean nullable;

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
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;

	public AddColumn toDomain() {
		return new AddColumn(
				this.columnId,
				new ColumnDesign(
						this.columnId,
						name,
						jpName,
						new DefineColumnType(
								DataType.valueOf(this.dataType),
								this.maxLength,
								this.scale,
								this.nullable,
								(this.defaultValue == null) ? "" : this.defaultValue,
								this.check
							),
						(this.comment == null) ? "" : this.comment,
						this.dispOrder)
				);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
