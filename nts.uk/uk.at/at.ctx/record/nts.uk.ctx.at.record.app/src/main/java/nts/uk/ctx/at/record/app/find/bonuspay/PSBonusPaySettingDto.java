package nts.uk.ctx.at.record.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class PSBonusPaySettingDto {
	public String employeeId;
	public String bonusPaySettingCode;
}
