package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * Domain insufficient flex holiday time management
 * @author HoangNDH
 *
 */
@Getter
@AllArgsConstructor
public class InsufficientFlexHolidayMnt {
	/** 会社ID */
	private String companyId;
	
	/** 補填可能時間 */
	private AttendanceTime attendanceTime;
	
	public static InsufficientFlexHolidayMnt createFromJavaType(String companyId, int attendanceTime) {
		return new InsufficientFlexHolidayMnt(companyId, new AttendanceTime(attendanceTime));
	}
}
