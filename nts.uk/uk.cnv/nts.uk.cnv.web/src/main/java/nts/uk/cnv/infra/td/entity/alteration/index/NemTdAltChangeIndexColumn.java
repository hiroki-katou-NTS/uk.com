package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;

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
@Table(name = "NEM_TD_ALT_CHANGE_INDEX_COLUMN")
public class NemTdAltChangeIndexColumn extends JpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public NemTdAltChangeTableConstraintsColumnPk pk;

	public static NemTdAltChangeIndexColumn toEntity(NemTdAltChangeTableConstraintsPk parent, String columnId) {
		return new NemTdAltChangeIndexColumn(
				new NemTdAltChangeTableConstraintsColumnPk(
					parent.alterationId,
					parent.seqNo,
					parent.suffix,
					columnId
				)
			);
	}

	@Override
	protected Object getKey() {
		return pk;
	}
}