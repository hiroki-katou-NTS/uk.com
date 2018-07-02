package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;

/**
 * データ保存の保存結果
 */
@AllArgsConstructor
@Value
public class ResultOfSavingDto {

	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * システム種類
	 */
	private int systemType;

	/**
	 * ファイル容量
	 */
	private long fileSize;

	/**
	 * 保存セットコード
	 */
	private String saveSetCode;

	/**
	 * 保存ファイル名
	 */
	private String saveFileName;

	/**
	 * 保存名称
	 */
	private String saveName;

	/**
	 * 保存形態
	 */
	private int saveForm;

	/**
	 * 保存終了日時
	 */
	private GeneralDateTime saveEndDatetime;

	/**
	 * 保存開始日時
	 */
	private GeneralDateTime saveStartDatetime;

	/**
	 * 削除済みファイル
	 */
	private int deletedFiles;

	/**
	 * 圧縮パスワード
	 */
	private String compressedPassword;

	/**
	 * 実行者
	 */
	private String practitioner;

	/**
	 * 対象人数
	 */
	private int targetNumberPeople;

	/**
	 * 結果状態
	 */
	private int saveStatus;

	/**
	 * 調査用保存
	 */
	private int saveForInvest;

	/**
	 * ファイルID
	 */
	private String fileId;

	public static ResultOfSavingDto fromDomain(ResultOfSaving domain) {
		return new ResultOfSavingDto(domain.getStoreProcessingId(), domain.getCid(), domain.getSystemType().value,
				domain.getFileSize(), domain.getSaveSetCode().v(), domain.getSaveFileName().v(),
				domain.getSaveName().v(), domain.getSaveForm().value, domain.getSaveEndDatetime(),
				domain.getSaveStartDatetime(), domain.getDeletedFiles().value, domain.getCompressedPassword().v(),
				domain.getPractitioner(), domain.getTargetNumberPeople(), domain.getSaveStatus().value,
				domain.getSaveForInvest().value, domain.getFileId());
	}

}
