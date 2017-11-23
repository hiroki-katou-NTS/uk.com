package nts.uk.ctx.at.request.dom.application.workchange;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;

public interface IFactoryWorkChange {
	/**
	 * 申請
	 */
	Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
			String applicationReason, List<AppApprovalPhase> listAppApprovalPhase);

	/**
	 * 勤務変更申請
	 */
	AppWorkChange buildAppWorkChange();
}
