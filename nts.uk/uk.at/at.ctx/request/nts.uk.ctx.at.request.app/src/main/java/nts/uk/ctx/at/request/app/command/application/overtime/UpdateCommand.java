package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommand {
	
	public String companyId;
	
	public AppOverTimeUpdateCommand appOverTime;
	
	public AppDispInfoStartupDto appDispInfoStartupDto;
}
