package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ復旧の実行
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_PERFORM_DAT_RECOVER")
public class SspmtPerformDataRecovery extends UkJpaEntity implements Serializable {

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

	public PerformDataRecovery toDomain() {
		return new PerformDataRecovery(this.dataRecoveryProcessId, this.cid, this.saveProcessId, this.uploadfileId,
				this.recoveryFileName, this.numPeopleBeRestore, this.numPeopleSave, this.recoveryMethod,
				this.recoverFromAnoCom);
	}

	public static SspmtPerformDataRecovery toEntity(PerformDataRecovery domain) {
		return new SspmtPerformDataRecovery(domain.getDataRecoveryProcessId(), domain.getCid(),
				domain.getSaveProcessId().orElse(null), domain.getUploadfileId(), domain.getRecoveryFileName(),
				domain.getNumPeopleBeRestore(), domain.getNumPeopleSave(), domain.getRecoveryMethod().value,
				domain.getRecoverFromAnoCom().value);
	}
}
