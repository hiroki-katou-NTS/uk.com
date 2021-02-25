package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp;

/**
 * 打刻方法
 * @author tutk
 *
 */
public enum EngravingMethod {
	
	// タイムレコーダ(ID入力)
	TIME_RECORD_ID_INPUT(0, "Enum_EngravingMethod_TIME_RECORD_ID_INPUT", "タイムレコーダ(ID入力)"),
	
	// タイムレコーダ(磁気カード)
	TIME_RECORD_MAGNETIC_CARD(1, "Enum_EngravingMethod_TIME_RECORD_MAGNETIC_CARD", "タイムレコーダ(磁気カード)"),

	// タイムレコーダ(ICカード)
	TIME_RECORD_IC_CARD(2, "Enum_EngravingMethod_TIME_RECORD_IC_CARD", "タイムレコーダ(ICカード)"),
	
	// タイムレコーダ(指紋打刻)
	TIME_RECORD_FINGERPRINT_ENGRAVING(3, "Enum_EngravingMethod_TIME_RECORD_FINGERPRINT_ENGRAVING", "タイムレコーダ(指紋打刻)"),
	
	// Web打刻入力
	WEB_STAMP_INPUT(4, "Enum_EngravingMethod_WEB_STAMP_INPUT", "Web打刻入力"),
	
	// 直行直帰ボタン
	DIRECT_BOUNCE_BUTTON(5, "Enum_EngravingMethod_DIRECT_BOUNCE_BUTTON", "直行直帰ボタン"),
	
	// モバイル打刻
	MOBILE_ENGRAVING(6, "Enum_EngravingMethod_MOBILE_ENGRAVING", "モバイル打刻"),
	
	// モバイル打刻(エリア外)
	MOBILE_ENGRAVING_OUTSIDE_AREA(7, "Enum_EngravingMethod_MOBILE_ENGRAVING_OUTSIDE_AREA", "モバイル打刻(エリア外)");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;
	
	/** The Constant values. */
	private final static EngravingMethod[] values = EngravingMethod.values();

	/**
	 * Instantiates a new completion status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private EngravingMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the completion status
	 */
	public static EngravingMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (EngravingMethod val : EngravingMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
