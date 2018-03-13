package nts.uk.ctx.at.request.dom.applicationreflect.workschedule.service;

import javax.ejb.Stateless;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
<<<<<<< HEAD:nts.uk/uk.at/at.ctx/request/nts.uk.ctx.at.request.dom/src/main/java/nts/uk/ctx/at/request/dom/applicationreflect/workschedule/service/WorkScheduleReflectServiceImpl.java
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectScheDto;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectedStatesInfo;
=======
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.WorkReflectedStatesInfo;
>>>>>>> c6494c277b... 勤務実績に反映: 残業申請 (事前) ALL:nts.uk/uk.at/at.ctx/request/nts.uk.ctx.at.request.dom/src/main/java/nts/uk/ctx/at/request/dom/applicationreflect/service/workschedule/WorkScheduleReflectServiceImpl.java
@Stateless
public class WorkScheduleReflectServiceImpl implements WorkScheduleReflectService {
	private ApplicationReflectProcessSche appReflectProcess;

	@Override
	public ScheReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto) {
		Application_New application = reflectSheDto.getApplication();
		ScheReflectedStatesInfo reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.NOTREFLECTED, ReasonNotReflect_New.NOT_PROBLEM);
		// TODO 反映チェック処理
		
		//反映処理
		//残業申請
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			reflectInfo = new ScheReflectedStatesInfo(application.getReflectionInformation().getStateReflection(),
					application.getReflectionInformation().getNotReason().isPresent() ? application.getReflectionInformation().getNotReason().get() : ReasonNotReflect_New.NOT_PROBLEM);
			return reflectInfo;
		} else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION //直行直帰申請
				&& application.getPrePostAtr() == PrePostAtr.PREDICT){
			appReflectProcess.goBackDirectlyReflect(reflectSheDto);
			reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
			
		} else if (application.getAppType() == ApplicationType.ABSENCE_APPLICATION //休暇申請
				&& application.getPrePostAtr() == PrePostAtr.PREDICT) {
			appReflectProcess.forleaveReflect(reflectSheDto);
			reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
		}
		return reflectInfo;
	}

}
