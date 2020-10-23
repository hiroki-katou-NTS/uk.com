package nts.uk.screen.at.app.find.ktg.ktg005.b;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.screen.at.app.find.ktg.ktg005.a.ApplicationStatusDetailedSettingDto;
import nts.uk.shr.com.context.AppContexts;

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
				.findByWidgetType(StandardWidgetType.APPLICATION_STATUS.value, AppContexts.user().companyId());

		List<ApplicationStatusDetailedSettingDto> appSettings = standardWigetOpt.get().getAppStatusDetailedSettingList()
				.stream().map(x -> new ApplicationStatusDetailedSettingDto(x.getDisplayType().value, x.getItem().value))
				.collect(Collectors.toList());

		return new StartScreenBResult(standardWigetOpt.get().getName().v(), appSettings);
	}
}
