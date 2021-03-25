package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableJpName;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_TABLE_JPNAME")
public class NemTdAltChangeTableJpName extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "JPNAME")
	public String jpName;

	public static NemTdAltChangeTableJpName toEntity(NemTdAltContentPk pk, ChangeTableJpName d) {
		val e = new NemTdAltChangeTableJpName();
		e.pk = pk;
		e.jpName = d.getJpName();
		return e;
	}

	public ChangeTableJpName toDomain() {
		return new ChangeTableJpName(this.jpName);
	}

	public static NemTdAltContentBase toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		return new NemTdAltChangeTableJpName(contentPk, ((ChangeTableJpName)ac).getJpName());
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
