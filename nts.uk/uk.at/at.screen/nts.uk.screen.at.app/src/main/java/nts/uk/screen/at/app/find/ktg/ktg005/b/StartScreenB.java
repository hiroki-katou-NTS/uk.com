package nts.uk.screen.at.app.find.ktg.ktg005.b;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApplicationStatusWidgetItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.screen.at.app.find.ktg.ktg005.a.ApplicationStatusDetailedSettingDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 
 * @author sonnlb
 *         UKDesign.UniversalK.就業.KTG_ウィジェット.KTG005_申請件数.B：設定ダイアログ.ユースケース.起動する.起動する
 */
@Stateless
public class StartScreenB {

	@Inject
	private ApproveWidgetRepository approveWidgetRepo;

	public StartScreenBResult startScreenB() {
		// 指定するウィジェットの設定を取得する
		// Input :標準ウィジェット種別＝申請状況

		Optional<StandardWidget> standardWigetOpt = this.approveWidgetRepo
				.findByWidgetTypeAndCompanyId(StandardWidgetType.APPLICATION_STATUS, AppContexts.user().companyId());
		
		if (!standardWigetOpt.isPresent()) {
			List<ApplicationStatusDetailedSettingDto> applicationStatusDetailedSettings = new ArrayList<>();
			for (ApplicationStatusWidgetItem value : ApplicationStatusWidgetItem.values()) {
				applicationStatusDetailedSettings.add(new ApplicationStatusDetailedSettingDto(NotUseAtr.USE.value, value.value));
			}
			return new StartScreenBResult(TextResource.localize("KTG005_15"), applicationStatusDetailedSettings);
		}

		List<ApplicationStatusDetailedSettingDto> appSettings = standardWigetOpt.get().getAppStatusDetailedSettingList()
				.stream().map(x -> new ApplicationStatusDetailedSettingDto(x.getDisplayType().value, x.getItem().value))
				.collect(Collectors.toList());

		return new StartScreenBResult("", appSettings);
	}
}
