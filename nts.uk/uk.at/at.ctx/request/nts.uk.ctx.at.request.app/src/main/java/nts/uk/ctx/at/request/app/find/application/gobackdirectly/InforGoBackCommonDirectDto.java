package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.setting.request.gobackdirectlycommon.GoBackDirectlyCommonSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
@Data
@AllArgsConstructor
@NoArgsConstructor
//直行直帰申請起動時の表示情報
public class InforGoBackCommonDirectDto {
//	勤務種類初期選択
	private InforWorkType workType;
//	就業時間帯初期選択
	private InforWorkTime workTime;
//	申請表示情報
	private AppDispInfoStartupDto appDispInfoStartupDto;
//	直行直帰申請共通設定
	private GoBackDirectlyCommonSettingDto gobackDirectCommonDto;
//	勤務種類リスト
	private List<WorkTypeDto> lstWorkType;
//	直行直帰申請
	private GoBackDirectlyDto goBackDirectly;
	
	public static InforGoBackCommonDirectDto convertDto(InforGoBackCommonDirectOutput output) {
		return new InforGoBackCommonDirectDto(
					output.getWorkType(),
					output.getWorkTime(),
					AppDispInfoStartupDto.fromDomain(output.getAppDispInfoStartup()),
					GoBackDirectlyCommonSettingDto.convertToDto(output.getGobackDirectCommon()),
					output.getLstWorkType().stream().map(item -> WorkTypeDto.fromDomain(item)).collect(Collectors.toList()),
					output.getGoBackDirectly().isPresent() ? GoBackDirectlyDto.convertToDto(output.getGoBackDirectly().get()): null
				)
		;
	}
	
}
