package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentFile {
	
	/**
	 * 電子書類ファイル
	 */

	/** The UploadOrder */
	private UploadOrder uploadOrder;

	/** The FileID */
	private String fileId;

	/** The PersonalInformationCtgID */
	private String personInfoCategoryId;

	public static DocumentFile createFromJavaType(int uploadOrder, String fileId, String personInfoCategoryId) {
		return new DocumentFile(new UploadOrder(uploadOrder), fileId, personInfoCategoryId);
	}

}
