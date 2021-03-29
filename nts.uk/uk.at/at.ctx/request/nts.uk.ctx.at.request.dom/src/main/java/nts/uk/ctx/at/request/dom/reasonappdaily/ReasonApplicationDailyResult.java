package nts.uk.ctx.at.request.dom.reasonappdaily;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;

/**
 * @author thanh_nx
 *
 *         日別実績の申請理由
 */
@Getter
public class ReasonApplicationDailyResult {

	// 社員ID
	private final String employeeId;

	// 年月日
	private final GeneralDate date;

	// 申請種類
	private final ApplicationTypeReason applicationTypeReason;

	// 事前事後区分
	private final PrePostAtr prePostAtr;

	// 申請理由
	private final ApplicationReasonInfo reasonInfo;

	public ReasonApplicationDailyResult(String employeeId, GeneralDate date,
			ApplicationTypeReason applicationTypeReason, PrePostAtr prePostAtr, ApplicationReasonInfo reasonInfo) {
		this.employeeId = employeeId;
		this.date = date;
		this.applicationTypeReason = applicationTypeReason;
		this.prePostAtr = prePostAtr;
		this.reasonInfo = reasonInfo;
	}

}
