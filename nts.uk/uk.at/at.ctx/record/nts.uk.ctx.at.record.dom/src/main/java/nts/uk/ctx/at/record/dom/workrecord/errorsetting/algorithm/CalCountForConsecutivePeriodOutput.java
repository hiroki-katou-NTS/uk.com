package nts.uk.ctx.at.record.dom.workrecord.errorsetting.algorithm;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.errorsetting.ContinuousCount;

@Data
@AllArgsConstructor
public class CalCountForConsecutivePeriodOutput {
	private Optional<ContinuousCount> optContinuousCount; 
	private int count;
}
