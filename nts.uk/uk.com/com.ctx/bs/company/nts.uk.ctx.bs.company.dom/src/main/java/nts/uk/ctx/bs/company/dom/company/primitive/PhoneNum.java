package nts.uk.ctx.bs.company.dom.company.primitive;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;
/**
 * 
 * @author yennth
 *
 */
@StringRegEx("^\\d{3}-\\d{4}-\\d{4}|(\\d{4}-\\d{4}-\\d{3}$)")
public class PhoneNum extends StringPrimitiveValue<PhoneNum>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 電話番号  */
	public PhoneNum (String rawValue){
		super(rawValue);
	}
}
