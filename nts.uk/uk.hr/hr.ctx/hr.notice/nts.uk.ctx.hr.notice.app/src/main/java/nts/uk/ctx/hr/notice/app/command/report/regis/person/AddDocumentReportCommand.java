package nts.uk.ctx.hr.notice.app.command.report.regis.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;


@Data
@AllArgsConstructor
public class AddDocumentReportCommand {
	 String cid; //会社ID
	 int docID; //書類ID
	 String docName; //書類名
	 String fileId; //ファイルID
	 String fileName; //ファイル名
	 int fileAttached; //ファイル添付済     0:未添付、1:添付済み	
	 GeneralDate fileStorageDate; //ファイル格納日時
	 String mimeType; //MIMEタイプ
	 String fileTypeName; //ファイル種別名
	 int fileSize; //ファイルサイズ    đơn vị byte
	 int delFlg; //削除済     0:未削除、1:削除済
	 String sampleFileID; //サンプルファイルID
	 String sampleFileName;
	 String reportID; //届出ID
	 String layoutReportId;
	 String missingDocName;
	 SaveReportInputContainer dataLayout;
	 boolean fromJhn002;
}
