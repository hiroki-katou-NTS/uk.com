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
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_TYPE")
public class NemTdAltChangeColumnType extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

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

	@Column(name = "CHECK_CONSTRAINT")
	public String check;

	public static NemTdAltChangeColumnType toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (ChangeColumnType) ac;
		val type = domain.getAfterType();
		return new NemTdAltChangeColumnType(
				contentPk,
				domain.getColumnId(),
				type.getDataType().toString(),
				type.getLength(),
				type.getScale(),
				type.isNullable(),
				type.getDefaultValue(),
				type.getCheckConstaint()
			);
	}

	public ChangeColumnType toDomain() {
		return new ChangeColumnType(
				this.columnId,
				new DefineColumnType(
						DataType.valueOf(this.dataType),
						this.maxLength,
						this.scale,
						this.nullable,
						(this.defaultValue == null) ? "" : this.defaultValue,
						this.check
					)
				);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
