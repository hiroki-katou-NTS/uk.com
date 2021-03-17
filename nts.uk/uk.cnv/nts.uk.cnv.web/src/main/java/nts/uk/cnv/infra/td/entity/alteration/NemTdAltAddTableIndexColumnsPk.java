package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Embeddable
public class NemTdAltAddTableIndexColumnsPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	private String AlterationId;

	@Column(name = "SEQ_NO")
	private int seqNo;

	@Column(name = "INDEX_ID")
	public String indexId;

	@Column(name = "ID")
	private String id;
}
