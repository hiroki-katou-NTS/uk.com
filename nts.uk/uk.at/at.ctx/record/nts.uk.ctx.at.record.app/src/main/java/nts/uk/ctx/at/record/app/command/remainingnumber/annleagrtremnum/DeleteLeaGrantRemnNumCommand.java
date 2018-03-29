package nts.uk.ctx.at.record.app.command.remainingnumber.annleagrtremnum;

import lombok.Getter;
import nts.arc.time.GeneralDate;

@Getter
public class DeleteLeaGrantRemnNumCommand {
	
	private String annLeavId;
	
	private String employeeId;
	
	private GeneralDate grantDate;
}
