package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.scoped.session.SessionContextProvider;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/** 詳細画面起動前申請共通設定を取得する */
public class BeforeAppCommonSettingImpl implements BeforeAppCommonSetting {

	 @Inject
	 ApplicationRepository appRepo;
	                  

	/** 1-1.新規画面起動前申請共通設定を取得する */
	@Inject
	BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Override
	public PrelaunchAppSetting getPrelaunchAppSetting(String appID) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		Application app = appRepo.findByID(companyID, appID).get();
		// TODO: Tra Application Setting tu 1-1
		/*
		ApplicationSetting appCommonSetting = beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, app.getAppType(),app.getAppDate()).applicationSetting;
		GeneralDate cacheDate = SessionContextProvider.get().get("baseDate");
		return new PrelaunchAppSetting(appCommonSetting, cacheDate);
		*/
		return null;
	}
}