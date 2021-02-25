package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime_Old;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;

public interface IFactoryOvertime {
	// 申請
	Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
			String applicationReason,String employeeID);

	// 残業申請
	AppOverTime_Old buildAppOverTime(String companyID, String appID, int overTimeAtr, String workTypeCode, String siftCode,
			Integer workClockFrom1, Integer workClockTo1, Integer workClockFrom2, Integer workClockTo2, String divergenceReason,
			Integer flexExessTime, Integer overTimeShiftNight, List<OverTimeInput> overtimeInputs, Optional<AppOvertimeDetail> appOvertimeDetail);
}
