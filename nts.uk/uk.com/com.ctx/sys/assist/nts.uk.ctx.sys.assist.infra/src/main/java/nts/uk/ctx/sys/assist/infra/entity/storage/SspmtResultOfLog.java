package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.ResultLogSaving;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "SSPMT_RESULT_OF_LOG")
@NoArgsConstructor
@AllArgsConstructor
public class SspmtResultOfLog extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public SspmtResultOfLogPK sspmtResultOfLogPK;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * ログ登録日時
	 */
	@Basic(optional = false)
	@Column(name = "LOG_DATETIME")
	public GeneralDateTime logTime;


	/**
	 * 処理内容
	 */
	@Basic(optional = false)
	@Column(name = "LOG_CONTENT")
	public String logContent;

	/**
	 * エラー社員
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_SID")
	public String errorEmployeeId;

	/**
	 * エラー日付
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_DATETIME")
	public GeneralDate errorDate;

	/**
	 * エラー内容
	 */
	@Basic(optional = true)
	@Column(name = "ERROR_CONTENT")
	public String errorContent;
	
	@ManyToOne
	@JoinColumn(name="STORE_PROCESSING_ID", referencedColumnName="STORE_PROCESSING_ID", insertable = false, updatable = false)		
	public SspmtResultOfSaving resultOfSaving;

	@Override
	protected Object getKey() {
		return sspmtResultOfLogPK;
	}

	public ResultLogSaving toDomain() {
		return ResultLogSaving.createFromJavatype
				(
				this.sspmtResultOfLogPK.logNumber, 
				this.contractCd,
				this.sspmtResultOfLogPK.processingId, 
				this.cid, 
				this.logTime,
				this.logContent, 
				this.errorEmployeeId, 
				this.errorDate, 
				this.errorContent
				);
	}

	public static SspmtResultOfLog toEntity(ResultLogSaving domain) {
		return new SspmtResultOfLog
				(
				new SspmtResultOfLogPK(domain.getLogNumber(), domain.getProcessingId()), 
				domain.getContractCd(),
				domain.getCid(), 
				domain.getLogTime(), 
				domain.getLogContent().v(), 
				domain.getErrorEmployeeId(),
				domain.getErrorDate(), 
				domain.getErrorContent().v()
				);
	}

	public SspmtResultOfLog
		(
			SspmtResultOfLogPK sspmtResultOfLogPK, 
			String contractCd,
			String cid, 
			GeneralDateTime logTime, 
			String logContent,
			String errorEmployeeId, 
			GeneralDate errorDate, 
			String errorContent
		) {
		this.sspmtResultOfLogPK = sspmtResultOfLogPK;
		this.contractCd = contractCd;
		this.cid = cid;
		this.logTime = logTime;
		this.logContent = logContent;
		this.errorEmployeeId = errorEmployeeId;
		this.errorDate = errorDate;
		this.errorContent = errorContent;
	}
}