package nts.uk.cnv.infra.td.entity.alteration.constraints;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ChangeTableConstraintsColumnPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;

	@Column(name = "SUFFIX")
	public String suffix;

	@Column(name = "COLUMN_ID")
	public String columnId;

	public static ChangeTableConstraintsColumnPk create(ChangeTableConstraintsPk parent, String columnId) {
		return new ChangeTableConstraintsColumnPk(
				parent.alterationId,
				parent.seqNo,
				parent.suffix,
				columnId);
	}
}
