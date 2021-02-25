package nts.uk.ctx.at.request.dom.application.stamp;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after.DetailAfterUpdate;
//import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.BeforePreBootMode;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class AppStampDetailDefaultImpl implements AppStampDetailDomainService {

	@Inject
	private AppStampRepository_Old appStampRepository; 
	
//	@Inject
//	private BeforePreBootMode beforePreBootMode;
	
	@Inject
	private AppStampCommonDomainService appStampCommonDomainService;
	
	/*@Inject
	private PreLaunchScreenSetting preLaunchScreenSetting;*/
	
	@Inject
	private DetailBeforeUpdate detailBeforeProcessRegister;
	
	@Inject
	private DetailAfterUpdate afterProcessDetail;
	
	@Inject
	private ApplicationRepository applicationRepository;
	
	@Override
	public void appStampPreProcess(AppStamp_Old appStamp) {
//		beforePreBootMode.judgmentDetailScreenMode(appStamp, appStamp.getApplicationDate());
//		// this.preLaunchScreenSetting
//		appStampCommonDomainService.appStampSet(appStamp.getCompanyID());
//		// 13.実績を取得する
	}

	@Override
	public ProcessResult appStampUpdate(String applicationReason, AppStamp_Old appStamp) {
		appStampCommonDomainService.appReasonCheck(applicationReason, appStamp);
		appStampCommonDomainService.validateReason(appStamp);
		return appStampUpdateProcess(appStamp);
	}
	
	private ProcessResult appStampUpdateProcess(AppStamp_Old appStamp) {
		// error EA refactor 4
		/*detailBeforeProcessRegister.processBeforeDetailScreenRegistration(appStamp.getApplication_New().getCompanyID(),
				appStamp.getApplication_New().getEmployeeID(), appStamp.getApplication_New().getAppDate(), 1,
				appStamp.getApplication_New().getAppID(), appStamp.getApplication_New().getPrePostAtr(),
				appStamp.getApplication_New().getVersion(), null, null);*/
		appStampRepository.updateStamp(appStamp);
		applicationRepository.update(appStamp.getApplication());
		/*return afterProcessDetail.processAfterDetailScreenRegistration(appStamp.getApplication_New());*/
		return null;
	}
}
