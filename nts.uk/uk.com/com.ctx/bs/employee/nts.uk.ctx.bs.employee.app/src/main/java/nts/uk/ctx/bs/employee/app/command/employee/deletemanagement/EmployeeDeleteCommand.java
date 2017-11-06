package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Instantiates a new employee remove command.
 */

@Data
@AllArgsConstructor
public class EmployeeDeleteCommand {
	
	/** The employment Id. */
	private String sId;
	
	/** The reason delete employee. */
	private String reason;
	
}
