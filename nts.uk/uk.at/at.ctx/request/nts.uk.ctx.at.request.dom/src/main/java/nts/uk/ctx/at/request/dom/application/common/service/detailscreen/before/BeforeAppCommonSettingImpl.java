package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.scoped.session.SessionContextProvider;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.application.common.service.other.DataAppPhaseFrame;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.OutputAllDataApp;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/** 詳細画面起動前申請共通設定を取得する */
public class BeforeAppCommonSettingImpl implements BeforeAppCommonSetting {

	/** 15.詳細画面申請データを取得する */
	@Inject
	DataAppPhaseFrame getAllDataAppPhaseFrame;
	
	 @Inject
	 ApplicationRepository appRepo;
	                  

	/** 1-1.新規画面起動前申請共通設定を取得する */
	@Inject
	BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Override
	public PrelaunchAppSetting getPrelaunchAppSetting(String appID) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		
		OutputAllDataApp outputAllDataApp = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID );
		Application app = appRepo.getAppById(companyID, appID).get();
		
	
		
		Optional<Application> applicationInfo = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID).getApplication();
		Application appInfor = applicationInfo.get();
		// TODO: Tra Application Setting tu 1-1
		ApplicationSetting appCommonSetting = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, app.getApplicationType(),appInfor.getApplicationDate()).applicationSetting;
		GeneralDate cacheDate = SessionContextProvider.get().get("baseDate");
		return new PrelaunchAppSetting(appCommonSetting, cacheDate);
	}
}