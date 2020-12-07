package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author dungbn
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtTrRemoteBackupPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 契約コード
	 */
	@NotNull
	@Column(name = "CONTRACT_CD")
	public String contractCode;
	
	/**
	 * 就業情報端末コード
	 */
	@NotNull
	@Column(name = "TIMERECORDER_CD")
	public int timeRecordCode;
	
	/**
	 * 変数名
	 */
	@NotNull
	@Column(name = "VARIABLE_NAME")
	public String variableName;
}
