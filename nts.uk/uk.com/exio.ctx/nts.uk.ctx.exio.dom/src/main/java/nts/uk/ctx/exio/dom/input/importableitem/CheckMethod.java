package nts.uk.ctx.exio.dom.input.importableitem;

/**
 * 受入値の検証方法
 */
public enum CheckMethod {
	PRIMITIVE_VALUE(1),
	ENUM(2),
	;
	
	public final int value;

	private CheckMethod(int value) {
		this.value = value;
	}
}
