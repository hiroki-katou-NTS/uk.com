package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

@Getter
@Setter
@NoArgsConstructor
public class OutputCreateDailyResult {
	private ProcessState processState;
	
	private List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();

	public OutputCreateDailyResult(ProcessState processState, List<ErrorMessageInfo> listErrorMessageInfo) {
		super();
		this.processState = processState;
		this.listErrorMessageInfo = listErrorMessageInfo;
	}
	
	
}
