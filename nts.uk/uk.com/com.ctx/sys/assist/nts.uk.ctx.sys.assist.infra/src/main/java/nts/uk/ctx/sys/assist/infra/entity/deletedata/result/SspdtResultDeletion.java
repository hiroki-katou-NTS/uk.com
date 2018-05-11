package nts.uk.ctx.sys.assist.infra.entity.deletedata.result;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.result.ResultDeletion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SSPDT_RESULT_DELETION")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtResultDeletion extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtResultDeletionPK sspdtResultDeletionPK;
	
	/** The company Id. */
	/** 会社ID */
	@Column(name = "CID")
	public String companyID;
	
	/** The deletion name. */
	/** 削除名称 */
	@Column(name = "DEL_NAME")
	public String delName;
	
	/** The deletion type. */
	/** 削除形態 */
	@Column(name = "DEL_TYPE")
	public int delType;
	
	/** The deleted file flag. */
	/** 削除済みファイル */
	@Column(name = "IS_DELETED_FILES_FLG")
	public int isDeletedFilesFlg;
	
	/** The deletion code. */
	/** 削除セットコード  */
	@Column(name = "DEL_CODE")
	public String delCode;
	
	/** The number employees. */
	/** 対象人数 */
	@Column(name = "NUMBER_EMPLOYEES")
	public int numberEmployees;
	
	/** The system type. */
	/** システム種類  */
	@Column(name = "SYSTEM_TYPE")
	public int systemType;
	
	/** The employee Id. */
	/** 実行者 */
	@Column(name = "SID")
	public String sId;
	
	/** The status. */
	/** 結果状態 */
	@Column(name = "STATUS")
	public int status;
	
	/** The start date time deletion. */
	/** 削除開始日時 */
	@Column(name = "START_DATE_TIME_DELETE")
	@Convert(converter = GeneralDateToDBConverter.class)
	@Temporal(TemporalType.TIMESTAMP)
	public GeneralDateTime startDateTimeDel;
	
	/** The end date time deletion. */
	/** 削除終了日時 */
	@Column(name = "END_DATE_TIME_DELETE")
	@Convert(converter = GeneralDateToDBConverter.class)
	@Temporal(TemporalType.TIMESTAMP)
	public GeneralDateTime endDateTimeDel;
	
	/** The file id. */
	/**  ファイルID */
	@Column(name = "FILE_ID")
	public String fileId;
	
	/** The file name. */
	/** 保存ファイル名 */
	@Column(name = "FILE_NAME")
	public String fileName;
	
	/** The file size. */
	/** ファイル容量 */
	@Column(name = "FILE_SIZE")
	public int fileSize;
	
	@Override
	protected Object getKey() {
		return sspdtResultDeletionPK;
	}

	public ResultDeletion toDomain() {
		boolean isDeletedFilesFlg = this.isDeletedFilesFlg == 1;
		return ResultDeletion.createFromJavatype(this.sspdtResultDeletionPK.delID, this.companyID, this.delName, 
				this.delType, isDeletedFilesFlg,
				this.delCode, this.numberEmployees, this.systemType, this.sId, this.status,
				this.startDateTimeDel, this.endDateTimeDel, this.fileId, this.fileName, this.fileSize);
	}

	public static SspdtResultDeletion toEntity(ResultDeletion result) {
		int isDeletedFilesFlg = result.isDeletedFilesFlg() ? 1 : 0;
		
		return new SspdtResultDeletion(new SspdtResultDeletionPK(result.getDelId()),
				result.getCompanyId(), result.getDelName().v(), result.getDelType().value, isDeletedFilesFlg,
				result.getDelCode().v(), result.getNumberEmployees(), result.getSystemType().value, 
				result.getSId(), result.getStatus().value, result.getStartDateTimeDel(), 
				result.getEndDateTimeDel(), result.getFileId(), result.getFileName().v(), result.getFileSize());
	}
}
