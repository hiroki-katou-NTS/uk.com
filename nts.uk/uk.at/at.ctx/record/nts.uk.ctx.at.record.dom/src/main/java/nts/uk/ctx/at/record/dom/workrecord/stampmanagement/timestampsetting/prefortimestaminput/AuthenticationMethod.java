package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

/**
 * 認証方法設定
 * 
 * @author tutt
 *
 */
public enum AuthenticationMethod {

	/** ICカード */
	IC_CARD(0),

	/** QRコード */
	QR_CODE(1);

	public int value;

	/** The Constant values. */
	private final static AuthenticationMethod[] values = AuthenticationMethod.values();

	private AuthenticationMethod(int value) {
		this.value = value;
	}

	public static AuthenticationMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AuthenticationMethod val : AuthenticationMethod.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
