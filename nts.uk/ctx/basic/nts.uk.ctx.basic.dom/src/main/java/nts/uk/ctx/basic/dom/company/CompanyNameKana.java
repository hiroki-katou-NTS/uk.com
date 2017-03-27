package nts.uk.ctx.basic.dom.company;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(30)
public class CompanyNameKana extends StringPrimitiveValue<CompanyNameKana>{
	/**serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 
	 * contructors
	 * @params rawValue  raw value
	 */
	public CompanyNameKana(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
