package nts.uk.ctx.exio.dom.exi.codeconvert;

/**
 * @author sang.nv するしない区分
 *
 */
public enum NotUseAtr {
	USE(1, "Enum_NotUseAtr_To"),
	NOT_USE(0, "Enum_NotUseAtr_Donot");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private NotUseAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
