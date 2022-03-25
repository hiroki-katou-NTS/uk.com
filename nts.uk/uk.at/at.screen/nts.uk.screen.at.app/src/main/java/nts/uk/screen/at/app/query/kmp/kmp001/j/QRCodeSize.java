package nts.uk.screen.at.app.query.kmp.kmp001.j;

/**
 * 
 * @author tutt QRコードサイズ
 */
public enum QRCodeSize {
	/** 中 */
	MEDIUM(1),

	/** 大 */
	BIG(0),

	/** 小 */
	SMALL(2);

	public int value;

	/** The Constant values. */
	private final static QRCodeSize[] values = QRCodeSize.values();

	private QRCodeSize(int value) {
		this.value = value;
	}

	public static QRCodeSize valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (QRCodeSize val : QRCodeSize.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
