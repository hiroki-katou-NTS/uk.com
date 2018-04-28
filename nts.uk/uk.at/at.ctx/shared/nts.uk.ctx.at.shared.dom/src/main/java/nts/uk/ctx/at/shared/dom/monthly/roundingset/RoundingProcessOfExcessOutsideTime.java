package nts.uk.ctx.at.shared.dom.monthly.roundingset;

/**
 * 時間外超過の端数処理.
 *
 * @author shuichu_ishida
 */
public enum RoundingProcessOfExcessOutsideTime {
	
	/**  切り捨て. */
	ROUNDING_DOWN(0, "Enum_Exc_Rounding_Down", "切り捨て"),
	
	/**  切り上げ. */
	ROUNDING_UP(1, "Enum_Exc_Rounding_Up", "切り上げ"),
	
	/**  要素の丸めに従う. */
	FOLLOW_ELEMENTS(2, "Enum_Exc_Follow_Element","要素の丸めに従う");
	
	/** The value. */
	public int value;
	
	/** The name id. */
	public String nameId;
	
	/** The description. */
	public String description;
	
	/**
	 * Instantiates a new rounding process of excess outside time.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private RoundingProcessOfExcessOutsideTime(int value, String nameId, String description){
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
}
