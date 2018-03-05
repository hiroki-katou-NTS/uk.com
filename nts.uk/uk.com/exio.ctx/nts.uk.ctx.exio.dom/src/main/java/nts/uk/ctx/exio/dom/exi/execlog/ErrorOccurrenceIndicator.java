package nts.uk.ctx.exio.dom.exi.execlog;

/**
 * 
 * @author DatLH エラー発生区分
 *
 */
public enum ErrorOccurrenceIndicator {
	EDIT(1, "編集エラー"), 
	ACCEPTANCE_CONDITION(2, "受入条件エラー"), 
	CODE_CONVERSION(3, "コード変換エラー"),
	VALUE_CHECK(4, "値チェックエラ"),
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
