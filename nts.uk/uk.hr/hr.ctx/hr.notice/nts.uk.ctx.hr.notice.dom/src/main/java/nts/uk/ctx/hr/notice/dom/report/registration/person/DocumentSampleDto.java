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
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DocumentSampleDto {

	public String cid; //会社ID
	public int  reportLayoutID; // 個別届出種類ID
	public int docID; //書類ID
	public String docName; //書類名
	public int dispOrder; //表示順
	public int requiredDoc; //必須書類
	public String docRemarks; //備考
	public int sampleFileId; //サンプルファイルID
	public String sampleFileName; //サンプルファイル名
	
	private int reportID; //届出ID
	private String fileId; //ファイルID
	private String fileName; //ファイル名
	private int fileSize; //ファイルサイズ    đơn vị byte
}
