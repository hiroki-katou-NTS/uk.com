package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpAvaOrMapCommand {
	/**employee id*/
	private String employeeId;
	/**file id*/
	private String fileId;
	/**file type*/
	private int fileType;
	
	/**file id*/
	private String fileIdnew;
	
	private boolean isAvatar;
	
	public EmpAvaOrMapCommand(){}
}

