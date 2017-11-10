package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;

public interface IFactoryOvertime {
	// 申請
	Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
			String applicationReason, List<AppApprovalPhase> listAppApprovalPhase);

	// 残業申請
	AppOverTime buildAppOverTime();
}
