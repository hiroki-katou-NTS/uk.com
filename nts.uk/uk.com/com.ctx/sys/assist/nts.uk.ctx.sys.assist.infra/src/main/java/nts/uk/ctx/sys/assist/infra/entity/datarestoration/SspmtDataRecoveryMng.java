package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryMng;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * データ復旧動作管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_DATA_RECOVERY_MNG")
public class SspmtDataRecoveryMng extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 処理ID
	 */
	@Id
	@Column(name = "DATA_RECOVERY_PROCESS_ID")
	public String dataRecoveryProcessId;

	/**
	 * エラー件数
	 */
	@Basic(optional = false)
	@Column(name = "ERROR_COUNT")
	public int errorCount;

	/**
	 * カテゴリカウント
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_CNT")
	public int categoryCnt;

	/**
	 * カテゴリトータルカウント
	 */
	@Basic(optional = false)
	@Column(name = "CATEGORY_TOTAL_COUNT")
	public int categoryTotalCount;

	/**
	 * トータル処理件数
	 */
	@Basic(optional = true)
	@Column(name = "TOTAL_NUM_OF_PROCESSES")
	public int totalNumOfProcesses;

	/**
	 * 処理件数
	 */
	@Basic(optional = true)
	@Column(name = "NUM_OF_PROCESSES")
	public int numOfProcesses;

	/**
	 * 処理対象社員コード
	 */
	@Basic(optional = true)
	@Column(name = "PROCESS_TARGET_EMP_CODE")
	public String processTargetEmpCode;

	/**
	 * 中断状態
	 */
	@Basic(optional = false)
	@Column(name = "SUSPENDED_STATE")
	public int suspendedState;

	/**
	 * 動作状態
	 */
	@Basic(optional = false)
	@Column(name = "OPERATING_CONDITION")
	public int operatingCondition;

	/**
	 * 復旧日付
	 */
	@Basic(optional = true)
	@Column(name = "RECOVERY_DATE")
	public String recoveryDate;

	@Override
	protected Object getKey() {
		return dataRecoveryProcessId;
	}

	public DataRecoveryMng toDomain() {
		return new DataRecoveryMng(this.dataRecoveryProcessId, this.errorCount, this.categoryCnt,
				this.categoryTotalCount, this.totalNumOfProcesses, this.numOfProcesses, this.processTargetEmpCode,
				this.suspendedState, this.operatingCondition, this.recoveryDate);
	}

	public static SspmtDataRecoveryMng toEntity(DataRecoveryMng domain) {
		return new SspmtDataRecoveryMng(domain.getDataRecoveryProcessId(), domain.getErrorCount(),
				domain.getCategoryCnt(), domain.getCategoryTotalCount(), domain.getTotalNumOfProcesses().orElse(null),
				domain.getNumOfProcesses().orElse(null), domain.getProcessTargetEmpCode().orElse(null),
				domain.getSuspendedState().value, domain.getOperatingCondition().value, domain.getRecoveryDate());
	}
}
