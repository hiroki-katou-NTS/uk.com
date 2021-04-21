package nts.uk.cnv.infra.td.entity.alteration.index;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NemTdAltChangeTableConstraintsColumnPk   implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;

	@Column(name = "SUFFIX")
	public String suffix;

	@Column(name = "COLUMN_ID")
	public String columnId;

	public static NemTdAltChangeTableConstraintsColumnPk create(NemTdAltChangePrimaryKey parent, String columnId) {
		return new NemTdAltChangeTableConstraintsColumnPk(
				parent.pk.alterationId,
				parent.pk.seqNo,
				parent.pk.suffix,
				columnId);
	}
}
