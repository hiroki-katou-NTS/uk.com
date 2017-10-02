/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailserver;

/**
 * サーバ使用
 */

public enum IpVersion {

	IPv4(0, "ENUM_IPVERSION_IPV4"),

	IPv6(1, "ENUM_IPVERSION_IPV6");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static IpVersion[] values = IpVersion.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private IpVersion(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static IpVersion valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (IpVersion val : IpVersion.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
