package nts.uk.ctx.sys.assist.dom.datarestoration;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
public class DataRecoverySelection {

	/**
	 * 保存セットコード
	 */
	private String code;

	/**
	 * 保存名称
	 */
	private String name;

	/**
	 * 補足説明
	 */
	private String suppleExplanation;

	/**
	 * 保存開始日時
	 */
	private GeneralDateTime saveStartDatetime;

	/**
	 * 保存形態
	 */
	private int saveForm;

	/**
	 * 対象人数
	 */
	private int targetNumberPeople;
	/**
	 * 保存ファイル名
	 */
	private String saveFileName;

	/**
	 * ファイルID
	 */
	private String fileId;

	/**
	 * データ保存処理ID
	 */
	private String storeProcessingId;

	public DataRecoverySelection(String code, String name, String suppleExplanation, GeneralDateTime saveStartDatetime,
			int saveForm, int targetNumberPeople, String saveFileName, String fileId, String storeProcessingId) {
		super();
		this.code = code;
		this.name = name;
		this.suppleExplanation = suppleExplanation;
		this.saveStartDatetime = saveStartDatetime;
		this.saveForm = saveForm;
		this.targetNumberPeople = targetNumberPeople;
		this.saveFileName = saveFileName;
		this.fileId = fileId;
		this.storeProcessingId = storeProcessingId;
	}

}
