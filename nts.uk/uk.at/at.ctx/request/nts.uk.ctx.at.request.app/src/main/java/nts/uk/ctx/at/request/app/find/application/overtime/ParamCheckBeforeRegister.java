package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeInsertCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;

@AllArgsConstructor
@NoArgsConstructor
public class ParamCheckBeforeRegister {
	
	public Boolean require;
	
	public String companyId;
	
	public DisplayInfoOverTimeCommand displayInfoOverTime;
	
	public AppOverTimeInsertCommand appOverTime;
	
}
