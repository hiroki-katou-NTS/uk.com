package nts.uk.ctx.at.function.dom.holidaysremaining;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * コード変換名称
 */
@StringMaxLength(20)
public class HolidayRemainingName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contructor
	 * 
	 * @param rawValue
	 */
	public HolidayRemainingName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
