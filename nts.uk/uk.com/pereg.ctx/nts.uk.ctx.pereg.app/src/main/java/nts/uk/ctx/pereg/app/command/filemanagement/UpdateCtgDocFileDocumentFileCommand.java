package nts.uk.ctx.pereg.app.command.filemanagement;

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
