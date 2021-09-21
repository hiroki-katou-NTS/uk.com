package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 
 * @author HungTT 既存データの削除方法
 *
 */

public enum DeleteExistDataMethod {
	/**受け入れる会社のデータを全て削除してから受入	 */
	DELETE_ALL(1, "Enum_DeleteExistDataMethod_DELETE_ALL"),
	/**対象データのみ削除してから受入	 */
	DELETE_TARGET(2, "Enum_DeleteExistDataMethod_DELETE_TARGET");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DeleteExistDataMethod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
