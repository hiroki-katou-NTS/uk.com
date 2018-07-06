package nts.uk.ctx.sys.assist.dom.tablelist;

/**
 * 参照年
 * 
 * @author thanh.tq
 *
 */
public enum ReferenceYear {
	
	//当年
	THIS_YEAR(1, "Enum_ReferenceYear_THIS_YEAR"),
	//前年
	PREVIOUS_YEAR(2,"Enum_ReferenceYear_PREVIOUS_YEAR"),
	//前々年
	TWO_YEARS_AGO(3, "Enum_ReferenceYear_TWO_YEARS_AGO");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private ReferenceYear(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
