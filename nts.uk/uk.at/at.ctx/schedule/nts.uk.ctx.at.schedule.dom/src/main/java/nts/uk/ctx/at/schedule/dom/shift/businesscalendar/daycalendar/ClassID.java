package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class ClassID extends StringPrimitiveValue<PrimitiveValue<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public  ClassID(String rawValue){
		super(rawValue);	
	}
	}
