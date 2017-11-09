package nts.uk.ctx.bs.company.dom.company.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(10)
public class RepPos extends StringPrimitiveValue<RepPos>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 代表者職位 **/
	public RepPos(String rawValue){
		super(rawValue);
	}
}
