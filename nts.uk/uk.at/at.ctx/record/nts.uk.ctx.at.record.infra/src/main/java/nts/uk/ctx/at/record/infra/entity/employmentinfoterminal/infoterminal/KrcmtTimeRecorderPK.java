package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtTimeRecorderPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	/**
	 * コード
	 */
	@Column(name = "CD")
	public String timeRecordCode;

}
