package nts.uk.ctx.sys.assist.infra.entity.datarestoration;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * サーバー準備動作管理
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_SERVER_PREPARE_MNG")
public class SspmtServerPrepareMng extends ContractUkJpaEntity implements Serializable {

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
	@Basic(optional = true)
	@Column(name = "DATA_STORE_PROCESS_ID")
	public String dataStoreProcessId;

	/**
	 * ファイルID
	 */
	@Basic(optional = true)
	@Column(name = "FILE_ID")
	public String fileId;

	/**
	 * アップロードファイル名
	 */
	@Basic(optional = true)
	@Column(name = "UPLOAD_FILE_NAME")
	public String uploadFileName;

	/**
	 * アップロードをするしない
	 */
	@Basic(optional = false)
	@Column(name = "DO_NOT_UPLOAD")
	public int doNotUpload;

	/**
	 * パスワード
	 */
	@Basic(optional = true)
	@Column(name = "PASSWORD")
	public String password;

	/**
	 * 動作状態
	 */
	@Basic(optional = false)
	@Column(name = "OPERATING_CONDITION")
	public int operatingCondition;

	@Override
	protected Object getKey() {
		return dataRecoveryProcessId;
	}

	public ServerPrepareMng toDomain() {
		return new ServerPrepareMng(this.dataRecoveryProcessId, this.dataStoreProcessId, this.fileId,
				this.uploadFileName, this.doNotUpload, this.password, this.operatingCondition);
	}

	public static SspmtServerPrepareMng toEntity(ServerPrepareMng domain) {
		return new SspmtServerPrepareMng(domain.getDataRecoveryProcessId(),
				domain.getDataStoreProcessId().orElse(null), domain.getFileId().orElse(null),
				domain.getUploadFileName().orElse(null), domain.getDoNotUpload().value,
				domain.getPassword().isPresent() ? domain.getPassword().get().v() : null,
				domain.getOperatingCondition().value);
	}
}
