package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod;

/**
 * The Enum RoleOfOpenPeriodEnum.
 */
// 休出枠の役割
public enum RoleOfOpenPeriodEnum {
	
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
	private final static RoleOfOpenPeriodEnum[] values = RoleOfOpenPeriodEnum.values();
	
	/**
	 * Instantiates a new role of open period enum.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RoleOfOpenPeriodEnum(int value, String nameId, String description) {
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
	public static RoleOfOpenPeriodEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoleOfOpenPeriodEnum val : RoleOfOpenPeriodEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
