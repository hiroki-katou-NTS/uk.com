package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@AllArgsConstructor
@NoArgsConstructor
public class InsertCommand {
	
	public String companyId;
	
	public Boolean mode;
	
	public AppOverTimeInsertCommand appOverTimeInsert;
	
	public AppOverTimeUpdateCommand appOverTimeUpdate;
	
	public Boolean isMailServer;
	
	public AppDispInfoStartupDto appDispInfoStartupOutput;
}
