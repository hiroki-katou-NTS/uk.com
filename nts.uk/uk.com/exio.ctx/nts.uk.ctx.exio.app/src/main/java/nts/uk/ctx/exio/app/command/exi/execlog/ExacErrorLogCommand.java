package nts.uk.ctx.exio.app.command.exi.execlog;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

@Value
public class ExacErrorLogCommand {

	/**
	 * ログ連番
	 */
	private int logSeqNumber;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 外部受入処理ID
	 */
	private String externalProcessId;

	/**
	 * CSVエラー項目名
	 */
	private String csvErrorItemName;

	/**
	 * CSV受入値
	 */
	private String csvAcceptedValue;

	/**
	 * エラー内容
	 */
	private String errorContents;

	/**
	 * レコード番号
	 */
	private int recordNumber;

	/**
	 * ログ登録日時
	 */
	private GeneralDateTime logRegDateTime;

	/**
	 * 項目名
	 */
	private String itemName;

	/**
	 * エラー発生区分
	 */
	private int errorAtr;

	private Long version;

}
