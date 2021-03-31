package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.alteration.content.RemoveTable;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@SuppressWarnings("serial")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "NEM_TD_ALT_DELETE_TABLE")
public class NemTdAltDeleteTable extends NemTdAltContentBase implements Serializable {

	@EmbeddedId
	public NemTdAltContentPk pk;

	public static NemTdAltContentBase toEntity(NemTdAltContentPk contentPk) {
		return new NemTdAltDeleteTable(contentPk);
	}

	public RemoveTable toDomain() {
		return new RemoveTable();
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
