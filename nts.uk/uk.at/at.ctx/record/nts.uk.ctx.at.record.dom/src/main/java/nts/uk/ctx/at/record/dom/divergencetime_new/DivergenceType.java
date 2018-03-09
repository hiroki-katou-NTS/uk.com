package nts.uk.ctx.at.record.dom.divergencetime_new;

/**
 * The Enum DivergenceType.
 */
//乖離の種類
public enum DivergenceType {

	/** The arbitrary divergence time. */
	ARBITRARY_DIVERGENCE_TIME(0, "Enum_DivergenceType_ARBITRARY_DIVERGENCE_TIME"),

	/** The holidays departure time. */
	HOLIDAYS_DEPARTURE_TIME(1, "Enum_DivergenceType_HOLIDAYS_DEPARTURE_TIME"),
	
	/** The entry divergence time. */
	ENTRY_DIVERGENCE_TIME(2, "Enum_DivergenceType_ENTRY_DIVERGENCE_TIME"),
	
	/** The evacuation departure time. */
	EVACUATION_DEPARTURE_TIME(3, "Enum_DivergenceType_EVACUATION_DEPARTURE_TIME"),
	
	/** The logon pc divergence time. */
	LOGON_PC_DIVERGENCE_TIME(4, "Enum_DivergenceType_LOGON_PC_DIVERGENCE_TIME"),
	
	/** The logoff pc divergence time. */
	LOGOFF_PC_DIVERGENCE_TIME(5, "Enum_DivergenceType_LOGOFF_PC_DIVERGENCE_TIME"),
	
	/** The predetermined break time divergence time. */
	PREDETERMINED_BREAK_TIME_DIVERGENCE_TIME(6, "Enum_DivergenceType_PREDETERMINED_BREAK_TIME_DIVERGENCE_TIME"),
	
	/** The non scheduled divergence time. */
	NON_SCHEDULED_DIVERGENCE_TIME(7, "Enum_DivergenceType_NON_SCHEDULED_DIVERGENCE_TIME"),
	
	/** The premature overtime departure time. */
	PREMATURE_OVERTIME_DEPARTURE_TIME(8, "Enum_DivergenceType_PREMATURE_OVERTIME_DEPARTURE_TIME"),
	
	/** The normal overtime divergence time. */
	NORMAL_OVERTIME_DIVERGENCE_TIME(9, "Enum_DivergenceType_NORMAL_OVERTIME_DIVERGENCE_TIME");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static DivergenceType[] values = DivergenceType.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private DivergenceType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static DivergenceType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DivergenceType val : DivergenceType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
