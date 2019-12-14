package nts.uk.ctx.hr.notice.infra.entity.report.registration.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.hr.notice.dom.report.registration.person.AttachmentPersonReportFile;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "JHNDT_RPT_ATC_FILE")
public class JhndtReportAtcFile extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public JhndtReportAtcFilePK pk;
	
	@Column(name = "RPTID")
	int reportID; //届出ID
	
	@Column(name = "DOC_ID")
	int docID; //書類ID
	
	@Column(name = "DOC_NAME")
	String docName; //書類名
	
	@Column(name = "FILE_NAME")
	String fileName; //ファイル名
	
	@Column(name = "FILE_ATTACHED")
	int fileAttached; //ファイル添付済     0:未添付、1:添付済み
	
	@Column(name = "FILE_STORAGE_DATE")
	GeneralDateTime fileStorageDate; //ファイル格納日時
	
	@Column(name = "MIME_TYPE")
	String mimeType; //MIMEタイプ
	
	@Column(name = "FILE_TYPE_NAME")
	String fileTypeName; //ファイル種別名
	
	@Column(name = "FILE_SIZE")
	int fileSize; //ファイルサイズ    đơn vị byte
	
	@Column(name = "DEL_FLG")
	boolean delFlg; //削除済     0:未削除、1:削除済
	
	@Column(name = "SAMPLE_FILE_ID")
	String sampleFileID; //サンプルファイルID
	
	@Column(name = "SAMPLE_FILE_NAME")
	String sampleFileName; //サンプルファイル名
	
	
	@Override
	public Object getKey() {
		return pk;
	}

	public AttachmentPersonReportFile toDomain() {
		return AttachmentPersonReportFile.createFromJavaType(
				this.pk.cid ,
				this.reportID ,
				this.docID ,
				this.docName ,
				this.pk.fileId ,
				this.fileName, 
				this.fileAttached == 1 ? true : false, 
				this.fileStorageDate, 
				this.mimeType ,
				this.fileTypeName ,
				this.fileSize ,
				this.delFlg ,
				this.sampleFileID ,
				this.sampleFileName );
	}
}
