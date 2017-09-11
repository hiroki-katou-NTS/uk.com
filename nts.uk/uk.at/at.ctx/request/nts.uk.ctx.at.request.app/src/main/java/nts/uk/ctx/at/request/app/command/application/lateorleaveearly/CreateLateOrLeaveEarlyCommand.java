package nts.uk.ctx.at.request.app.command.application.lateorleaveearly;

import lombok.Getter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Getter
public class CreateLateOrLeaveEarlyCommand {
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** 実績取消区分 */
	private int actualCancelAtr;

	/** 早退1 */
	private int early1;

	/** 早退時刻1 */
	private int earlyTime1;

	/** 遅刻1 */
	private int late1;

	/** 遅刻時刻1 */
	private int lateTime1;

	/** 早退2 */
	private int early2;

	/** 早退時刻2 */
	private int earlyTime2;

	/** 遅刻2 */
	private int late2;

	/** 遅刻時刻2 */
	private int lateTime2;

	public LateOrLeaveEarly toDomain(String appID) {
		 new LateOrLeaveEarly(
				AppContexts.user().companyId(),
				appID,
				this.actualCancelAtr,
				this.early1,
				this.earlyTime1,
				this.late1,
				this.lateTime1,
				this.early2,
				this.earlyTime2,
				this.late2,
				this.lateTime2);

	}
}
