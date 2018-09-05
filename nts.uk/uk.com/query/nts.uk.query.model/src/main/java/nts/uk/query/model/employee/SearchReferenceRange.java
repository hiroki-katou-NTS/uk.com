/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.model.employee;


/**
 * The Enum SearchReferenceRange.
 */
public enum SearchReferenceRange {
	
	/** The all employee. */
	// 全社員
	ALL_EMPLOYEE(0, "Enum_EmployeeReferenceRange_allEmployee", "全社員"),

	/** The department and child. */
	// 部門（配下含む）
	DEPARTMENT_AND_CHILD(1, "Enum_EmployeeReferenceRange_departmentAndChild", "部門（配下含む）"),

	/** The department only. */
	// 部門（配下含まない）
	DEPARTMENT_ONLY(2, "Enum_EmployeeReferenceRange_departmentOnly", "部門（配下含まない）"),
	//参照範囲を考慮しない
	/** The do not consider reference range. */
	DO_NOT_CONSIDER_REFERENCE_RANGE(3, "Enum_EmployeeReferenceRange_DO_NOT_CONSIDER_REFERENCE_RANGE", "");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static SearchReferenceRange[] values = SearchReferenceRange.values();

	/**
	 * Instantiates a new employee reference range.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private SearchReferenceRange(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the employee reference range
	 */
	public static SearchReferenceRange valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SearchReferenceRange val : SearchReferenceRange.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}

