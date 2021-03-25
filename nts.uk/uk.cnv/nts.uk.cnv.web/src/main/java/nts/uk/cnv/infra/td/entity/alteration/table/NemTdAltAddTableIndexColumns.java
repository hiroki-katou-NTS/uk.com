package nts.uk.cnv.infra.td.entity.alteration.table;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.JpaEntity;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NEM_TD_ALT_ADD_TABLE_INDEX_COLUMN")
public class NemTdAltAddTableIndexColumns extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltAddTableIndexColumnsPk pk;

	@Column(name = "COLUMN_ID")
	public String columnId;

	public static NemTdAltAddTableIndexColumns create(NemTdAltAddTableIndex parent, int columnOrder, String columnId) {
		return new NemTdAltAddTableIndexColumns(
				NemTdAltAddTableIndexColumnsPk.create(parent.pk, columnOrder),
				columnId);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}
