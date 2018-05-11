package nts.uk.ctx.sys.assist.infra.entity.deletedata.resultlog;

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
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.resultlog.ResultLogDeletion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SSPDT_RESULT_LOG_DELETION")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtResultLogDeletion extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtResultLogDeletionPK sspdtResultLogDeletionPK;

	/** The deletion Id. */
	/** データ削除処理ID */
	@Column(name = "DEL_ID")
	public String delId;

	/** The company Id. */
	/** 会社ID */
	@Column(name = "CID")
	public String companyID;

	/** The log time. */
	/** ログ登録日時 */
	@Column(name = "LOG_TIME")
	@Convert(converter = GeneralDateToDBConverter.class)
	@Temporal(TemporalType.TIMESTAMP)
	public GeneralDateTime logTime;

	/** The processing content. */
	// 処理内容
	@Column(name = "PROCESSING_CONTENT")
	public String processingContent;

	/** The error content. */
	// エラー内容
	@Column(name = "ERROR_CONTENT")
	public String errorContent;

	/** The error employee id. */
	// エラー社員
	@Column(name = "ERROR_EMPLOYEE_ID")
	public String errorEmployeeId;

	/** The error date. */
	// エラー日付
	@Column(name = "ERROR_DATE")
	@Convert(converter = GeneralDateToDBConverter.class)
	@Temporal(TemporalType.DATE)
	public GeneralDate errorDate;

	@Override
	protected Object getKey() {
		return sspdtResultLogDeletionPK;
	}

	public ResultLogDeletion toDomain() {
		return ResultLogDeletion.createFromJavatype(this.sspdtResultLogDeletionPK.seqId, this.delId, this.companyID, this.logTime,
				this.processingContent, this.errorContent, this.errorEmployeeId, this.errorDate);
	}

	public static SspdtResultLogDeletion toEntity(ResultLogDeletion resultLog) {
		return new SspdtResultLogDeletion(new SspdtResultLogDeletionPK(resultLog.getSeqId()), resultLog.getDelId(), 
				resultLog.getCompanyId(), resultLog.getLogTime(), resultLog.getProcessingContent().v(), resultLog.getErrorContent().v(),
				resultLog.getErrorEmployeeId(), resultLog.getErrorDate());
	}
}
