package nts.uk.ctx.pereg.app.command.filemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmpAvaOrMapCommand {
	/**employee id*/
	private String employeeId;
	/**file id*/
	private String fileId;
	/**file type*/
	private int fileType;
	
	/**file id*/
	private String fileIdnew;
	private String fileName;
	private boolean isAvatar;
	private String categoryName;
	private String itemName;
	// dùng cho lưu log trong trường hợp update
	private String fileIdOld;
	private String fileNameOld;
	
	public EmpAvaOrMapCommand(){}
}

