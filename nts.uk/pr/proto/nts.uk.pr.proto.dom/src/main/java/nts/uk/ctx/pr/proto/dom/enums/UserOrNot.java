package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**
 * Enum: User or not
 *
 */
public enum UserOrNot {
	DO_NOT_USER(0),
	USE(1);
	
	public final int value;
	
	private static HashMap<Integer, UserOrNot> map = new HashMap<>();
	
	static {
        for (UserOrNot item : UserOrNot.values()) {
            map.put(item.value, item);
        }
    }
	
	UserOrNot(int value) {
		this.value = value;
	}
	
	/**
	 * Convert to enum UserOrNot by value
	 * @param value
	 * @return UserOrNot
	 */
	public static UserOrNot valueOf(int value) {
        return map.get(value);
    }
}
