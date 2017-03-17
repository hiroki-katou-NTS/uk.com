package nts.uk.ctx.pr.report.dom.payment.comparing;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(4)
public class ItemCode extends CodePrimitiveValue<ItemCode> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public ItemCode(String rawValue) {
		super(rawValue);
	}

	// public boolean isRemainDaysOfHoliday() {
	// return this.v().equals("F206");
	// }
	//
	// public boolean isRemainTimeOfHoliday() {
	// return this.v().equals("F212");
	// }
	//
	// public boolean isNeededWorkDays() {
	// return this.v().equals("F201") || this.v().equals("F202");
	// }
	//
	// public boolean isNeededWorkTime() {
	// return this.v().equals("F203");
	// }
}
