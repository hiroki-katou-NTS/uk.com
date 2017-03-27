package nts.uk.ctx.basic.dom.company;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 
 * @author lanlt
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(8)
public class ExclusVersion extends StringPrimitiveValue<ExclusVersion>{
     /**serialVersionUID*/
	private static final long serialVersionUID = 1L;
	/**
	 * contructor
	 * @param rawValue
	 */
	public ExclusVersion(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
