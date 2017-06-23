package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemSettingAddCommand {
	public String companyId;
	public String timeItemId;
	public int holidayCalSettingAtr;
	public int overtimeCalSettingAtr;
	public int worktimeCalSettingAtr;

}
