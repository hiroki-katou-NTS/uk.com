package nts.uk.ctx.at.request.app.find.application.overtime;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeInsertCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.AppOverTimeUpdateCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;

@AllArgsConstructor
@NoArgsConstructor
public class ParamCalculateMobile {
	
	public String companyId;
	
	public DisplayInfoOverTimeCommand displayInfoOverTime;
	
	public AppOverTimeInsertCommand appOverTimeInsert;
	
	public AppOverTimeUpdateCommand appOverTimeUpdate;
	
	public Boolean mode;
	
	public String employeeId;
	
	public String dateOp;
}
