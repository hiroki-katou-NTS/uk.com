package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentPk;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NemTdAltChangeTableConstraintsPk  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;

	@Column(name = "SUFFIX")
	public String suffix;

	public static NemTdAltChangeTableConstraintsPk asPK(NemTdAltContentPk parentPk) {
		return new NemTdAltChangeTableConstraintsPk(parentPk.alterationId, parentPk.seqNo, "PK");
	}

	public static NemTdAltChangeTableConstraintsPk asUK(NemTdAltContentPk parentPk, String suffix) {
		return new NemTdAltChangeTableConstraintsPk(parentPk.alterationId, parentPk.seqNo, suffix);
	}

	public static NemTdAltChangeTableConstraintsPk asIndex(NemTdAltContentPk parentPk, String suffix) {
		return new NemTdAltChangeTableConstraintsPk(parentPk.alterationId, parentPk.seqNo, suffix);
	}
}
