package nts.uk.ctx.at.shared.app.command;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPTimeItemSettingUpdateCommand {
	public String timeItemId;
	public int holidayCalSettingAtr;
	public int overtimeCalSettingAtr;
	public int worktimeCalSettingAtr;
}
