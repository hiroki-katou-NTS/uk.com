package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import org.apache.logging.log4j.util.Strings;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReflectionInformation_New;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class IFactoryHolidayWorkImpl implements IFactoryHolidayWork{

	@Override
	public Application_New buildApplication(String appID, GeneralDate applicationDate, int prePostAtr,
			String appReasonID, String applicationReason,String employeeID) {
		// 会社ID
				String companyId = AppContexts.user().companyId();
				// 申請者
				String applicantSID = AppContexts.user().employeeId();

				Application_New app = new Application_New(
						0L, 
						companyId, 
						appID,
						EnumAdaptor.valueOf(prePostAtr, PrePostAtr.class), 
						GeneralDateTime.now(), 
						applicantSID,
						new AppReason(Strings.EMPTY), 
						applicationDate, 
						new AppReason(applicationReason),
						ApplicationType.BREAK_TIME_APPLICATION, 
						employeeID, Optional.of(applicationDate),
						Optional.of(applicationDate), 
						ReflectionInformation_New.firstCreate());
				return app;
	}

	@Override
	public AppHolidayWork buildHolidayWork(String companyID, String appID, String workTypeCode,
			String siftCode, Integer workClockStart1, Integer workClockEnd1, Integer workClockStart2,
			Integer workClockEnd2, int goAtr1, int backAtr1, int goAtr2, int backAtr2, String divergenceReason,
			int overTimeShiftNight, List<HolidayWorkInput> holidayWorkInputs, 
			Optional<AppOvertimeDetail> appOvertimeDetail) {
		AppHolidayWork appHolidayWork = AppHolidayWork.createSimpleFromJavaType(companyID, appID, workTypeCode,
				siftCode, workClockStart1, workClockEnd1, workClockStart2, workClockEnd2,goAtr1,backAtr1,goAtr2,backAtr2, divergenceReason,
				overTimeShiftNight);
		appHolidayWork.setHolidayWorkInputs(holidayWorkInputs);
		appHolidayWork.setAppOvertimeDetail(appOvertimeDetail);
		return appHolidayWork;
	}

}
