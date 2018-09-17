package nts.uk.ctx.at.record.dom.monthly.information.childnursing;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author phongtq
 */
@TimeRange(min ="0:00", max="999:59")
public class MonChildHdMinutes extends TimeDurationPrimitiveValue<MonChildHdMinutes>{

	private static final long serialVersionUID = 1991334616598114830L;

	public MonChildHdMinutes(Integer rawValue){
		super(rawValue);
	}
}
