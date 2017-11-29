package nts.uk.ctx.bs.company.dom.company.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(10)
public class RepJob extends StringPrimitiveValue<RepJob>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 代表者職位 **/
	public RepJob(String rawValue){
		super(rawValue);
	}
}
