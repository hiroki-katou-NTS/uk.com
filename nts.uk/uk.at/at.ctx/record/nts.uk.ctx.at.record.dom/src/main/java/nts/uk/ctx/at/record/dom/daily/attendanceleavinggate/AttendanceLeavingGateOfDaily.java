package nts.uk.ctx.at.record.dom.daily.attendanceleavinggate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/** 日別実績の入退門 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLeavingGateOfDaily {

	/** 社員ID: 社員ID */
	private String employeeId;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** 入退門: 入退門 */
	private List<AttendanceLeavingGate> attendanceLeavingGates;
	
}
