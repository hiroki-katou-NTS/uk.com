package nts.uk.screen.at.ws.dailyperformance.correction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.task.AsyncTaskInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DataSessionDto;

@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExecMonthlyAggregateOutput {
	private AsyncTaskInfo asyncTaskInfo ;
	private DataSessionDto dataSessionDto;
}
