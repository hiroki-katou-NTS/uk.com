package nts.uk.ctx.at.shared.app.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPSettingAddCommand {
	public String code;
	public String name;
	public List<BPTimesheetAddCommand> lstBonusPayTimesheet;
	public List<SpecBPTimesheetAddCommand> lstSpecBonusPayTimesheet;
}
