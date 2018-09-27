package nts.uk.ctx.pereg.app.command.filemanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemoveDocumentFileCommand {
	
	/** The file id. */
	private String fileid;
	
	private String sid;
	
	private String itemName;
	
	private String fileName;
	
	private String categoryName;
	
	public RemoveDocumentFileCommand(){}
}
