package nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtTopPgAlManagerTrPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String cid;

	/**
	 * 就業情報端末コード
	 */
	@Column(name = "TIMERECORDER_CD")
	public String timeRecordCode;

	/**
	 * 実行完了日時
	 */
	@Column(name = "FINISH_TIME")
	public GeneralDateTime finishTime;
	
	/**
	 * 管理社員ID
	 */
	@Column(name = "MANAGER_SID")
	public String managerSid;

}
