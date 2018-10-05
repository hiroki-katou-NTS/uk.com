package nts.uk.ctx.exio.dom.exo.category;

/**
 * システム使用可否
 */
public enum SystemUsability {
	/**
	 * 使用不可
	 */
	UNVAILABLE(0),
	/**
	 * 使用可
	 */
	AVAILABLE(1);

	/** The value. */
	public final int value;

	private SystemUsability(int value) {
		this.value = value;
	}
}
