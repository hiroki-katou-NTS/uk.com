package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
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
		if(application.getStartDate().isPresent() && application.getEndDate().isPresent()) {
			for(int i = 0; application.getStartDate().get().daysTo(application.getEndDate().get()) - i >= 0; i++){
				GeneralDate loopDate = application.getStartDate().get().addDays(i);
				if(!processScheReflect.isSche(application.getEmployeeID(), loopDate)) {
					return false;
				}
			}
		} else {
			if(!processScheReflect.isSche(application.getEmployeeID(), application.getAppDate())) {
				return false;
			}
		}
		
		//反映チェック処理(Xử lý check phản ánh)
		//TODO: tam thoi chua goi den xu ly nay
		/*if(!this.checkBeforeReflected(application)) {
			return reflectedStatesInfo;
		}*/
		boolean isReflect = false;
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {			
			return false;
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
