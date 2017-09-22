package nts.uk.ctx.at.request.dom.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.AfterProcessDetail;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeProcessRegister;
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
	private DetailBeforeProcessRegister detailBeforeProcessRegister;
	
	@Inject
	private AfterProcessDetail afterProcessDetail;
	
	@Override
	public void appStampPreProcess(AppStamp appStamp) {
		this.beforePreBootMode.getDetailedScreenPreBootMode(appStamp, appStamp.getApplicationDate());
		// this.preLaunchScreenSetting
		this.appStampCommonDomainService.appStampSet(appStamp.getCompanyID());
		// 13.実績を取得する
	}

	@Override
	public void appStampGoOutPermitUpdate(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampUpdate(appStamp);
		
	}

	@Override
	public void appStampWorkUpdate(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampUpdate(appStamp);
		
	}

	@Override
	public void appStampCancelUpdate(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampUpdate(appStamp);
		
	}

	@Override
	public void appStampOnlineRecordUpdate(String titleReason, String detailReason, AppStamp appStamp) {
		this.appStampCommonDomainService.appReasonCheck(titleReason, detailReason, appStamp);
		this.appStampCommonDomainService.validateReason(appStamp);
		this.appStampUpdate(appStamp);
		
	}

	@Override
	public void appStampOtherUpdate(String titleReason, String detailReason, AppStamp appStamp) {
		// TODO Auto-generated method stub
		
	}
	
	private void appStampUpdate(AppStamp appStamp) {
		StampRequestMode StampRequestMode = appStamp.getStampRequestMode();
		this.detailBeforeProcessRegister.processBeforeDetailScreenRegistration(
				appStamp.getCompanyID(), 
				appStamp.getApplicantSID(), 
				appStamp.getApplicationDate(), 
				1, 
				appStamp.getApplicationID(), 
				PrePostAtr.PREDICT);
		switch(StampRequestMode){
			case STAMP_GO_OUT_PERMIT: appStampRepository.updateStampGoOutPermit(appStamp);break;
			case STAMP_ADDITIONAL: appStampRepository.updateStampWork(appStamp);break;
			case STAMP_CANCEL: appStampRepository.updateStampCancel(appStamp);break;
			case STAMP_ONLINE_RECORD: appStampRepository.updateStampOnlineRecord(appStamp);break;
			default: break;
		}
		this.afterProcessDetail.processAfterDetailScreenRegistration(appStamp.getCompanyID(), appStamp.getApplicationID());
	}
}
