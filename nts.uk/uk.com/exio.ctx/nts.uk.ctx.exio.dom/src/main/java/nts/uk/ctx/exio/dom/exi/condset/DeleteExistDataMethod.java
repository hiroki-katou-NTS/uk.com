package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 
 * @author HungTT 既存データの削除方法
 *
 */

public enum DeleteExistDataMethod {

	DELETE_ALL(1, "Enum_DeleteExistDataMethod_DELETE_ALL"),

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
