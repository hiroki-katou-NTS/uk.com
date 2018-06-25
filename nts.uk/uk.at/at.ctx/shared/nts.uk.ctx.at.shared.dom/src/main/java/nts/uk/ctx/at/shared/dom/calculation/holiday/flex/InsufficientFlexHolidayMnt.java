package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;

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
	private AttendanceDaysMonth supplementableDays;
	
	public static InsufficientFlexHolidayMnt createFromJavaType(String companyId, double supplementableDays) {
		return new InsufficientFlexHolidayMnt(companyId, new AttendanceDaysMonth(supplementableDays));
	}
}
