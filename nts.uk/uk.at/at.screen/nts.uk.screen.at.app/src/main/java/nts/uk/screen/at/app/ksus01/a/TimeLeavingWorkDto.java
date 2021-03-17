package nts.uk.screen.at.app.ksus01.a;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.TimeActualStampDto;

@AllArgsConstructor
@NoArgsConstructor
public class TimeLeavingWorkDto {
	
	private int workNo;
	
	private TimeActualStampDto attendanceStamp;
	
	private TimeActualStampDto leaveStamp;
	
	private boolean canceledLate = false;
	
	private boolean CanceledEarlyLeave = false;
	
	public static TimeLeavingWorkDto toDto() {
		return new TimeLeavingWorkDto();
	}
}
