package nts.uk.ctx.at.request.dom.applicationreflect.workschedule.service;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectScheDto;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectedStatesInfo;
@Stateless
public class WorkScheduleReflectServiceImpl implements WorkScheduleReflectService {
	private ApplicationReflectProcessSche appReflectProcess;

	@Override
	public ReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto) {
		Application_New application = reflectSheDto.getApplication();
		ReflectedStatesInfo reflectInfo = new ReflectedStatesInfo(ReflectedState_New.NOTREFLECTED, ReasonNotReflect_New.NOT_PROBLEM);
		// TODO 反映チェック処理
		
		//反映処理
		//残業申請
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			reflectInfo = new ReflectedStatesInfo(application.getReflectionInformation().getStateReflection(),
					application.getReflectionInformation().getNotReason().isPresent() ? application.getReflectionInformation().getNotReason().get() : ReasonNotReflect_New.NOT_PROBLEM);
			return reflectInfo;
		} else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION //直行直帰申請
				&& application.getPrePostAtr() == PrePostAtr.PREDICT){
			appReflectProcess.goBackDirectlyReflect(reflectSheDto);
			reflectInfo = new ReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
			
		} else if (application.getAppType() == ApplicationType.ABSENCE_APPLICATION //休暇申請
				&& application.getPrePostAtr() == PrePostAtr.PREDICT) {
			appReflectProcess.forleaveReflect(reflectSheDto);
			reflectInfo = new ReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
		}
		return reflectInfo;
	}

}
