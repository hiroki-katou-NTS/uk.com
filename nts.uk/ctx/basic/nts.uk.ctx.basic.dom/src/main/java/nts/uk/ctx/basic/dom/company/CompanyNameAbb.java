package nts.uk.ctx.basic.dom.company;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * @author lanlt
 *
 */
@StringMaxLength(20)
public class CompanyNameAbb  extends StringPrimitiveValue<CompanyNameAbb>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/**
	 * contructors
	 * @param rawValue
	 */
	public CompanyNameAbb(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
	

}
