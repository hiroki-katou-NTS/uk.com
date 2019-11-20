package nts.uk.ctx.at.function.dom.dailyperformanceformat.enums;

/**
 * 初期表示フォーマット
 */
public enum PCSmartPhoneAtt {
	/* PC */
	PC(0),

	/* スマホ */
	SMART_PHONE(1);

	/** The value. */
	public final int value;

	private PCSmartPhoneAtt(int type) {
		this.value = type;
	}
}
