package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset;

/**
 * 時間外超過の端数処理
 * @author shuichu_ishida
 */
public enum RoundingProcessOfExcessOutsideTime {
	/** 切り捨て */
	ROUNDING_DOWN(0, "切り捨て", "Enum_Rounding_Down"),
	/** 切り上げ */
	ROUNDING_UP(1, "切り上げ", "Enum_Rounding_Up"),
	/** 要素の丸めに従う */
	FOLLOW_ELEMENTS(2, "要素の丸めに従う", "Enum_Follow_Elements");
	
	public int value;
	public String nameId;
	public String description;

	RoundingProcessOfExcessOutsideTime(int value, String nameId, String description){
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	private final static RoundingProcessOfExcessOutsideTime[] values = RoundingProcessOfExcessOutsideTime.values();

	public static RoundingProcessOfExcessOutsideTime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoundingProcessOfExcessOutsideTime val : RoundingProcessOfExcessOutsideTime.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
