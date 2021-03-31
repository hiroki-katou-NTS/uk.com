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
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnName;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_NAME")
public class NemTdAltChangeColumnName extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@Column(name = "NAME")
	public String name;

	public static NemTdAltChangeColumnName toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (ChangeColumnName)ac;
		return new NemTdAltChangeColumnName(
				contentPk,
				domain.getColumnId(),
				domain.getAfterName());
	}

	public ChangeColumnName toDomain() {
		return new ChangeColumnName(this.columnId, this.name);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
