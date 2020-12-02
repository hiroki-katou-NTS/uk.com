package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeUpdateCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;

@AllArgsConstructor
@NoArgsConstructor
public class ParamCheckBeforeUpdate {
	
	public Boolean require;
	
	public String companyId;
	
	public DisplayInfoOverTimeCommand displayInfoOverTime;
	
	public AppOverTimeUpdateCommand appOverTime;
}
