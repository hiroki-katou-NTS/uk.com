package nts.uk.ctx.pr.proto.dom.enums;

import java.util.HashMap;

/**
 * �\�����敪
 * 
 * @author vunv
 *
 */
public enum SparePayAttribute {
	/**
	 * �ʏ�
	 */
	NORMAL(0),

	/**
	 * �\��
	 */
	PRELIMINARY(1);

	public int value;
	
	private static HashMap<Integer, SparePayAttribute> map = new HashMap<>();
	
	static {
		for (SparePayAttribute item : SparePayAttribute.values()) {
			map.put(item.value, item);
		}
	}
	
	private SparePayAttribute(int value) {
		this.value = value;
	}

	/**
	 * Convert to enum SparePayAttribute by value
	 * @param value
	 * @return SparePayAttribute
	 */
	public static SparePayAttribute valueOf(int value) {
        return map.get(value);
    }
}
