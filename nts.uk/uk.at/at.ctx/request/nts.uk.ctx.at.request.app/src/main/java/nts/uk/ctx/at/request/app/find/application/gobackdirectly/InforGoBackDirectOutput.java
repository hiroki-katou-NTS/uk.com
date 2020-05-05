package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;
import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;

@Data
//直行直帰申請起動時の表示情報
public class InforGoBackDirectOutput {
//	勤務種類初期選択
	private InforWorkType workType;
//	就業時間帯コード
	private InforWorkTime workTime;
//	申請表示情報
	private AppDispInfoStartupDto appDispInfoStartupOutput;
//	直行直帰申請共通設定
	private GoBackDirectlyCommonSetting gobackDirect;
//	勤務種類リスト
	private List<String> lstWorkType;
//	直行直帰申請
	private Optional<GoBackDirectly> goBackDirectly;
	 
}
