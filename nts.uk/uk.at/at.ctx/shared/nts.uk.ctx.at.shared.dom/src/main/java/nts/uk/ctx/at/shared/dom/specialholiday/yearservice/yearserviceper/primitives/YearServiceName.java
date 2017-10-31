package nts.uk.ctx.at.shared.dom.specialholiday.yearservice.yearserviceper.primitives;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(20)
public class YearServiceName extends StringPrimitiveValue<PrimitiveValue<String>>{
	private static final long serialVersionUID = 1L;
	public YearServiceName (String rawValue){
		super(rawValue);
	}
}
