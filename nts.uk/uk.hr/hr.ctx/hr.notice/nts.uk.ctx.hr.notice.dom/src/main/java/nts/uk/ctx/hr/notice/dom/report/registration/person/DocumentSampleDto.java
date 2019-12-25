/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentSampleDto {

	private String cid; //会社ID
	private int  reportLayoutID; // 個別届出種類ID
	private int docID; //書類ID
	private String docName; //書類名
	private int dispOrder; //表示順
	private boolean requiredDoc; //必須書類
	private String docRemarks; //備考
	private int sampleFileId; //サンプルファイルID
	private String sampleFileName; //サンプルファイル名
}
