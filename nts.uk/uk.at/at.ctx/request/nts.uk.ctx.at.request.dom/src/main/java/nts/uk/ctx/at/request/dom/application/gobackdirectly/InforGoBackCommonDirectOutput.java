package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.directgoback.GoBackReflect;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
@Data
@AllArgsConstructor
@NoArgsConstructor
//直行直帰申請起動時の表示情報
// Refactor 4
public class InforGoBackCommonDirectOutput {
//	勤務種類初期選択
	private String workType;
//	就業時間帯初期選択
	private String workTime;
//	申請表示情報
	private AppDispInfoStartupOutput appDispInfoStartup;
//	直行直帰申請の反映
	private GoBackReflect goBackReflect;
//	勤務種類リスト
	private List<WorkType> lstWorkType;
//	直行直帰申請
	private Optional<GoBackDirectly> goBackDirectly = Optional.ofNullable(null);
//	時間帯(使用区分付き)
	private List<TimezoneUse> timezones = Collections.emptyList();
}
