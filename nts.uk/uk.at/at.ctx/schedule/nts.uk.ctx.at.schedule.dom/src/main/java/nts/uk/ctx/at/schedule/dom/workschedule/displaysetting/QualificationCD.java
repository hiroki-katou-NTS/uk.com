package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;


import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
//資格コード
@StringMaxLength(10)
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
public class QualificationCD extends CodePrimitiveValue<QualificationCD> {


	private static final long serialVersionUID = 1L;

	public QualificationCD(String rawValue) {
		super(rawValue);
	
	}

}
