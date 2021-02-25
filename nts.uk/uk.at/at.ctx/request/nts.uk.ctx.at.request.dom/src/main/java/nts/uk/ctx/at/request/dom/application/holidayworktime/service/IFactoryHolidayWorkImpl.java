package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
@Stateless
public class IFactoryHolidayWorkImpl implements IFactoryHolidayWork{

	@Override
	public Application buildApplication(String appID, GeneralDate applicationDate, int prePostAtr,
			String appReasonID, String applicationReason,String employeeID) {
		// 会社ID
//				String companyId = AppContexts.user().companyId();
//				// 申請者
//				String applicantSID = AppContexts.user().employeeId();
//
//				Application_New app = new Application_New(
//						0L, 
//						companyId, 
//						appID,
//						EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
//						GeneralDateTime.now(), 
//						applicantSID,
//						new AppReason(Strings.EMPTY), 
//						applicationDate, 
//						new AppReason(applicationReason),
//						ApplicationType.HOLIDAY_WORK_APPLICATION, 
//						employeeID, Optional.of(applicationDate),
//						Optional.of(applicationDate), 
//						ReflectionInformation_New.firstCreate());
//				return app;
		return null;
	}

	@Override
	public AppHolidayWork_Old buildHolidayWork(String companyID, String appID, String workTypeCode,
			String siftCode, Integer workClockStart1, Integer workClockEnd1, Integer workClockStart2,
			Integer workClockEnd2, int goAtr1, int backAtr1, int goAtr2, int backAtr2, String divergenceReason,
			int overTimeShiftNight, List<HolidayWorkInput> holidayWorkInputs, 
			Optional<AppOvertimeDetail> appOvertimeDetail) {
		AppHolidayWork_Old appHolidayWork = AppHolidayWork_Old.createSimpleFromJavaType(companyID, appID, workTypeCode,
				siftCode, workClockStart1, workClockEnd1, workClockStart2, workClockEnd2,goAtr1,backAtr1,goAtr2,backAtr2, divergenceReason,
				overTimeShiftNight);
		appHolidayWork.setHolidayWorkInputs(holidayWorkInputs);
		appHolidayWork.setAppOvertimeDetail(appOvertimeDetail);
		return appHolidayWork;
	}

}
