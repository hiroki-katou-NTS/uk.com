package nts.uk.ctx.pereg.app.command.deleteemployee;

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
