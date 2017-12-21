package nts.uk.ctx.at.shared.app.find.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemSettingDto {
	
	public String companyId;
	
	public int timeItemNo;
	
	public int holidayCalSettingAtr;
	
	public int overtimeCalSettingAtr;
	
	public int worktimeCalSettingAtr;

	public int timeItemTypeAtr;
}
