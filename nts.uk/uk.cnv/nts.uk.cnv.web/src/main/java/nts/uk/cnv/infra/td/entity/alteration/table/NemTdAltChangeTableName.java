package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.alteration.content.ChangeTableName;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_CHANGE_TABLE_NAME")
public class NemTdAltChangeTableName extends NemTdAltContentBase implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltContentPk pk;

	@Column(name = "NAME")
	public String name;

	public static NemTdAltChangeTableName toEntity(NemTdAltContentPk contentPk, AlterationContent ac) {
		return new NemTdAltChangeTableName(contentPk, ((ChangeTableName)ac).getTableName());
	}

	public ChangeTableName toDomain() {
		return new ChangeTableName(this.name);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
