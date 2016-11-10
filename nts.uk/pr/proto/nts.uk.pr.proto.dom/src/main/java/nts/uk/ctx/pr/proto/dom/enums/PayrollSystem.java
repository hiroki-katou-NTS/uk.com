package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**
 * Enum: Payroll system
 *
 */
public enum PayrollSystem {
	MONTHLY(1), DAILY(2), DAY(3), HOURS(4);

	public final int value;

	private static HashMap<Integer, PayrollSystem> map = new HashMap<>();

	static {
		for (PayrollSystem item : PayrollSystem.values()) {
			map.put(item.value, item);
		}
	}

	PayrollSystem(int value) {
		this.value = value;
	}

	/**
	 * Convert to enum PayrollSystem by value
	 * 
	 * @param value
	 * @return PayrollSystem
	 */
	public static PayrollSystem valueOf(int value) {
		return map.get(value);
	}
}
