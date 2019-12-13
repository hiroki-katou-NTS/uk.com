package nts.uk.ctx.hr.notice.dom.report.registration.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;

/**
 * @author laitv
 * Domain : 人事届出ファイルの添付
 */
@Getter
@NoArgsConstructor
public class AttachmentPersonReportFile extends AggregateRoot{
	
	String cid; //会社ID
	int reportID; //届出ID
	int docID; //書類ID
	String docName; //書類名
	String fileId; //ファイルID
	String fileName; //ファイル名
	boolean fileAttached; //ファイル添付済     0:未添付、1:添付済み	
	GeneralDateTime fileStorageDate; //ファイル格納日時
	String mimeType; //MIMEタイプ
	String fileTypeName; //ファイル種別名
	int fileSize; //ファイルサイズ    đơn vị byte
	boolean delFlg; //削除済     0:未削除、1:削除済
	String sampleFileID; //サンプルファイルID
	String sampleFileName; //サンプルファイル名
	
	public AttachmentPersonReportFile(String cid, int reportID, int docID, String docName, String fileId,
			String fileName, boolean fileAttached, GeneralDateTime fileStorageDate, String mimeType,
			String fileTypeName, int fileSize, boolean delFlg, String sampleFileID, String sampleFileName) {
		super();
		this.cid = cid;
		this.reportID = reportID;
		this.docID = docID;
		this.docName = docName;
		this.fileId = fileId;
		this.fileName = fileName;
		this.fileAttached = fileAttached;
		this.fileStorageDate = fileStorageDate;
		this.mimeType = mimeType;
		this.fileTypeName = fileTypeName;
		this.fileSize = fileSize;
		this.delFlg = delFlg;
		this.sampleFileID = sampleFileID;
		this.sampleFileName = sampleFileName;
	}

	public static AttachmentPersonReportFile createFromJavaType(String cid, int reportID, int docID, String docName, String fileId,
			String fileName, boolean fileAttached, GeneralDateTime fileStorageDate, String mimeType,
			String fileTypeName, int fileSize, boolean delFlg, String sampleFileID, String sampleFileName){
		return new AttachmentPersonReportFile(cid, reportID, docID, docName, fileId, fileName, fileAttached,
				fileStorageDate, mimeType, fileTypeName, fileSize, delFlg, sampleFileID, sampleFileName);
	}
}
