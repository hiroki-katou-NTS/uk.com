package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository_Old;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.request.application.common.BaseDateFlg;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
@Transactional
public class ApplicationApprovalImpl_New implements ApplicationApprovalService {

	@Inject
	private ApplicationRepository_New applicationRepository_Old;
	
	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Inject
	private AppStampRepository appStampRepository;

	@Inject
	private OvertimeRepository overtimeRepository;

	@Inject
	private GoBackDirectlyRepository_Old goBackDirectlyRepository;

	@Inject
	private IAppWorkChangeRepository workChangeRepository;

	@Inject
	private LateOrLeaveEarlyRepository lateOrLeaveEarlyRepository;
	
	@Inject
	private AppHolidayWorkRepository appHolidayWorkRepository;
	
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	
	@Inject
	private RecruitmentAppRepository recRepo;
	
	@Inject
	private AppAbsenceRepository appAbsenceRepository;
	@Inject
	private BrkOffSupChangeMngRepository brkOffSupChangeMngRepository;
	
	@Inject
	private ApplicationSettingRepository applicationSettingRepository;

	@Override
	public void insert(Application application) {
		String companyID = AppContexts.user().companyId();
		applicationRepository.insert(application);
		BaseDateFlg baseDateFlg = applicationSettingRepository.getApplicationSettingByComID(companyID)
				.map(x -> x.getBaseDateFlg()).orElse(BaseDateFlg.SYSTEM_DATE);
		GeneralDate targetDate = baseDateFlg.equals(BaseDateFlg.SYSTEM_DATE) ? GeneralDate.today() : application.getAppDate().getApplicationDate();
		approvalRootStateAdapter.insertByAppType(companyID, application.getEmployeeID(),
				application.getAppType().value, application.getAppDate().getApplicationDate(), application.getAppID(), targetDate);
	}

	@Override
	public void delete(String appID) {
		String companyID = AppContexts.user().companyId();
		Application application = applicationRepository.findByID(appID).get();
		switch (application.getAppType()) {
		case STAMP_APPLICATION:
			appStampRepository.delete(companyID, appID);
			break;
		case OVER_TIME_APPLICATION:
			overtimeRepository.delete(companyID, appID);
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			goBackDirectlyRepository.delete(companyID, appID);
			break;
		case WORK_CHANGE_APPLICATION:
			workChangeRepository.delete(companyID, appID);
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			lateOrLeaveEarlyRepository.remove(companyID, appID);
			break;
		case LEAVE_TIME_APPLICATION:
			appHolidayWorkRepository.delete(companyID, appID);
			Optional<BrkOffSupChangeMng> brOptional = this.brkOffSupChangeMngRepository.findHolidayAppID(appID);
			if(brOptional.isPresent()){
				Optional<Application_New> optapplicationLeaveApp = this.applicationRepository_Old.findByID(companyID, brOptional.get().getAbsenceLeaveAppID());
				if(optapplicationLeaveApp.isPresent()){
					Application_New applicationLeaveApp = optapplicationLeaveApp.get();
					applicationLeaveApp.setVersion(applicationLeaveApp.getVersion());
					applicationLeaveApp.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
					applicationRepository_Old.update(applicationLeaveApp);
				}
				this.brkOffSupChangeMngRepository.remove(appID, brOptional.get().getAbsenceLeaveAppID());
			}
			break;
		case COMPLEMENT_LEAVE_APPLICATION:
			absRepo.remove(appID);
			recRepo.remove(appID);
			break;
		case ABSENCE_APPLICATION:
			appAbsenceRepository.delete(companyID, appID);
			break;
		default:
			break;
		}
		applicationRepository.remove(appID);
		approvalRootStateAdapter.deleteApprovalRootState(appID);

	}

	@Override
	public void insertApp(Application application, List<ApprovalPhaseStateImport_New> listApprovalPhaseState) {
		applicationRepository.insert(application);
		approvalRootStateAdapter.insertApp(
				application.getAppID(), 
				application.getAppDate().getApplicationDate(), 
				application.getEmployeeID(), 
				listApprovalPhaseState);
	}

	@Override
	public void updateApp(Application application) {
		Application dbApplication = applicationRepository.findByID(application.getAppID()).get();
		application.setVersion(dbApplication.getVersion() + 1);
		applicationRepository.update(application);
	}

}
