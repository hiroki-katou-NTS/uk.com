package nts.uk.ctx.at.schedule.pub.shift.specificdayset;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class BasicWorkSettingExport {
	private String worktypeCode;
	
	/** The sift code. */
	private String workingCode;
	
	/** The work day division. */
	private int workdayDivision;
}
