package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkTime;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforWorkType;
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
	private AppDispInfoStartupDto appDispInfoStartup;
//	直行直帰申請の反映
	private GoBackReflectDto goBackReflect;
//	勤務種類リスト
	private List<WorkTypeDto> lstWorkType;
//	直行直帰申請
	private GoBackAplicationDto goBackApplication;
	
	public static InforGoBackCommonDirectDto convertDto(InforGoBackCommonDirectOutput value) {
		return new InforGoBackCommonDirectDto(
				value.getWorkType(),
				value.getWorkTime(),
				AppDispInfoStartupDto.fromDomain(value.getAppDispInfoStartup()),
				GoBackReflectDto.fromDomain(value.getGoBackReflect()),
				value.getLstWorkType().stream().map(item -> WorkTypeDto.fromDomain(item)).collect(Collectors.toList()),
				value.getGoBackDirectly().isPresent() ? GoBackAplicationDto.convertDto(value.getGoBackDirectly().get()) : null);
	}
	
}
