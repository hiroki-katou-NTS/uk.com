package nts.uk.ctx.at.function.dom.adapter;

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
	
	/** The hiredate. */
	HIREDATE(6);

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
