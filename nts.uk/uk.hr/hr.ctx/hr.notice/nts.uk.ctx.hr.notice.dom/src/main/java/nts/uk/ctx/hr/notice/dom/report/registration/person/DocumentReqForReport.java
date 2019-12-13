/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 * Domain  届出必要書類
 * 
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentReqForReport {

	String cid; //会社ID
	int  reportLayoutID; // 個別届出種類ID
	int docID; //書類ID
	String docName; //書類名
	int dispOrder; //表示順
	boolean requiredDoc; //必須書類
	
	public static DocumentReqForReport createFromJavaType(String cid, int reportLayoutID, int docID, String docName, int dispOrder,
			boolean requiredDoc){
		return new DocumentReqForReport(cid, reportLayoutID, docID, docName, dispOrder, requiredDoc);
	}
}
