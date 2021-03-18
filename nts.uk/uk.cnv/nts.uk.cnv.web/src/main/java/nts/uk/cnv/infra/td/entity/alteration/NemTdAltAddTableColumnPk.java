package nts.uk.cnv.infra.td.entity.alteration;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class NemTdAltAddTableColumnPk implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ALTERATION_ID")
	public String AlterationId;

	@Column(name = "SEQ_NO")
	public int seqNo;

	@Column(name = "ID")
	public String id;

}
