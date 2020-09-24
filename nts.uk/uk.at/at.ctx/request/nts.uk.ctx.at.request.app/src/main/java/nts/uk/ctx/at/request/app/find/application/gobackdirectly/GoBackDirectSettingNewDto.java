package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

@Data
public class GoBackDirectSettingNewDto {
//	勤務種類初期選択
	private String workTypeCode;
//	就業時間帯初期選択
	private String workTimeCode;
//  申請表示情報
	private AppDispInfoStartupDto appDispInfoStartupOutput; 
//	直行直帰申請共通設定
	private GoBackDirectlyCommonSetting backDirectCommonSetting;
//	勤務種類リスト
	private List<AppEmployWorkType> appSetting;
//	直行直帰申請
	private GoBackDirectly_Old goBackDirect;
}
