package nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command;

import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
@NoArgsConstructor
@Setter
public class RegisterWhenChangeDateCommand {

	public GeneralDate appDateNew;
	
	public DisplayInforWhenStarting displayInforWhenStarting;
	
	public String appReason;
	
	public Integer appStandardReasonCD;
	
	public boolean checkFlag = false;
}
