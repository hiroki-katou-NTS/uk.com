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
@StringMaxLength(30)
@StringCharType(CharType.KANA)
public class ComNameKana extends StringPrimitiveValue<ComNameKana>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 会社名カナ  **/
	public ComNameKana(String rawValue){
		super(rawValue);
	}
}
