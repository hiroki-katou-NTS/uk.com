package nts.uk.ctx.pereg.app.command.filemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddEmpDocumentFileCommand {

	/** The employment Id. */
	private String pid;
	
	/** The file id. */
	private String fileid;
	
	/** The personInfoCategoryId  */
	private String personInfoCtgId;
	
	/** The upload order*/
	private int uploadOrder;
	
	private String sid;
	
	private String itemName;
	
	private String fileName;
	
	private String categoryName;
}
