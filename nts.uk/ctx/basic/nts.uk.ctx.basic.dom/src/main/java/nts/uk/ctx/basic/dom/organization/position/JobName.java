package nts.uk.ctx.basic.dom.organization.position;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;


@StringCharType(CharType.ALPHABET)
@StringMaxLength(10)
public class JobName extends StringPrimitiveValue<PrimitiveValue<String>>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}