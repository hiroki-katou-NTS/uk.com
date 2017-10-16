package nts.uk.ctx.bs.employee.app.command.employee;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Instantiates a new employee remove command.
 */

@Data
@AllArgsConstructor
public class EmployeeDeleteToRestoreCommand {
	
	/** The employment Id. */
	private String sId;
	
	private String code;
	
	/** The newCode, newName of Employee after RestoreData. */
	private String newCode;
	
	private String newName;
	
}
