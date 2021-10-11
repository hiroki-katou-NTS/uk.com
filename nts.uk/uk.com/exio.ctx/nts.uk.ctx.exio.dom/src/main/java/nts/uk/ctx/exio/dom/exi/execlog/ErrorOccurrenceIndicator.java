package nts.uk.ctx.exio.dom.exi.execlog;

/**
 * 
 * @author DatLH エラー発生区分
 *
 */
public enum ErrorOccurrenceIndicator {
	/**
	 * 1: 編集エラー
	 */
	EDIT(1, "編集エラー"),
	/**
	 * 2: 受入条件エラー
	 */
	ACCEPTANCE_CONDITION(2, "受入条件エラー"),
	/**
	 * 3: コード変換エラー
	 */
	CODE_CONVERSION(3, "コード変換エラー"),
	/**
	 * 4: 値チェックエラ
	 */
	VALUE_CHECK(4, "値チェックエラ"),
	/**
	 * 5: 登録・更新時エラー
	 */
	REGIST_UPDATE(5, "登録・更新時エラー");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ErrorOccurrenceIndicator(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
