package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.InsufficientFlexHolidayMnt;

/**
 * The class add insufficient flex holiday command
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class AddInsufficientFlexHolidayMntCommand {
	/** 補填可能時間 */
	public double attendanceTime;
	
	public InsufficientFlexHolidayMnt toDomain(String companyId) {
		return InsufficientFlexHolidayMnt.createFromJavaType(companyId, attendanceTime); 
	}
}
