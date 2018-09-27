package nts.uk.ctx.at.record.dom.monthly.information.care;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author phongtq
 */
@TimeRange(min ="0:00", max="999:59")
public class MonCareHdMinutes extends TimeDurationPrimitiveValue<MonCareHdMinutes>{

	private static final long serialVersionUID = 1991334616598114830L;

	public MonCareHdMinutes(Integer rawValue){
		super(rawValue);
	}
}
