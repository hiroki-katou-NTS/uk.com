package nts.uk.ctx.sys.assist.infra.entity.deletedata;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SSPDT_RESULT_LOG_DELETION")
@NoArgsConstructor
@AllArgsConstructor
public class SspdtResultLogDeletion extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspdtResultLogDeletionPK sspdtResultLogDeletionPK;

	/** The company Id. */
	/** 会社ID */
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyID;

	/** The log time. */
	/** ログ登録日時 */
	@Basic(optional = false)
	@Column(name = "LOG_TIME")
	public GeneralDateTime logTime;

	/** The processing content. */
	// 処理内容
	@Basic(optional = false)
	@Column(name = "PROCESSING_CONTENT")
	public String processingContent;

	/** The error content. */
	// エラー内容
	@Basic(optional = true)
	@Column(name = "ERROR_CONTENT")
	public String errorContent;

	/** The error employee id. */
	// エラー社員
	@Basic(optional = true)
	@Column(name = "ERROR_EMPLOYEE_ID")
	public String errorEmployeeId;

	/** The error date. */
	// エラー日付
	@Basic(optional = true)
	@Column(name = "ERROR_DATE")
	public GeneralDate errorDate;

	@Override
	protected Object getKey() {
		return sspdtResultLogDeletionPK;
	}

	public ResultLogDeletion toDomain() {
		return ResultLogDeletion.createFromJavatype(this.sspdtResultLogDeletionPK.seqId, this.sspdtResultLogDeletionPK.delId, this.companyID, this.logTime,
				this.processingContent, this.errorContent, this.errorEmployeeId, this.errorDate);
	}

	public static SspdtResultLogDeletion toEntity(ResultLogDeletion resultLog) {
		return new SspdtResultLogDeletion(new SspdtResultLogDeletionPK(resultLog.getDelId(), resultLog.getSeqId()), 
				resultLog.getCompanyId(), resultLog.getLogTime(), resultLog.getProcessingContent().v(), resultLog.getErrorContent().v(),
				resultLog.getErrorEmployeeId(), resultLog.getErrorDate());
	}
}
