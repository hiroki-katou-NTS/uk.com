package nts.uk.ctx.exio.dom.exi.condset;

/**
 * 
 * @author HungTT
 * 受入モード
 *
 */
public enum AcceptMode {

	INSERT_ONLY(0, "新規受入のみを行う（INSERTのみ）"),
	
	UPDATE_ONLY(1, "上書き受入のみを行う（UPDATEのみ）"),

	INSERT_AND_UPDATE(2, "新規受入と上書き受入を行う（INSERT・UPDATE）");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private AcceptMode(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
