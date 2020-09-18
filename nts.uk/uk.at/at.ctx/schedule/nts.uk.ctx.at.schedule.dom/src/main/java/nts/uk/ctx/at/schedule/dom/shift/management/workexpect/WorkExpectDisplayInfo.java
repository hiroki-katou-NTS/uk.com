package nts.uk.ctx.at.schedule.dom.shift.management.workexpect;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

@Value
public class WorkExpectDisplayInfo {
	
	private AssignmentMethod method;
	
	private List<String> nameList;
	
	private List<TimeSpanForCalc> timeZoneList;

}
