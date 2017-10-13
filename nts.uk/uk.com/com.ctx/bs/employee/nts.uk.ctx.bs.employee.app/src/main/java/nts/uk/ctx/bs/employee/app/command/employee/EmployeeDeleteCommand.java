package nts.uk.ctx.bs.employee.app.command.employee;

import lombok.Data;


/**
 * Instantiates a new employee remove command.
 */

@Data
public class EmployeeDeleteCommand {
	
	/** The employment Id. */
	private String employeeId;
	
	/** The reason delete employee. */
	private String reasonDelete;
	
}
