package nts.uk.cnv.infra.td.entity.alteration.column;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.column.AddColumn;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_ADD_COLUMN")
public class NemTdAltAddColumn extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@Column(name = "NAME")
	public String name;

	@Column(name = "JPNAME")
	public String jpName;

	@Column(name = "DATA_TYPE")
	public String dataType;

	@Column(name = "MAX_LENGTH")
	public int maxLength;

	@Column(name = "SCALE")
	public int scale;

	@Column(name = "NULLABLE")
	public boolean nullable;

	@Column(name = "DEFAULT_VALUE")
	public String defaultValue;

	@Column(name = "COMMENT")
	public String comment;

	@Column(name = "CHECK_CONSTRAINT")
	public String check;

	@Column(name = "DISPORDER")
	public int dispOrder;

	public static NemTdAltAddColumn toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (AddColumn) ac;
		val type = domain.getColumn().getType();
		return new NemTdAltAddColumn(
			contentPk,
			domain.getColumn().getId(),
			domain.getColumn().getName(),
			domain.getColumn().getJpName(),
			type.getDataType().toString(),
			type.getLength(),
			type.getScale(),
			type.isNullable(),
			type.getDefaultValue(),
			domain.getColumn().getComment(),
			type.getCheckConstraint(),
			domain.getColumn().getDispOrder());
	}

	public AddColumn toDomain() {
		return new AddColumn(
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
