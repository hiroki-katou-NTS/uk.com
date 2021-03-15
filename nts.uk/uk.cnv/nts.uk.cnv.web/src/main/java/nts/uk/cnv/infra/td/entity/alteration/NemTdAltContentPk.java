package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NemTdAltContentPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	private String alterationId;

	@Column(name = "SEQ_NO")
	private int seqNo;
}
