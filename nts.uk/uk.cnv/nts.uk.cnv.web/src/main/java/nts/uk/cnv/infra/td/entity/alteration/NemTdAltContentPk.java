package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NemTdAltContentPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String alterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;
}
