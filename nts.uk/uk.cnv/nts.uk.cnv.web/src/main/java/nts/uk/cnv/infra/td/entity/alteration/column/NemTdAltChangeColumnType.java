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
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_TYPE")
public class NemTdAltChangeColumnType extends JpaEntity implements Serializable {

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

	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "ALTERATION_ID", referencedColumnName = "ALTERATION_ID")
    })
	public NemTdAlteration alteration;
	
	public static NemTdAltChangeColumnType toEntity(NemTdAltContentPk pk, ChangeColumnType d) {
		val e = new NemTdAltChangeColumnType();
		
		e.columnId = d.getColumnId();
		
		val t = d.getAfterType();
		e.dataType = t.getDataType().toString();
		e.maxLength = t.getLength();
		e.scale = t.getScale();
		e.nullable = t.isNullable();
		e.defaultValue = t.getDefaultValue();
		e.check = t.getCheckConstaint();
		
		return e;
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
