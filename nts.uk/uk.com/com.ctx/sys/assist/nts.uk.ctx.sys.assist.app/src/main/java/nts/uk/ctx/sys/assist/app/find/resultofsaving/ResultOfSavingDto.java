package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;
import nts.uk.ctx.sys.assist.dom.storage.ResultLogSaving;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;

/**
 * データ保存の保存結果
 */
@AllArgsConstructor
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
	
	 //field 実行結果
    private List<ResultLogSaving> listResultLogSavings;

	/**
	 * 対象人数
	 */
	private Integer targetNumberPeople;

	/**
	 * 結果状態
	 */
	private Integer saveStatus;

	/**
	 * 調査用保存
	 */
	private int saveForInvest;

	/**
	 * ファイルID
	 */
	private String fileId;
	
	//field ログイン情報
    private LoginInfo loginInfo;

	public static ResultOfSavingDto fromDomain(ResultOfSaving domain) {
		return new ResultOfSavingDto
			(
				domain.getStoreProcessingId(), 
				domain.getCid(), 
				domain.getFileSize().orElse(null), 
				domain.getPatternCode().v(), 
				domain.getSaveFileName().map(i -> i.v()).orElse(null),
				domain.getSaveName().v(),
				domain.getSaveForm().value,
				domain.getSaveEndDatetime().orElse(null),
				domain.getSaveStartDatetime().orElse(null),
				domain.getDeletedFiles().value, 
				domain.getCompressedPassword().map(i -> i.v()).orElse(null),
				domain.getPractitioner(),
				domain.getListResultLogSavings(),
				domain.getTargetNumberPeople().orElse(null),
				domain.getSaveStatus().map(i -> i.value).orElse(null),
				domain.getSaveForInvest().value,
				domain.getFileId().orElse(null),
				domain.getLoginInfo()
			);
	}

}
