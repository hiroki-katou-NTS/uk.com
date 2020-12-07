package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CalculationResultDto {
	// 計算フラグ
	public Integer flag;
	// 残業時間帯修正フラグ
	public Integer overTimeZoneFlag;
	// 事前申請・実績の超過状態
	public OverStateOutputDto overStateOutput;
	// 申請時間
	public List<ApplicationTimeDto> applicationTimes;
	
	public static CalculationResultDto fromDomain(CalculationResult calculationResult) {
		
		if (calculationResult == null) return null;
		return new CalculationResultDto(
				calculationResult.getFlag(),
				calculationResult.getOverTimeZoneFlag(),
				OverStateOutputDto.fromDomain(calculationResult.getOverStateOutput()),
				calculationResult.getApplicationTimes()
					.stream()
					.map(x -> ApplicationTimeDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
	

}
