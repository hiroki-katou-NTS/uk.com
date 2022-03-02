package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;

@AllArgsConstructor
@NoArgsConstructor
public class ParamChangeDateMobile {
	
	public String companyId;

	public String employeeId;

	public String date;

	public int prePostAtr;
	
	public DisplayInfoOverTimeCommand displayInfoOverTime;
}
