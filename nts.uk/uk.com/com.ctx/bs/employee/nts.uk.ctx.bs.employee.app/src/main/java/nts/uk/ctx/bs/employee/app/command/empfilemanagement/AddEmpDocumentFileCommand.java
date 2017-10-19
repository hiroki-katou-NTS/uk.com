package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmpDocumentFileCommand {

	/** The employment Id. */
	private String sid;
	
	/** The reason delete employee. */
	private String fileid;
}
