package nts.uk.ctx.exio.dom.exo.exoutsummaryservice;

/**
 * The Enum SearchReferenceRange.
 */
public enum SearchReferenceRange {
	
	/** The all employee. */
	// 参照可能範囲すべて
	ALL_REFERENCE_RANGE(0, "Enum_SearchReferenceRange_allReferenceRange", "参照可能範囲すべて"),

	/** The department and child. */
	// 所属と配下すべて
	AFFILIATION_AND_ALL_SUBORDINATES(1, "Enum_SearchReferenceRange_affiliationAndAllSubordinates", "所属と配下すべて"),

	/** The department only. */
	// 所属のみ
	AFFILIATION_ONLY(2, "Enum_SearchReferenceRange_affiliationOnly", "所属のみ"),

	//参照範囲を考慮しない
	/** The do not consider reference range. */
	DO_NOT_CONSIDER_REFERENCE_RANGE(3, "Enum_SearchReferenceRange_doNotConsiderReferenceRange", "参照範囲を考慮しない");

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


