package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.log;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 *
 *         トップページアラーム
 */
@Getter
public class TopPageAlarmPub {

	/** 会社ID */
	private String companyId;

	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;

	/** エラーの有無 */
	private ExistenceErrorPub existenceError;

	/** 中止フラグ */
	private IsCancelledPub isCancelled;

	/** 管理社員ごとの既読状態 */
	private List<TopPageAlarmManagerTrPub> lstManagerTr;

	public TopPageAlarmPub(String companyId, GeneralDateTime finishDateTime, ExistenceErrorPub existenceError,
			IsCancelledPub isCancelled, List<TopPageAlarmManagerTrPub> lstManagerTr) {
		this.companyId = companyId;
		this.finishDateTime = finishDateTime;
		this.existenceError = existenceError;
		this.isCancelled = isCancelled;
		this.lstManagerTr = lstManagerTr;
	}
}
