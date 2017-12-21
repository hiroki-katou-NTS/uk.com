package nts.uk.ctx.at.record.app.command.bonuspay;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class BPSettingUpdateCommand {
	public String code;
	public String name;
	public List<BPTimesheetUpdateCommand> lstBonusPayTimesheet;
	public List<SpecBPTimesheetUpdateCommand> lstSpecBonusPayTimesheet;
}
