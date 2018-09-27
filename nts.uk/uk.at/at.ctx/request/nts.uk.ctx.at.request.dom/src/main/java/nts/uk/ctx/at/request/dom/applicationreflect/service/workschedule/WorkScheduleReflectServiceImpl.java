package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.ReflectInformationResult;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
@Stateless
public class WorkScheduleReflectServiceImpl implements WorkScheduleReflectService{
	@Inject
	private ApplicationReflectProcessSche processScheReflect;
	@Inject
	private AppReflectProcessRecord checkReflect;
	@Override
	public ReflectInformationResult workscheReflect(ReflectScheDto reflectParam) {
		Application_New application = reflectParam.getAppInfor();
		
		if(application.getPrePostAtr() != PrePostAtr.PREDICT) {
			return ReflectInformationResult.CHECKFALSE;
		}
		//反映チェック処理(Xử lý check phản ánh)		
		if(!checkReflect.appReflectProcessRecord(application, false)) {
			return ReflectInformationResult.CHECKFALSE;
		}
		boolean isReflect = true;
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {			
			return ReflectInformationResult.CHECKFALSE;
		}  else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION){
			isReflect = processScheReflect.goBackDirectlyReflect(reflectParam);
		} else if(application.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
			isReflect = processScheReflect.workChangeReflect(reflectParam);
		} else if(application.getAppType() == ApplicationType.ABSENCE_APPLICATION) {
			isReflect = processScheReflect.forleaveReflect(reflectParam);
		} else if (application.getAppType() == ApplicationType.BREAK_TIME_APPLICATION) {
			/**TODO chua doi ung lan nay
			/*reflectSchePara.setHolidayWork(reflectParam.getHolidayWork());
			isReflect = processScheReflect.holidayWorkReflect(reflectSchePara);*/
		} else if (application.getAppType() == ApplicationType.COMPLEMENT_LEAVE_APPLICATION) {
			if(reflectParam.getAbsenceLeave() != null) {
				isReflect = processScheReflect.ebsenceLeaveReflect(reflectParam);
			} 
			if(reflectParam.getRecruitment() != null) {
				isReflect = processScheReflect.recruitmentReflect(reflectParam);
			}
		}
		return isReflect ? ReflectInformationResult.DONE : ReflectInformationResult.NOTDONE;
	}

}
