package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

public interface IFactoryOvertime {
	// 申請
	Application_New buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
			String applicationReason);

	// 残業申請
	AppOverTime buildAppOverTime(String companyID, String appID, int overTimeAtr, String workTypeCode, String siftCode,
			int workClockFrom1, int workClockTo1, int workClockFrom2, int workClockTo2, String divergenceReason,
			int flexExessTime, int overTimeShiftNight, List<OverTimeInput> overtimeInputs);
}
