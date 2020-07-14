package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InforGoBackCommonDirectOutput_Old {
//	勤務種類初期選択
	private InforWorkType workType;
//	就業時間帯初期選択
	private InforWorkTime workTime;
//	申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartup;
//	直行直帰申請共通設定
	private GoBackDirectlyCommonSetting gobackDirectCommon;
//	勤務種類リスト
	private List<WorkType> lstWorkType;
//	直行直帰申請
	private Optional<GoBackDirectly_Old> goBackDirectly;
}
