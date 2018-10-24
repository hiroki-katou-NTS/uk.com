package nts.uk.ctx.at.request.dom.application.overtime.service;

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
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FactoryOvertimeImpl implements IFactoryOvertime {

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
				ApplicationType.OVER_TIME_APPLICATION, 
				employeeID, Optional.of(applicationDate),
				Optional.of(applicationDate), 
				ReflectionInformation_New.firstCreate());
		return app;
	}

	@Override
	public AppOverTime buildAppOverTime(
			String companyID, 
			String appID, 
			int overTimeAtr, 
			String workTypeCode,
			String siftCode, 
			Integer workClockFrom1, 
			Integer workClockTo1, 
			Integer workClockFrom2, 
			Integer workClockTo2,
			String divergenceReason, 
			Integer flexExessTime, 
			Integer overTimeShiftNight, 
			List<OverTimeInput> overtimeInputs,
			Optional<AppOvertimeDetail> appOvertimeDetail) {
		
		AppOverTime appOverTime = AppOverTime.createSimpleFromJavaType(companyID, appID, overTimeAtr, workTypeCode,
				siftCode, workClockFrom1, workClockTo1, workClockFrom2, workClockTo2, divergenceReason, flexExessTime,
				overTimeShiftNight);
		appOverTime.setOverTimeInput(overtimeInputs);
		appOverTime.setAppOvertimeDetail(appOvertimeDetail);
		return appOverTime;
	}

}
