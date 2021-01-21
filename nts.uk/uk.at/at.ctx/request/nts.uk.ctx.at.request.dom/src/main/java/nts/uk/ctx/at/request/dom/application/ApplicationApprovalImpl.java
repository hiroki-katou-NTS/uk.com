package nts.uk.ctx.at.request.dom.application;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository_Old;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarlyRepository;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImageRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampRepository_Old;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChangeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * @author Doan Duy Hung
 *
 */
@Stateless
@Transactional
public class ApplicationApprovalImpl implements ApplicationApprovalService {

	@Inject
	private ApplicationRepository applicationRepository;

	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;

	@Inject
	private AppStampRepository appStampRepository;
	
	@Inject
	private AppRecordImageRepository appRecordImageRepository;

	@Inject
	private OvertimeRepository overtimeRepository;

	@Inject
	private GoBackDirectlyRepository goBackDirectlyRepository;

	@Inject
	private AppWorkChangeRepository workChangeRepository;

	@Inject
	private ArrivedLateLeaveEarlyRepository lateOrLeaveEarlyRepository;

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
    private BusinessTripRepository businessTripRepo;

	@Override
	public void delete(String appID) {
		String companyID = AppContexts.user().companyId();
		Application application = applicationRepository.findByID(appID).get();
		switch (application.getAppType()) {
		case STAMP_APPLICATION:
			if (application.getOpStampRequestMode().isPresent()) {
				if (application.getOpStampRequestMode().get() == StampRequestMode.STAMP_ADDITIONAL) {
					appStampRepository.delete(companyID, appID);					
				} else {
					appRecordImageRepository.delete(companyID, appID);
				}
			}
			break;
		case OVER_TIME_APPLICATION:
			overtimeRepository.delete(companyID, appID);
			break;
		case GO_RETURN_DIRECTLY_APPLICATION:
			goBackDirectlyRepository.delete(companyID, appID);
			break;
		case WORK_CHANGE_APPLICATION:
			workChangeRepository.remove(companyID, appID);
			break;
		case EARLY_LEAVE_CANCEL_APPLICATION:
			lateOrLeaveEarlyRepository.remove(companyID, appID);
			break;
		case HOLIDAY_WORK_APPLICATION:
			appHolidayWorkRepository.delete(companyID, appID);
			Optional<BrkOffSupChangeMng> brOptional = this.brkOffSupChangeMngRepository.findHolidayAppID(appID);
			if(brOptional.isPresent()){
				Optional<Application> optapplicationLeaveApp = this.applicationRepository.findByID(companyID, brOptional.get().getAbsenceLeaveAppID());
				if(optapplicationLeaveApp.isPresent()){
					Application applicationLeaveApp = optapplicationLeaveApp.get();
					applicationLeaveApp.setVersion(applicationLeaveApp.getVersion());
					// applicationLeaveApp.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
					applicationRepository.update(applicationLeaveApp);
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
		case BUSINESS_TRIP_APPLICATION:
		    Optional<BusinessTrip> businessTrip = businessTripRepo.findByAppId(companyID, appID);
		    if (businessTrip.isPresent()) {
		        businessTripRepo.remove(businessTrip.get());
		    }
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

}
