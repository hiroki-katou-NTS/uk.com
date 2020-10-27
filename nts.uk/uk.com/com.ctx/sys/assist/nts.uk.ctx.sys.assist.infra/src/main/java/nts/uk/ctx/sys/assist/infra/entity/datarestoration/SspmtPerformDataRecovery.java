package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.RestorationTarget;
import nts.uk.ctx.sys.assist.dom.datarestoration.Target;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * データ復旧の実行
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "SSPMT_PERFORM_DAT_RECOVER")
public class SspmtPerformDataRecovery extends ContractUkJpaEntity implements Serializable {

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
	 * 保存処理ID
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_PROCESS_ID")
	public String saveProcessId;

	/**
	 * アップロードファイルID
	 */
	@Basic(optional = false)
	@Column(name = "UPLOADFILE_ID")
	public String uploadfileId;

	/**
	 * 復旧ファイル名
	 */
	@Basic(optional = false)
	@Column(name = "RECOVERY_FILE_NAME")
	public String recoveryFileName;

	/**
	 * 復旧対象者数
	 */
	@Basic(optional = false)
	@Column(name = "NUM_PEOPLE_BE_RESTORE")
	public int numPeopleBeRestore;

	/**
	 * 保存対象者数
	 */
	@Basic(optional = false)
	@Column(name = "NUM_PEOPLE_SAVE")
	public int numPeopleSave;

	/**
	 * 復旧方法
	 */
	@Basic(optional = false)
	@Column(name = "RECOVERY_METHOD")
	public int recoveryMethod;

	/**
	 * 別会社復旧
	 */
	@Basic(optional = false)
	@Column(name = "RECOVER_FROM_ANO_COM")
	public int recoverFromAnoCom;

	@Override
	protected Object getKey() {
		return dataRecoveryProcessId;
	}

	public PerformDataRecovery toDomain(List<SspmtTarget> targets, List<SspmtRestorationTarget> restorationTarget) {
		return new PerformDataRecovery(this.dataRecoveryProcessId,
				this.cid, 
				targets != null ? targets.stream().map(x -> new Target(x.targetPk.dataRecoveryProcessId, x.targetPk.sid, x.scd, x.bussinessName)).collect(Collectors.toList()) : null,
				this.saveProcessId,
				this.uploadfileId,
				this.recoveryFileName,
				restorationTarget != null ? restorationTarget.stream().map(x -> new RestorationTarget(x.restorationTargetPk.dataRecoveryProcessId, x.restorationTargetPk.recoveryCategory, x.retentionPeriodIndicator, x.recoveryTargetStartYear, x.recoveryTargetEndYear, x.recoveryTargetStartYm, x.recoveryTargetEndYm, x.recoveryTargetStartDate, x.recoveryTargetEndDate)).collect(Collectors.toList()) : null,
				this.numPeopleBeRestore,
				this.numPeopleSave,
				this.recoveryMethod,
				this.recoverFromAnoCom);
	}

	public static SspmtPerformDataRecovery toEntity(PerformDataRecovery domain) {
		return new SspmtPerformDataRecovery(domain.getDataRecoveryProcessId(), domain.getCid(),
				domain.getSaveProcessId().orElse(null), domain.getUploadfileId(), domain.getRecoveryFileName(),
				domain.getNumPeopleBeRestore(), domain.getNumPeopleSave(), domain.getRecoveryMethod().value,
				domain.getRecoverFromAnoCom().value);
	}
}
