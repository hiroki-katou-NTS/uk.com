package nts.uk.ctx.pr.core.dom.wageprovision.taxexemptionlimit;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author thanh.tq 非課税限度額名称
 *
 */
@StringMaxLength(20)
public class TaxExemptionName extends StringPrimitiveValue<PrimitiveValue<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public TaxExemptionName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
