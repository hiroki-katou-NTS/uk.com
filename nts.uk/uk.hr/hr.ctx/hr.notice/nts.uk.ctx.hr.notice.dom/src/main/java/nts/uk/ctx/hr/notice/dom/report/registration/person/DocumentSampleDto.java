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
	public Integer  reportLayoutID; // 個別届出種類ID
	public Integer docID; //書類ID
	public String docName; //書類名
	public Integer dispOrder; //表示順
	public Integer requiredDoc; //必須書類
	public String docRemarks; //備考
	public String sampleFileId; //サンプルファイルID
	public String sampleFileName; //サンプルファイル名
	
	public Integer reportID; //届出ID
	public String fileId; //ファイルID
	public String fileName; //ファイル名
	public Integer fileSize; //ファイルサイズ    đơn vị byte
}
