package nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
@AllArgsConstructor
@Value
public class DailyWorkTypeListImport {
	
	private String employeeId;
	private List<NumberOfWorkTypeUsedImport> numberOfWorkTypeUsedExports;
}
