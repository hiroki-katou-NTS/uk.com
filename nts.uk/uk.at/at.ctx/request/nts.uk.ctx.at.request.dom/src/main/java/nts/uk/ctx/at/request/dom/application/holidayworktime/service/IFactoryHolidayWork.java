package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;

public interface IFactoryHolidayWork {
	// 申請
		Application_New buildApplication(String appID, GeneralDate applicationDate, int prePostAtr, String appReasonID,
				String applicationReason,String employeeID);

		// 残業申請
		AppHolidayWork buildHolidayWork(String companyID, String appID, String workTypeCode, String siftCode,
				Integer workClockStart1, Integer workClockEnd1, Integer workClockStart2, Integer workClockEnd2,int goAtr1,int backAtr1,int goAtr2, int backAtr2, String divergenceReason,
				int overTimeShiftNight, List<HolidayWorkInput> holidayWorkInputs, Optional<AppOvertimeDetail> appOvertimeDetail);

}
