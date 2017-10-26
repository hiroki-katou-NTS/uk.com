package nts.uk.ctx.bs.employee.app.command.empfilemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCtgDocFileDocumentFileCommand {

	/** The file id. */
	private String fileId;
	
	/** The personInfoCategoryId new   */
	private String personInfoCategoryIdNew;
	
}
