package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 * Domain Object 書類
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

	private String cid; //会社ID
	private int docID; //書類ID
	private String docName; //書類名
	private String docRemarks; //備考
	private String sampleFileId; //サンプルファイルID
	private String sampleFileName; //サンプルファイル名

	public static Document createFromJavaType(String cid, int docID, String docName, String docRemarks, String sampleFileId, String sampleFileName){
		return new Document(cid, docID, docName, docRemarks, sampleFileId, sampleFileName);
	}
}
