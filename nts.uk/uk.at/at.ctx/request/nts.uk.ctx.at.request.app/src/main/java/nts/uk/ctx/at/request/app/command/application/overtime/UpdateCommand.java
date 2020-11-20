package nts.uk.ctx.at.request.app.command.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommand {
	
	public String companyId;
	
	public AppOverTimeCommand appOverTime;
}
