package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
// 特定日名称
@StringMaxLength(12)
public class SpecificName extends StringPrimitiveValue<SpecificName>{
	private static final long serialVersionUID = 1L;

	public SpecificName(String rawValue) {
		super(rawValue);
	}

}
