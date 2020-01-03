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
public class DocumentRequiredForReport {

	private String cid; //会社ID
	private int  reportLayoutID; // 個別届出種類ID
	private int docID; //書類ID
	private String docName; //書類名
	private int dispOrder; //表示順
	private boolean requiredDoc; //必須書類
	
	public static DocumentRequiredForReport createFromJavaType(String cid, int reportLayoutID, int docID, String docName, int dispOrder,
			boolean requiredDoc){
		return new DocumentRequiredForReport(cid, reportLayoutID, docID, docName, dispOrder, requiredDoc);
	}
}
