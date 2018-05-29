package nts.uk.ctx.at.record.pub.specifiedworktype;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyWorkTypeListExport {
	
	private String employeeId;
	
	private List<NumberOfWorkTypeUsedExport> numberOfWorkTypeUsedExports;
}
