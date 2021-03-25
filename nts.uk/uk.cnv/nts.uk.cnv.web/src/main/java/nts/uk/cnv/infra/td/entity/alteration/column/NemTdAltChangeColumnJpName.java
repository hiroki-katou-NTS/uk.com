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
import nts.uk.cnv.dom.td.alteration.content.column.ChangeColumnJpName;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_COLUMN_JPNAME")
public class NemTdAltChangeColumnJpName extends NemTdAltContentBase implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	@Column(name = "JPNAME")
	public String jpName;

	public static NemTdAltChangeColumnJpName toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		val domain = (ChangeColumnJpName) ac;
		return new NemTdAltChangeColumnJpName(
				contentPk,
				domain.getColumnId(),
				domain.getJpName());
	}

	public ChangeColumnJpName toDomain() {
		return new ChangeColumnJpName(this.columnId, this.jpName);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
