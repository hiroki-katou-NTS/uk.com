package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**
 * Enum: commute means attribute
 *
 */
public enum CommuteMeansAttribute {
	TRANSPORTATION_EQUIPMENT(1),
	TRANSPORTTION_FACILITIES(2);
	
	public final int value;
	
	private static HashMap<Integer, CommuteMeansAttribute> map = new HashMap<>();
	
	static {
        for (CommuteMeansAttribute item : CommuteMeansAttribute.values()) {
            map.put(item.value, item);
        }
    }
	
	CommuteMeansAttribute(int value) {
		this.value = value;
	}
	
	/**
	 * Convert to enum CommuteMeansAttribute by value
	 * @param value
	 * @return CommuteMeansAttribute
	 */
	public static CommuteMeansAttribute valueOf(int value) {
        return map.get(value);
    }

}
