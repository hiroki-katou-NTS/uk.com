package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtTrSendWorkTypePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;

	/**
	 * 就業情報端末コード
	 */
	@Column(name = "TIMERECORDER_CD")
	public int timeRecordCode;
	
	/**
	 * 勤務種類コード
	 */
	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;

}
