package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework;

/**
 * The Enum RoleOvertimeWorkEnum.
 */
// 残業枠の役割
public enum RoleOvertimeWorkEnum {
	
	// 法定内残業
	/** The ot statutory work. */
	OT_STATUTORY_WORK(0, "Enum_OvertimeStatutoryWork", "法定内残業"),
	
	// 法定外残業
	/** The out ot statutory. */
	OUT_OT_STATUTORY(1,"Enum_OutOvertimeStatutory", "法定外残業"),
	
	// 法定内・外混在
	/** The mix in out statutory. */
	MIX_IN_OUT_STATUTORY(2, "Enum_MixInOutStatutory", "法定内・外混在");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;
	
	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RoleOvertimeWorkEnum[] values = RoleOvertimeWorkEnum.values();
	
	/**
	 * Instantiates a new role overtime work enum.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RoleOvertimeWorkEnum(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role overtime work enum
	 */
	public static RoleOvertimeWorkEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoleOvertimeWorkEnum val : RoleOvertimeWorkEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
