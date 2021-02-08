package nts.uk.ctx.at.request.app.find.application.overtime.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.CheckBeforeOutput;

@AllArgsConstructor
@NoArgsConstructor
public class CheckBeforeOutputDto {
	// 残業申請
	public AppOverTimeDto appOverTime;
	//確認メッセージリスト
	public List<ConfirmMsgOutput> confirmMsgOutputs;
	
	public static CheckBeforeOutputDto fromDomain(CheckBeforeOutput checkBeforeOutput) {
		return new CheckBeforeOutputDto(
				checkBeforeOutput.getAppOverTime() == null ? null : AppOverTimeDto.fromDomain(checkBeforeOutput.getAppOverTime()),
				checkBeforeOutput.getConfirmMsgOutputs());
	}
}
