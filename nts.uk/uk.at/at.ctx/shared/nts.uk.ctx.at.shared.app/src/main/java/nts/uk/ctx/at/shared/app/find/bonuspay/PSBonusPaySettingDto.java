package nts.uk.ctx.at.shared.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class PSBonusPaySettingDto {
	public String employeeId;
	public String bonusPaySettingCode;
}
