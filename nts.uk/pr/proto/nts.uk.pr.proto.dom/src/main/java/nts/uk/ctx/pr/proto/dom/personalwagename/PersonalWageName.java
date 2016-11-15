package nts.uk.ctx.pr.proto.dom.personalwagename;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLengh;

@StringMaxLengh(40)
public class PersonalWageName extends StringPrimitiveValue<PersonalWageName> {
	public PersonalWageName(String rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
}
