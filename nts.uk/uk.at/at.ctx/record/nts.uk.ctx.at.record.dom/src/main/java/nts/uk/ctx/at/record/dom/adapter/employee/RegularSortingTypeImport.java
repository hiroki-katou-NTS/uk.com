/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.adapter.employee;

/**
 * The Enum RegularSortingType.
 */
// 規定の並び替え種類
public enum RegularSortingTypeImport {

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
	
	/** The hire date. */
	HIRE_DATE(6);

	/** The value. */
	public final int value;

	/** The Constant values. */
	private final static RegularSortingTypeImport[] values = RegularSortingTypeImport.values();

	/**
	 * Instantiates a new regular sorting type.
	 *
	 * @param value the value
	 */
	private RegularSortingTypeImport(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the regular sorting type
	 */
	public static RegularSortingTypeImport valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RegularSortingTypeImport val : RegularSortingTypeImport.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
