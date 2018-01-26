package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BPSettingAddCommand {
	public String code;
	public String name;
	public List<BPTimesheetAddCommand> lstBonusPayTimesheet;
	public List<SpecBPTimesheetAddCommand> lstSpecBonusPayTimesheet;
}
