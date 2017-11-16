package nts.uk.ctx.pereg.app.command.addemployee;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.command.PeregInputContainer;

@Data
public class AddEmployeeCommand {

	private String employeeCopyId;
	private String InitSettingId;
	private String employeeName;
	private String employeeCode;
	private GeneralDate hireDate;
	private String cardNo;
	private String avatarId;
	private int createType;
	
	private PeregInputContainer inputContainer;

}
