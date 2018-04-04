package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 
 * @author HungTT
 * 受入モード
 *
 */
public enum AcceptMode {

	INSERT_ONLY(0, "Enum_AcceptMode_INSERT_ONLY"),
	
	UPDATE_ONLY(1, "Enum_AcceptMode_INSERT_UPDATE_ONLY"),

	INSERT_AND_UPDATE(2, "Enum_AcceptMode_INSERT_INSERT_AND_UPDATE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private AcceptMode(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
