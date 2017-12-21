package nts.uk.ctx.at.shared.app.command.bonuspay;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemSettingUpdateCommand {
	
	public int timeItemNo;
	
	public int holidayCalSettingAtr;
	
	public int overtimeCalSettingAtr;
	
	public int worktimeCalSettingAtr;
	
	public int timeItemTypeAtr;
}
