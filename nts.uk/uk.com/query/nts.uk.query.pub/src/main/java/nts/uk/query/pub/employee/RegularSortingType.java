/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.pub.employee;

/**
 * The Enum RegularSortingType.
 */
// 規定の並び替え種類
public enum RegularSortingType {

	/** The employment. */
	EMPLOYMENT(0),
	
	/** The department. */
	DEPARTMENT(1),
	
	/** The workplace. */
	WORKPLACE(2),
	
	/** The position. */
	POSITION(3),
	
	/** The name. */
	NAME(4),
	
	/** The classification. */
	CLASSIFICATION(5),
	
	/** The hiredate. */
	HIREDATE(6);

	/** The value. */
	public final int value;

	/** The Constant values. */
	private final static RegularSortingType[] values = RegularSortingType.values();

	/**
	 * Instantiates a new regular sorting type.
	 *
	 * @param value the value
	 */
	private RegularSortingType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the regular sorting type
	 */
	public static RegularSortingType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RegularSortingType val : RegularSortingType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
