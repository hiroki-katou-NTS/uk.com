package nts.uk.ctx.sys.assist.infra.entity.storage;

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
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * データ保存の保存結果
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_RESULT_OF_SAVING")
public class SspmtResultOfSaving extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * データ保存処理ID
	 */
	@Id
	@Basic(optional = false)
	@Column(name = "STORE_PROCESSING_ID")
	public String storeProcessingId;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String cid;

	/**
	 * システム種類
	 */
	@Basic(optional = false)
	@Column(name = "SYSTEM_TYPE")
	public int systemType;

	/**
	 * ファイル容量
	 */
	@Basic(optional = true)
	@Column(name = "FILE_SIZE")
	public long fileSize;

	/**
	 * 保存セットコード
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_SET_CODE")
	public String saveSetCode;

	/**
	 * 保存ファイル名
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_FILE_NAME")
	public String saveFileName;

	/**
	 * 保存名称
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_NAME")
	public String saveName;

	/**
	 * 保存形態
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FORM")
	public int saveForm;

	/**
	 * 保存終了日時
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_END_DATETIME")
	public GeneralDateTime saveEndDatetime;

	/**
	 * 保存開始日時
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_START_DATETIME")
	public GeneralDateTime saveStartDatetime;

	/**
	 * 削除済みファイル
	 */
	@Basic(optional = false)
	@Column(name = "DELETED_FILES")
	public int deletedFiles;

	/**
	 * 圧縮パスワード
	 */
	@Basic(optional = true)
	@Column(name = "COMPRESSED_PASSWORD")
	public String compressedPassword;

	/**
	 * 実行者
	 */
	@Basic(optional = false)
	@Column(name = "PRACTITIONER")
	public String practitioner;

	/**
	 * 対象人数
	 */
	@Basic(optional = true)
	@Column(name = "TARGET_NUMBER_PEOPLE")
	public int targetNumberPeople;

	/**
	 * 結果状態
	 */
	@Basic(optional = true)
	@Column(name = "SAVE_STATUS")
	public int saveStatus;

	/**
	 * 調査用保存
	 */
	@Basic(optional = false)
	@Column(name = "SAVE_FOR_INVEST")
	public int saveForInvest;

	/**
	 * ファイルID
	 */
	@Basic(optional = true)
	@Column(name = "FILE_ID")
	public String fileId;
	
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
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "resultOfSaving", orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SspmtResultOfLog> listResultOfLogs;

	@Override
	protected Object getKey() {
		return storeProcessingId;
	}

	public ResultOfSaving toDomain() {
		return new ResultOfSaving
			(
				this.storeProcessingId, 
				this.cid, 
				this.systemType, 
				this.fileSize, 
				this.saveSetCode,
				this.saveFileName, 
				this.saveName, 
				this.saveForm, 
				this.saveEndDatetime, 
				this.saveStartDatetime,
				this.deletedFiles, 
				this.compressedPassword, 
				this.practitioner,
				this.listResultOfLogs.stream().map(item -> item.toDomain()).collect(Collectors.toList()),
				this.targetNumberPeople,
				this.saveStatus,
				this.saveForInvest,
				this.fileId, 
				new LoginInfo(pcId,pcName,pcAccount)
			);
	}

	public static SspmtResultOfSaving toEntity(ResultOfSaving domain) {
		return new SspmtResultOfSaving
			(
				domain.getStoreProcessingId(), 
				domain.getCid(), 
				domain.getSystemType().value,
				domain.getFileSize().orElse(null),
				domain.getSaveSetCode().map(i -> i.v()).orElse(null),
				domain.getSaveFileName().map(i -> i.v()).orElse(null), 
				domain.getSaveName().v(),
				domain.getSaveForm().value, 
				domain.getSaveEndDatetime().orElse(null), 
				domain.getSaveStartDatetime().orElse(null),
				domain.getDeletedFiles().value, 
				domain.getCompressedPassword().map(i -> i.v()).orElse(null),
				domain.getPractitioner(), 
				domain.getTargetNumberPeople().orElse(null),
				domain.getSaveStatus().map(i->i.value).orElse(null),
				domain.getSaveForInvest().value,
				domain.getFileId().orElse(null),
				domain.getLoginInfo().getIpAddress(),
				domain.getLoginInfo().getPcName(),
				domain.getLoginInfo().getAccount(),
				domain.getListResultLogSavings().stream().map(item -> SspmtResultOfLog.toEntity(item)).collect(Collectors.toList())
			);
	}
}
