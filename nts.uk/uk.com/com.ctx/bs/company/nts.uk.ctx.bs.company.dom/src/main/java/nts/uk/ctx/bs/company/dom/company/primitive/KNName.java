package nts.uk.ctx.bs.company.dom.company.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(60)
@StringCharType(CharType.KANA)
public class KNName extends StringPrimitiveValue<KNName>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 会社名カナ  **/
	public KNName(String rawValue){
		super(rawValue);
	}
}
