package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;

@AllArgsConstructor
@NoArgsConstructor
public class DisplayInfoOverTimeDto {
	// 基準日に関する情報
	public InfoBaseDateOutputDto infoBaseDateOutput;
	// 基準日に関係しない情報
	public InfoNoBaseDateDto infoNoBaseDate;
	// 休出枠
	public List<WorkdayoffFrameDto> workdayoffFrames;
	// 残業申請区分
	public Integer overtimeAppAtr;
	// 申請表示情報
	public AppDispInfoStartupDto appDispInfoStartup;
	// 代行申請か
	public Boolean isProxy;
	// 計算結果
	public CalculationResultDto calculationResultOp;
	// 申請日に関係する情報
	public InfoWithDateApplicationDto infoWithDateApplicationOp;
	
	
	public static DisplayInfoOverTimeDto fromDomain(DisplayInfoOverTime displayInfoOverTime) {
		
		return new DisplayInfoOverTimeDto(
				InfoBaseDateOutputDto.fromDomain(displayInfoOverTime.getInfoBaseDateOutput()),
				InfoNoBaseDateDto.fromDomain(displayInfoOverTime.getInfoNoBaseDate()),
				displayInfoOverTime.getWorkdayoffFrames()
					.stream()
					.map(x -> WorkdayoffFrameDto.fromDomain(x))
					.collect(Collectors.toList()),
				displayInfoOverTime.getOvertimeAppAtr() == null ? null : displayInfoOverTime.getOvertimeAppAtr().value,
				AppDispInfoStartupDto.fromDomain(displayInfoOverTime.getAppDispInfoStartup()),
				displayInfoOverTime.getIsProxy(),
				CalculationResultDto.fromDomain(displayInfoOverTime.getCalculationResultOp().orElse(null)),
				InfoWithDateApplicationDto.fromDomain(displayInfoOverTime.getInfoWithDateApplicationOp().orElse(null)));
	}
}
