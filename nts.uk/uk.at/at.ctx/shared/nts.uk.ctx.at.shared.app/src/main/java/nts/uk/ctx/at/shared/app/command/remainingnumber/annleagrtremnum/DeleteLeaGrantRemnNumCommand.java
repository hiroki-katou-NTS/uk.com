package nts.uk.ctx.at.shared.app.command.remainingnumber.annleagrtremnum;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeleteLeaGrantRemnNumCommand {
	
	private String annLeavID;
	
	private String employeeId;
	
	private GeneralDate grantDate;
}
