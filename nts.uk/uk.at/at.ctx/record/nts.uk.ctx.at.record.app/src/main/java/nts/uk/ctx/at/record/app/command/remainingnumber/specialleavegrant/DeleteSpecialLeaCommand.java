package nts.uk.ctx.at.record.app.command.remainingnumber.specialleavegrant;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeleteSpecialLeaCommand {
	
	private int specialCode;
	
	private String employeeId;
	
	private GeneralDate grantDate;
}
