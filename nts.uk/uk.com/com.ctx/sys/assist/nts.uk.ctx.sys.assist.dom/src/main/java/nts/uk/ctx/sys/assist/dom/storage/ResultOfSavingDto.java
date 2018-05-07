/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Data;
import nts.arc.time.GeneralDateTime;

/**
 * @author nam.lh
 *
 */
@Data
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
	private int fileSize;

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
	private String saveForm;

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
	private String deletedFiles;

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
	private String saveStatus;

	/**
	 * 調査用保存
	 */
	private String saveForInvest;

	/**
	 * ファイルID
	 */
	private String fileId;
}
