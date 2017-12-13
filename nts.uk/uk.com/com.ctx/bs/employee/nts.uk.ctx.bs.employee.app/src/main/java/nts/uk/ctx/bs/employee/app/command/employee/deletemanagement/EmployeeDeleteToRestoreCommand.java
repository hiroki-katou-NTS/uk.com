package nts.uk.ctx.bs.employee.app.command.employee.deletemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Instantiates a new employee remove command.
 */

@Data
@AllArgsConstructor
public class EmployeeDeleteToRestoreCommand {
	
	/** The sid. */
	private String id;
	
	private String code;
	
	private String name;
	
}
