package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.AllArgsConstructor;

/**
 * @author ThanhNX
 * 
 *         認証方法
 */
@AllArgsConstructor
public enum AuthcMethod {

	// 0:ID認証
	ID_AUTHC(0, "ID認証"),

	// 1:ICカード認証
	IC_CARD_AUTHC(1, "ICカード認証"),

	// 2:静脈認証
	VEIN_AUTHC(2, "静脈認証"),

	// 3:外部認証
	EXTERNAL_AUTHC(3, "外部認証");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static AuthcMethod[] values = AuthcMethod.values();

	public static AuthcMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AuthcMethod val : AuthcMethod.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
