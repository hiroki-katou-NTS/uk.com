package nts.uk.ctx.at.shared.app.find.bonuspay;


/**
 * 9:59:33 AM Jun 6, 2017
 */

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPUnitUseSettingDto {
	public String companyId;
	public int workplaceUseAtr;
	public int personalUseAtr;
	public int workingTimesheetUseAtr;
}
