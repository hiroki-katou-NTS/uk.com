package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 *
 *         トップページアラーム
 */
@Getter
public class TopPageAlarm {

	/** 会社ID */
	private String companyId;

	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;

	/** エラーの有無 */
	private ExistenceError existenceError;

	/** 中止フラグ */
	private IsCancelled isCancelled;

	/** 管理社員ごとの既読状態 */
	private List<TopPageAlarmManagerTr> lstManagerTr;

	public TopPageAlarm(String companyId, GeneralDateTime finishDateTime, ExistenceError existenceError,
			IsCancelled isCancelled, List<TopPageAlarmManagerTr> lstManagerTr) {
		this.companyId = companyId;
		this.finishDateTime = finishDateTime;
		this.existenceError = existenceError;
		this.isCancelled = isCancelled;
		this.lstManagerTr = lstManagerTr;
	}
}
