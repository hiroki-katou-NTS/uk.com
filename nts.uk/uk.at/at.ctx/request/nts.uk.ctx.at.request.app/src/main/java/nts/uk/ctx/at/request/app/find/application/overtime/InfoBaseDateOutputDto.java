package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.InfoBaseDateOutput;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;

@AllArgsConstructor
@NoArgsConstructor
public class InfoBaseDateOutputDto {
	// 勤務種類リスト
	public List<WorkTypeDto> worktypes;
	// 残業申請で利用する残業枠
	public QuotaOuputDto quotaOutput;
	
	public static InfoBaseDateOutputDto fromDomain(InfoBaseDateOutput infoBaseDateOutput) {
		
		return new InfoBaseDateOutputDto(
				infoBaseDateOutput
					.getWorktypes()
					.stream()
					.map(x -> WorkTypeDto.fromDomain(x))
					.collect(Collectors.toList()),
				QuotaOuputDto.fromDomain(infoBaseDateOutput.getQuotaOutput()));
	}
}
