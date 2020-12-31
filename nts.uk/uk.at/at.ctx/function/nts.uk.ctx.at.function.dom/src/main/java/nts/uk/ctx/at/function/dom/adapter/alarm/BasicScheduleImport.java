package nts.uk.ctx.at.function.dom.adapter.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
public class BasicScheduleImport {

	private String employeeId;
	
	private GeneralDate date;
	
	private String workTypeCode;
	
	private String workTimeCode;
	
//	private List<WorkScheduleTimeZoneExport> workScheduleTimeZones;
}
