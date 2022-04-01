package nts.uk.screen.com.app.find.cmm018;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.app.find.setting.request.application.AppUseAtrDto;
import nts.uk.ctx.at.request.app.find.setting.request.application.ApplicationUseAtrFinder;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ApproverOperationSettingsDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.R:登録画面の設定.メニュー別OCD.Ｒ：登録画面の設定を起動する
 * @author NWS-DungDV
 */
@Stateless
public class ScreenQueryInitRegistationScreenSetting {
	
	@Inject
	private ApplicationUseAtrFinder appUseAtrFinder;

	@Inject
	private ApproverOperationSettingsRepository approverOperationSetRepo;
	
	public InitRegistationScreenSettingDto get() {
		List<AppUseAtrDto> appUseAtrDtos = appUseAtrFinder.getAppUseAtr(0, null, null);
		
		Optional<ApproverOperationSettings> setting = approverOperationSetRepo.get(AppContexts.user().companyId());
		
		ApproverOperationSettingsDto settingDto = null;
		if (setting.isPresent()) {
			settingDto = ApproverOperationSettingsDto.fromDomain(setting.get());
		}
		
		return InitRegistationScreenSettingDto.builder()
				.appUseAtrs(appUseAtrDtos)
				.setting(settingDto)
				.build();

	}
}
