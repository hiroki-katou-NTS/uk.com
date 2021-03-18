package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.overtime.CalculationResult;

@AllArgsConstructor
@NoArgsConstructor
public class CalculationResultCommand {
	// 事前申請・実績の超過状態
	public OverStateOutputCommand overStateOutput;
	// 申請時間
	public List<ApplicationTimeCommand> applicationTimes;
	
	public CalculationResult toDomain() {
		
		return new CalculationResult(
				// case change date at mobile
				!Optional.ofNullable(overStateOutput).isPresent() ? Optional.empty() : Optional.ofNullable(overStateOutput.toDomain()),
				CollectionUtil.isEmpty(applicationTimes) ?
						Collections.emptyList() : 
						applicationTimes.stream()
										.map(x -> x.toDomain())
										.collect(Collectors.toList()));
	}
}
