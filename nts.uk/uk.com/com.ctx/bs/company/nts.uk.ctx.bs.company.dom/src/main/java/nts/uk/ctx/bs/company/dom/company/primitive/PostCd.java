package nts.uk.ctx.bs.company.dom.company.primitive;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
/**
 * 
 * @author yennth
 *
 */
@StringRegEx("^\\s*$|^\\d{7}$|^(\\d{3}-\\d{4})$")
public class PostCd extends StringPrimitiveValue<PostCd>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 郵便番号  */
	public PostCd (String rawValue){
		super(rawValue);
	}
}   
