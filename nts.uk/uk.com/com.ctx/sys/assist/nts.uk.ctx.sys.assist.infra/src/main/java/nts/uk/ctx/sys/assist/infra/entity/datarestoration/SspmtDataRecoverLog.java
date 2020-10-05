package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryLog;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ復旧の結果ログ
 */
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_DATA_RECOVER_LOG")
public class SspmtDataRecoverLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public SspmtDataRecoverLogPk dataRecoverLogPk;

	/**
	 * 処理内容
	 */
	@Basic(optional = true)
	@Column(name = "PROCESSING_CONTENT")
	public String processingContent;

	/**
	 * 対象者
	 */
	@Basic(optional = true)
	@Column(name = "TARGET")
	public String target;

	/**
	 * 対象日
	 */
	@Basic(optional = true)
	@Column(name = "TARGET_DATE")
	public GeneralDate targetDate;

	/**
	 * エラー内容
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_CONTENT")
	public String errorContent;

	/**
	 * 実行SQL
	 */
	@Basic(optional = true)
	@Column(name = "CONTENT_SQL")
	public String contentSql;
	
	@ManyToOne
	@JoinColumn(name="RECOVERY_PROCESS_ID", referencedColumnName="DATA_RECOVERY_PROCESS_ID", insertable = false, updatable = false)		
	public SspmtDataRecoverResult dataRecoverResult;

	@Override
	protected Object getKey() {
		return dataRecoverLogPk;
	}

	public DataRecoveryLog toDomain() {
		return new DataRecoveryLog(this.dataRecoverLogPk.recoveryProcessId, this.target, 
				this.errorContent, this.targetDate, this.dataRecoverLogPk.logSequenceNumber, 
				this.processingContent, this.contentSql);
	}
	
	public static SspmtDataRecoverLog toEntity(DataRecoveryLog domain) {
		return new SspmtDataRecoverLog
			(
				new SspmtDataRecoverLogPk(domain.getRecoveryProcessId(),domain.getLogSequenceNumber()), 
				domain.getProcessingContent().v(),
				domain.getTarget(),
				domain.getTargetDate(),
				domain.getErrorContent().v(),
				domain.getContentSql().v()
			);
	}

	public SspmtDataRecoverLog(SspmtDataRecoverLogPk sspdtResultLogDeletionPK, String processingContent, String target,
			GeneralDate targetDate, String errorContent, String contentSql) {
		this.dataRecoverLogPk = sspdtResultLogDeletionPK;
		this.processingContent = processingContent;
		this.target = target;
		this.targetDate = targetDate;
		this.errorContent = errorContent;
		this.contentSql = contentSql;
	}
}
