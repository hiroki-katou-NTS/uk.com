package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**
 * ���^�ܗ^�敪
 * 
 * @author vunv
 *
 */
public enum PayBonusAttribute {
	/**
	 * ���^
	 */
	SALARY(0),

	/**
	 * �ܗ^
	 */
	BONUSES(1);

	public int value;

	private static HashMap<Integer, PayBonusAttribute> map = new HashMap<>();

	static {
		for (PayBonusAttribute item : PayBonusAttribute.values()) {
			map.put(item.value, item);
		}
	}

	private PayBonusAttribute(int value) {
		this.value = value;
	}
	
	/**
	 * Convert to enum PayBonusAttribute by value
	 * @param value
	 * @return PayBonusAttribute
	 */
	public static PayBonusAttribute valueOf(int value) {
        return map.get(value);
    }
}
