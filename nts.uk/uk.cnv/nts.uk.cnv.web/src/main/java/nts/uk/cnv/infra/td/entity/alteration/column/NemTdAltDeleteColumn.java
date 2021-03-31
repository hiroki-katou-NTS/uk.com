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
import nts.uk.cnv.dom.td.alteration.content.column.RemoveColumn;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_DELETE_COLUMN")
public class NemTdAltDeleteColumn extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	public static NemTdAltDeleteColumn toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (RemoveColumn) ac;
		return new NemTdAltDeleteColumn(
				contentPk,
				domain.getColumnId());
	}

	public RemoveColumn toDomain() {
		return new RemoveColumn(this.columnId);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
