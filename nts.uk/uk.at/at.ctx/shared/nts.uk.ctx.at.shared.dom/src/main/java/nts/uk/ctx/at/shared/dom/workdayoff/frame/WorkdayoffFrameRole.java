package nts.uk.ctx.at.shared.dom.workdayoff.frame;

/**
 * The Enum WorkdayoffFrameRole.
 */
// 休出枠の役割
public enum WorkdayoffFrameRole {
	
	// 法定外休出
	/** The non statutory holidays. */
	NON_STATUTORY_HOLIDAYS(0, "Enum_NonStatutoryHolidays", "法定外休出"),
	
	// 法定内休出
	/** The statutory holidays. */
	STATUTORY_HOLIDAYS(1,"Enum_StatutoryHolidays", "法定内休出"),
	
	// 法定内・外混在
	/** The mix within outside statutory. */
	MIX_WITHIN_OUTSIDE_STATUTORY(2, "Enum_MixWithinOutsideStatutory", "法定内・外混在");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkdayoffFrameRole[] values = WorkdayoffFrameRole.values();
	
	/**
	 * Instantiates a new role of open period enum.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private WorkdayoffFrameRole(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role of open period enum
	 */
	public static WorkdayoffFrameRole valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkdayoffFrameRole val : WorkdayoffFrameRole.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
