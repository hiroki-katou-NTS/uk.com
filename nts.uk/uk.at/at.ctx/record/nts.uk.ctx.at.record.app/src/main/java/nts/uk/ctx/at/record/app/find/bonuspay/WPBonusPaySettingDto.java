package nts.uk.ctx.at.record.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class WPBonusPaySettingDto {
	public String workplaceId;
	public String bonusPaySettingCode;
}
