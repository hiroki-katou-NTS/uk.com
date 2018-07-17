package nts.uk.ctx.exio.dom.exo.dataformat.init;

/**
 * 
 * @author TamNX 進数選択
 *
 */
public enum DecimalSelection {
	HEXA_DECIMAL(0, "60進数"), 
	DECIMAL(1, "10進数");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private DecimalSelection(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
