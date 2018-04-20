package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppDegreeReflectionAtr;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppExecutionType;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectInfor;
import nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.AppReflectProcessRecord;
@Stateless
public class WorkScheduleReflectServiceImpl implements WorkScheduleReflectService{
	@Inject
	private ScBasicScheduleAdapter basicScheAdapter;
	@Inject
	private AppReflectProcessRecord appReflectProcess;
	@Inject
	private ApplicationReflectProcessSche processScheReflect;

	@Override
	public boolean workscheReflect(ReflectScheDto reflectParam) {
		Application_New application = reflectParam.getAppInfor();
		
		if(application.getPrePostAtr() != PrePostAtr.PREDICT) {
			return false;
		}
				
		//反映チェック処理(Xử lý check phản ánh)
		//TODO: tam thoi chua goi den xu ly nay
		/*if(!this.checkBeforeReflected(application)) {
			return reflectedStatesInfo;
		}*/
		ReflectScheDto reflectSchePara = new ReflectScheDto(application.getEmployeeID(),
				application.getAppDate(),
				ExecutionType.NORMALECECUTION, 
				true, 
				ApplyTimeRequestAtr.START, 
				application,
				null, 
				null, 
				null,
				null);
		boolean isReflect = false;
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {			
			return false;
		}  else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION){			
			GoBackDirectly gobackData = reflectParam.getGoBackDirectly();
			reflectSchePara.setGoBackDirectly(gobackData);
			isReflect = processScheReflect.goBackDirectlyReflect(reflectSchePara);
		} else if(application.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION) {
			AppWorkChange appWorkChangeData = reflectParam.getWorkChange();
			reflectSchePara.setWorkChange(appWorkChangeData);
			isReflect = processScheReflect.workChangeReflect(reflectSchePara);
		} else if(application.getAppType() == ApplicationType.ABSENCE_APPLICATION) {
			reflectSchePara.setForLeave(reflectParam.getForLeave());
			isReflect = processScheReflect.forleaveReflect(reflectSchePara);
		} else if (application.getAppType() == ApplicationType.BREAK_TIME_APPLICATION) {
			/**TODO chua doi ung lan nay
			/*reflectSchePara.setHolidayWork(reflectParam.getHolidayWork());
			isReflect = processScheReflect.holidayWorkReflect(reflectSchePara);*/
		}
		return isReflect;
	}

	@Override
	public boolean checkBeforeReflected(Application_New application) {
		//ドメインモデル「勤務予定基本情報」を取得する
		Optional<ScBasicScheduleImport> optBasicSche = basicScheAdapter.findByID(application.getEmployeeID(), application.getAppDate());
		if(!optBasicSche.isPresent()) {
			return false;
		}
		//反映状況によるチェック(Check dựa vào trạng thái phản ánh)
		AppReflectInfor beforeReflect = new AppReflectInfor(AppDegreeReflectionAtr.SCHEDULE,
				AppExecutionType.EXCECUTION,
				application.getReflectionInformation().getStateReflection(),
				application.getReflectionInformation().getStateReflectionReal());
		return appReflectProcess.appReflectProcessRecord(beforeReflect);
		//確定状態によるチェック(Check dựa vào trạng thái xác nhận)
		//TODO cha hieu gi
		
	}

}
