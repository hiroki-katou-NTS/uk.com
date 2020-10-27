package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * データ復旧の結果
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPDT_RECOVER_RESULT")
public class SspdtRecoverResult extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * データ復旧処理ID
	 */
	@Id
	@Column(name = "DATA_RECOVERY_PROCESS_ID")
	public String dataRecoveryProcessId;
	
	/**
	 * データ保存処理ID
	 */
	@Basic(optional = false)
	@Column(name = "DATA_STOPRO_ID")
	private String dataStorageProcessId;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * 保存セットコード
	 */
	@Basic(optional = true)
	@Column(name = "PATTERN_CD")
	public String patternCode;

	/**
	 * 実行者
	 */
	@Basic(optional = false)
	@Column(name = "PRACTITIONER")
	public String practitioner;
	
	/**
	 * 実行結果
	 */
	@Basic(optional = true)
	@Column(name = "EXECUTION_RESULT")
	public int executionResult;

	/**
	 * 開始日時
	 */
	@Basic(optional = false)
	@Column(name = "START_DATE_TIME")
	public GeneralDateTime startDateTime;

	/**
	 * 終了日時
	 */
	@Basic(optional = true)
	@Column(name = "END_DATE_TIME")
	public GeneralDateTime endDateTime;

	/**
	 * 保存形態
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FORM")
	public int saveForm;

	/**
	 * 保存名称
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_NAME")
	public String saveName;

	/**
	 * ログイン情報.IPアドレス
	 */
	@Basic(optional = false)
	@Column(name = "PC_IP")
	public String pcId;

	/**
	 * ログイン情報.PC名
	 */
	@Basic(optional = false)
	@Column(name = "PC_NAME")
	public String pcName;

	/**
	 * ログイン情報.アカウント
	 */
	@Basic(optional = false)
	@Column(name = "PC_ACOUNT")
	public String pcAccount;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dataRecoverResult", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SspdtRecoverLog> listResultLogRecovers;

	@Override
	protected Object getKey() {
		return dataRecoveryProcessId;
	}

	public DataRecoveryResult toDomain() {
		return new DataRecoveryResult(
				this.dataRecoveryProcessId,
				this.dataStorageProcessId,
				this.cid, 
				this.patternCode, 
				this.practitioner,
				this.executionResult, 
				this.listResultLogRecovers.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				this.startDateTime, 
				this.endDateTime, 
				this.saveForm, 
				this.saveName,
				this.pcId,
				this.pcName,
				this.pcAccount);
	}

	public static SspdtRecoverResult toEntity(DataRecoveryResult domain) {
		return new SspdtRecoverResult
			(
				domain.getDataRecoveryProcessId(), domain.getDataStorageProcessId(), domain.getCid(),
				domain.getPatternCode().v(), domain.getPractitioner(),
				domain.getExecutionResult().value, domain.getStartDateTime(),
				domain.getEndDateTime().orElse(null), 
				domain.getSaveForm().value,
				domain.getSaveName().v(),
				domain.getLoginInfo().getIpAddress(),
				domain.getLoginInfo().getPcName(),
				domain.getLoginInfo().getAccount(),
				domain.getListDataRecoveryLogs().stream().map(item -> SspdtRecoverLog.toEntity(item)).collect(Collectors.toList())
			);
	}
}