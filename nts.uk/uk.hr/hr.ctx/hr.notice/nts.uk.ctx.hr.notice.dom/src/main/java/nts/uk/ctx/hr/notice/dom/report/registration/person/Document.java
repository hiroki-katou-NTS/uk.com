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

	String cid; //会社ID
	int docID; //書類ID
	String docName; //書類名
	String docRemarks; //備考
	int sampleFileId; //サンプルファイルID
	String sampleFileName; //サンプルファイル名

	public static Document createFromJavaType(String cid, int docID, String docName, String docRemarks, int sampleFileId, String sampleFileName){
		return new Document(cid, docID, docName, docRemarks, sampleFileId, sampleFileName);
	}
}
