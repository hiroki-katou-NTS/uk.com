package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.ResultLogSaving;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "SSPMT_RESULT_OF_LOG")
@NoArgsConstructor
@AllArgsConstructor
public class SspmtResultOfLog extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	@EmbeddedId
    public SspmtResultOfLogPK sspmtResultOfLogPK;
	
	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	//field 会社ID
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	//field ログ登録日時
	@Basic(optional = false)
	@Column(name = "LOG_DATETIME")
	public GeneralDateTime logTime;


	//field 処理内容
	@Basic(optional = false)
	@Column(name = "LOG_CONTENT")
	public String logContent;

	//field エラー社員									
	@Basic(optional = true)
	@Column(name = "ERROR_SID")
	public String errorEmployeeId;

	//field エラー日付	
	@Basic(optional = true)
	@Column(name = "ERROR_DATETIME")
	public GeneralDate errorDate;

	//field エラー内容	
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
				new SspmtResultOfLogPK(
				domain.getLogNumber(), 
				domain.getProcessingId()), 
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
