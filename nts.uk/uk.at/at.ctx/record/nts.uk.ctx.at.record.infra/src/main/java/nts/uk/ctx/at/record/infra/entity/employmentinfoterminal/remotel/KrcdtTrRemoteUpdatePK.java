package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtTrRemoteUpdatePK implements Serializable {
	/**
	* 
	*/
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
	public String timeRecordCode;

	/**
	 * 変数名
	 */
	@Column(name = "VARIABLE_NAME")
	public String variableName;
}
