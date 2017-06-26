package nts.uk.ctx.at.shared.app.find.bonuspay;

import java.math.BigDecimal;

/**
 * 9:59:33 AM Jun 6, 2017
 */

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

@AllArgsConstructor
@Value
public class BPUnitUseSettingDto {
	public String companyId;
	public int workplaceUseAtr;
	public int personalUseAtr;
	public int workingTimesheetUseAtr;
}
