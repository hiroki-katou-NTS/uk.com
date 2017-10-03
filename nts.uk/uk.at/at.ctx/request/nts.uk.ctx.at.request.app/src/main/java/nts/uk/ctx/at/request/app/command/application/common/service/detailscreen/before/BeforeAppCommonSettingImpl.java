package nts.uk.ctx.at.request.app.command.application.common.service.detailscreen.before;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.scoped.session.SessionContextProvider;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.GetAllDataAppPhaseFrame;
import nts.uk.ctx.at.request.dom.application.common.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.PrelaunchAppSetting;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.BeforePrelaunchAppCommonSet;
import nts.uk.ctx.at.request.dom.setting.request.application.applicationsetting.ApplicationSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
/** 詳細画面起動前申請共通設定を取得する */
public class BeforeAppCommonSettingImpl implements BeforeAppCommonSetting {

	/** 15.詳細画面申請データを取得する */
	@Inject
	GetAllDataAppPhaseFrame getAllDataAppPhaseFrame;

	/** 1-1.新規画面起動前申請共通設定を取得する */
	@Inject
	BeforePrelaunchAppCommonSet beforePrelaunchAppCommonSet;

	@Override
	public PrelaunchAppSetting getPrelaunchAppSetting(String appID) {
		String companyID = AppContexts.user().companyId();
		String employeeID = AppContexts.user().employeeId();
		
		ApplicationType appType = EnumAdaptor.valueOf(getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID).getApplicationDto().get().getApplicationType(), ApplicationType.class);
		
		/** Tra ve Dto ??? */
		
		Optional<ApplicationDto> applicationInfo = getAllDataAppPhaseFrame.getAllDataAppPhaseFrame(appID).getApplicationDto();
		ApplicationDto appInfor = applicationInfo.get();
		// TODO: Tra Application Setting tu 1-1
		ApplicationSetting appCommonSetting = null;
		beforePrelaunchAppCommonSet.prelaunchAppCommonSetService(companyID, employeeID, 1, appType,appInfor.getApplicationDate());

		GeneralDate cacheDate = SessionContextProvider.get().get("baseDate");
		return new PrelaunchAppSetting(appCommonSetting, cacheDate);
	}
}