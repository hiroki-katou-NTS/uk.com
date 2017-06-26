package nts.uk.ctx.at.shared.app.command;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimesheetDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.SpecBPTimesheetDto;

@AllArgsConstructor
@Value
public class BPSettingUpdateCommand {
	public String companyId;
	public String code;
	public String name;
	public List<BPTimesheetUpdateCommand> lstBonusPayTimesheet;
	public List<SpecBPTimesheetUpdateCommand> lstSpecBonusPayTimesheet;
}
