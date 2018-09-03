package nts.uk.ctx.pereg.app.command.deleteemployee;

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
