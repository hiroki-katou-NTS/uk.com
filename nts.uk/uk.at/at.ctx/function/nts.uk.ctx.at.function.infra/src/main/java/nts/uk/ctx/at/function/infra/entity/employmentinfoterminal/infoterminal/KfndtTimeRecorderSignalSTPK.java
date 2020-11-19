package nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfndtTimeRecorderSignalSTPK implements Serializable {
private static final long serialVersionUID = 1L;
	
	/**
	 * 契約コード
	 */
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	/**
	 * 就業情報端末コード
	 */
	@Column(name = "TIMERECORDER_CD")
	public int timeRecordCode;
}
