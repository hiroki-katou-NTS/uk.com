package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ復旧の結果
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_DATA_RECOVER_RESULT")
public class SspmtDataRecoverResult extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * データ復旧処理ID
	 */
	@Id
	@Column(name = "DATA_RECOVERY_PROCESS_ID")
	public String dataRecoveryProcessId;

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
	@Column(name = "SAVE_SET_CD")
	public String saveSetCd;

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
	public String executionResult;

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

	@Override
	protected Object getKey() {
		return dataRecoveryProcessId;
	}

	public DataRecoveryResult toDomain() {
		return new DataRecoveryResult(this.dataRecoveryProcessId, this.cid, this.saveSetCd, this.practitioner,
				this.executionResult, this.startDateTime, this.endDateTime, this.saveForm, this.saveName);
	}

	public static SspmtDataRecoverResult toEntity(DataRecoveryResult domain) {
		return new SspmtDataRecoverResult(domain.getDataRecoveryProcessId(), domain.getCid(),
				domain.getSaveSetCode().orElse(null), domain.getPractitioner(),
				domain.getExecutionResult().orElse(null), domain.getStartDateTime(),
				domain.getEndDateTime().orElse(null), 
				domain.getSaveForm(),
				domain.getSaveName());
	}
}
