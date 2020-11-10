package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.OverStateOutput;

@AllArgsConstructor
@NoArgsConstructor
public class OverStateOutputDto {
	// 事前申請なし
	public Boolean isExistApp;
	// 事前超過
	public OutDateApplicationDto advanceExcess;
	// 実績状態
	public Integer achivementStatus;
	// 実績超過
	public OutDateApplicationDto achivementExcess;
	
	
	public static OverStateOutputDto fromDomain(OverStateOutput overStateOutput) {
		return new OverStateOutputDto(
				overStateOutput.getIsExistApp(),
				OutDateApplicationDto.fromDomain(overStateOutput.getAdvanceExcess()),
				overStateOutput.getAchivementStatus().value,
				OutDateApplicationDto.fromDomain(overStateOutput.getAchivementExcess()));
	}
}
