package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;

/**
 * The class add insufficient flex holiday command
 * @author HoangNDH
 *
 */
@Data
@AllArgsConstructor
public class AddInsufficientFlexHolidayMntCommand {
	/** 補填可能時間 */
	public double supplementableDays;
	
	public InsufficientFlexHolidayMnt toDomain(String companyId) {
		return InsufficientFlexHolidayMnt.createFromJavaType(companyId, supplementableDays); 
	}
}
