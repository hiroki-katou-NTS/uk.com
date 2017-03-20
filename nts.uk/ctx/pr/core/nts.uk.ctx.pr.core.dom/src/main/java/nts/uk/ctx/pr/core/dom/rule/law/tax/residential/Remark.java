package nts.uk.ctx.pr.core.dom.rule.law.tax.residential;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author lanlt
 *
 */
@StringMaxLength(30)
public class Remark  extends StringPrimitiveValue<Remark>{
    /**
     * contructors
     * @param rawValue
     */
	public Remark(String rawValue) {
		super(rawValue);
		
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	

}
