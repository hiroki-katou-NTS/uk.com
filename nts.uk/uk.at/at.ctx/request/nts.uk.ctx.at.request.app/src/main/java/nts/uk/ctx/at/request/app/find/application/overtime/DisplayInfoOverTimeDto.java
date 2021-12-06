package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.*;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.overtime.dto.AppOverTimeDto;
import nts.uk.ctx.at.request.dom.application.overtime.service.DisplayInfoOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkInfo;

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
	// 計算結果
	public CalculationResultDto calculationResultOp;
	// 申請日に関係する情報
	public InfoWithDateApplicationDto infoWithDateApplicationOp;
	// 計算済フラグ
	public Integer calculatedFlag;
	
	public WorkInfo workInfo;

	// 最新の複数回残業申請
	public AppOverTimeDto latestMultiOvertimeApp;
	
	public DisplayInfoOverTimeDto(
			List<WorkdayoffFrameDto> workdayoffFrames,
			CalculationResultDto calculationResultOp,
			Integer calculateFlag) {
		this.workdayoffFrames = workdayoffFrames;
		this.calculationResultOp = calculationResultOp;
		this.calculatedFlag = calculateFlag;
	}
	
	public DisplayInfoOverTimeDto(
			List<WorkdayoffFrameDto> workdayoffFrames,
			AppDispInfoStartupDto appDispInfoStartup,
			CalculationResultDto calculationResultOp,
			InfoWithDateApplicationDto infoWithDateApplicationOp,
			Integer calculateFlag) {
		this.workdayoffFrames = workdayoffFrames;
		this.appDispInfoStartup = appDispInfoStartup;
		this.calculationResultOp = calculationResultOp;
		this.infoWithDateApplicationOp = infoWithDateApplicationOp;
		this.calculatedFlag = calculateFlag;
	}
	
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
				CalculationResultDto.fromDomain(displayInfoOverTime.getCalculationResultOp().orElse(null)),
				InfoWithDateApplicationDto.fromDomain(displayInfoOverTime.getInfoWithDateApplicationOp().orElse(null)),
				displayInfoOverTime.getCalculatedFlag().value,
				displayInfoOverTime.getWorkInfo().orElse(null),
				displayInfoOverTime.getLatestMultipleOvertimeApp().map(AppOverTimeDto::fromDomain).orElse(null)
		);
	}
	public static DisplayInfoOverTimeDto fromDomainChangeDate(DisplayInfoOverTime displayInfoOverTime) {
		
		return new DisplayInfoOverTimeDto(
				displayInfoOverTime.getWorkdayoffFrames()
					.stream()
					.map(x -> WorkdayoffFrameDto.fromDomain(x))
					.collect(Collectors.toList()),
				AppDispInfoStartupDto.fromDomain(displayInfoOverTime.getAppDispInfoStartup()),
				CalculationResultDto.fromDomain(displayInfoOverTime.getCalculationResultOp().orElse(null)),
				InfoWithDateApplicationDto.fromDomain(displayInfoOverTime.getInfoWithDateApplicationOp().orElse(null)),
				displayInfoOverTime.getCalculatedFlag().value);
	}
	
	public static DisplayInfoOverTimeDto fromDomainCalculation(DisplayInfoOverTime displayInfoOverTime) {
		
		return new DisplayInfoOverTimeDto(
				displayInfoOverTime.getWorkdayoffFrames()
					.stream()
					.map(x -> WorkdayoffFrameDto.fromDomain(x))
					.collect(Collectors.toList()),
				CalculationResultDto.fromDomain(displayInfoOverTime.getCalculationResultOp().orElse(null)),
				displayInfoOverTime.getCalculatedFlag().value
				);
	}
}
