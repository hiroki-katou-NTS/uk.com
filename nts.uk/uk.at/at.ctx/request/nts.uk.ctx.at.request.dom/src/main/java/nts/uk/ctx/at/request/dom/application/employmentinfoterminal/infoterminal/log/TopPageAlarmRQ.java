package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * @author ThanhNX
 *
 *         トップページアラーム
 */
@Getter
public class TopPageAlarmRQ {

	/** 会社ID */
	private String companyId;

	/** 実行完了日時 */
	private GeneralDateTime finishDateTime;

	/** エラーの有無 */
	private ExistenceErrorRQ existenceError;

	/** 中止フラグ */
	private IsCancelledRQ isCancelled;

	/** 管理社員ごとの既読状態 */
	private List<TopPageAlarmManagerTrRQ> lstManagerTr;

	public TopPageAlarmRQ(String companyId, GeneralDateTime finishDateTime, ExistenceErrorRQ existenceError,
			IsCancelledRQ isCancelled, List<TopPageAlarmManagerTrRQ> lstManagerTr) {
		this.companyId = companyId;
		this.finishDateTime = finishDateTime;
		this.existenceError = existenceError;
		this.isCancelled = isCancelled;
		this.lstManagerTr = lstManagerTr;
	}
}
