package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
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
	@Inject
	private GoBackDirectlyRepository gobackRepository;
	@Inject
	private IAppWorkChangeRepository workchangeRepo;

	@Override
	public ScheReflectedStatesInfo workscheReflect(Application_New application) {
		ScheReflectedStatesInfo reflectedStatesInfo = new ScheReflectedStatesInfo(application.getReflectionInformation().getStateReflection(),
				application.getReflectionInformation().getNotReason().isPresent() ? application.getReflectionInformation().getNotReason().get() : null);
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
				null);
		boolean isReflect = false;
		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
			reflectedStatesInfo = new ScheReflectedStatesInfo(application.getReflectionInformation().getStateReflection(),
					application.getReflectionInformation().getNotReason().isPresent() ? application.getReflectionInformation().getNotReason().get() : null);
			return reflectedStatesInfo;
		}  else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION //直行直帰申請
				&& application.getPrePostAtr() == PrePostAtr.PREDICT){
			Optional<GoBackDirectly> optGobackData = gobackRepository.findByApplicationID(application.getCompanyID(), application.getAppID());
			if(!optGobackData.isPresent()) {
				return reflectedStatesInfo;
			}
			GoBackDirectly gobackData = optGobackData.get();			
			reflectSchePara.setGoBackDirectly(gobackData);
			isReflect = processScheReflect.goBackDirectlyReflect(reflectSchePara);
		} else if(application.getAppType() == ApplicationType.WORK_CHANGE_APPLICATION
				&& application.getPrePostAtr() == PrePostAtr.PREDICT) {
			Optional<AppWorkChange> getAppworkChangeById = workchangeRepo.getAppworkChangeById(application.getCompanyID(), application.getAppID());
			if(!getAppworkChangeById.isPresent()) {
				return reflectedStatesInfo;
			}
			AppWorkChange appWorkChangeData = getAppworkChangeById.get();
			reflectSchePara.setWorkChange(appWorkChangeData);
			isReflect = processScheReflect.workChangeReflect(reflectSchePara);
		}
		if(isReflect){
			reflectedStatesInfo.setReflectedSate(ReflectedState_New.REFLECTED);
			reflectedStatesInfo.setNotReflectReson(ReasonNotReflect_New.WORK_FIXED);
		}
		return reflectedStatesInfo;
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

//	@Override
//	public ReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public ScheReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto) {
//		// TODO: Fix tạm theo chị dự bảo thế cho hết error
//		Application_New application = null;
//		ScheReflectedStatesInfo reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.NOTREFLECTED, ReasonNotReflect_New.NOT_PROBLEM);
//		// TODO 反映チェック処理
//		
//		//反映処理
//		//残業申請
//		if(application.getAppType() == ApplicationType.OVER_TIME_APPLICATION) {
//			reflectInfo = new ScheReflectedStatesInfo(application.getReflectionInformation().getStateReflection(),
//					application.getReflectionInformation().getNotReason().isPresent() ? application.getReflectionInformation().getNotReason().get() : ReasonNotReflect_New.NOT_PROBLEM);
//			return reflectInfo;
//		} else if (application.getAppType() == ApplicationType.GO_RETURN_DIRECTLY_APPLICATION //直行直帰申請
//				&& application.getPrePostAtr() == PrePostAtr.PREDICT){
//			appReflectProcess.goBackDirectlyReflect(reflectSheDto);
//			reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
//			
//		} else if (application.getAppType() == ApplicationType.ABSENCE_APPLICATION //休暇申請
//				&& application.getPrePostAtr() == PrePostAtr.PREDICT) {
//			appReflectProcess.forleaveReflect(reflectSheDto);
//			reflectInfo = new ScheReflectedStatesInfo(ReflectedState_New.REFLECTED, ReasonNotReflect_New.WORK_FIXED);
//		}
//		return reflectInfo;
//	}

}
