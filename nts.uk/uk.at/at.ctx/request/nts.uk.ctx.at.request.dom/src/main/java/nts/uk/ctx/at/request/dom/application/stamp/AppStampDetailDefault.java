package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhaseRepository;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrameRepository;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAcceptedRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampDetailDefault implements AppStampDetailDomainService {

	@Inject
	private AppStampRepository appStampRepository; 
	
	@Inject
	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	/*@Inject
	private PreLaunchScreenSetting preLaunchScreenSetting;*/
	
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegister;
	
	@Inject
	private DetailAfterUpdate afterProcessDetail;
	
	@Override
	public void appStampPreProcess(AppStamp appStamp) {
//		beforePreBootMode.judgmentDetailScreenMode(appStamp, appStamp.getApplicationDate());
//		// this.preLaunchScreenSetting
//		appStampCommonDomainService.appStampSet(appStamp.getCompanyID());
//		// 13.実績を取得する
	}

	@Override
	public void appStampUpdate(String applicationReason, AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		appStampCommonDomainService.appReasonCheck(applicationReason, appStamp);
		appStampCommonDomainService.validateReason(appStamp);
		appStampUpdateProcess(appStamp, appApprovalPhases);
	}
	
	private void appStampUpdateProcess(AppStamp appStamp, List<AppApprovalPhase> appApprovalPhases) {
		/*appStamp.setListPhase(appApprovalPhases);
		detailBeforeProcessRegister.processBeforeDetailScreenRegistration(
				appStamp.getCompanyID(), 
				appStamp.getApplicantSID(), 
				appStamp.getApplicationDate(), 
				1, 
				appStamp.getApplicationID(), 
				PrePostAtr.PREDICT);
		appStampRepository.updateStamp(appStamp);
		afterProcessDetail.processAfterDetailScreenRegistration(appStamp);*/
	}
}
