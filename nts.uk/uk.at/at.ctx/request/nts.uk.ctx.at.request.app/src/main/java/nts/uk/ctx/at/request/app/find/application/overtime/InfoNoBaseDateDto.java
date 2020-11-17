package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.holidaywork.dto.AgreeOverTimeDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetDto;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoNoBaseDate;

@AllArgsConstructor
@NoArgsConstructor
public class InfoNoBaseDateDto {
	
	// 残業休日出勤申請の反映
	public AppReflectOtHdWorkDto overTimeReflect;
	
	// 残業申請設定
	public OvertimeAppSetDto overTimeAppSet;
	
	// 申請用時間外労働時間パラメータ
	public AgreeOverTimeDto agreeOverTimeOutput;
	
	// 利用する乖離理由(DivergenceReasonInputMethod at record , so create new class #112406)
	public List<DivergenceReasonInputMethodDto> divergenceReasonInputMethod;
	
	// 乖離時間枠
	public List<DivergenceTimeRootDto> divergenceTimeRoot;
	
	public static InfoNoBaseDateDto fromDomain(InfoNoBaseDate infoNoBaseDate) {
		return new InfoNoBaseDateDto(
				AppReflectOtHdWorkDto.fromDomain(infoNoBaseDate.getOverTimeReflect()),
				OvertimeAppSetDto.fromDomain(infoNoBaseDate.getOverTimeAppSet()),
				AgreeOverTimeDto.fromDomain(infoNoBaseDate.getAgreeOverTimeOutput()),
				infoNoBaseDate.getDivergenceReasonInputMethod()
					.stream()
					.map(x -> DivergenceReasonInputMethodDto.fromDomain(x))
					.collect(Collectors.toList()),
				infoNoBaseDate.getDivergenceTimeRoot()
					.stream()
					.map(x -> DivergenceTimeRootDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
	
	
}
