package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;
/**
 * 日次任意時間
 * @author tutk
 *
 */
@TimeRange(min = "-99:59", max = "99:59")
public class AnyItemTime extends TimeDurationPrimitiveValue<AnyItemTime>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemTime(Integer rawValue) {
		super(rawValue.compareTo(60*99 + 59) > 0 ? 60*99 + 59 
   			 								 	 : rawValue.compareTo(-(60*99 + 59)) < 0 ? -(60*99 + 59) 
   			 										 						   		     : rawValue);
	}

	@Override
	protected String getPaddedMinutePart() {
		// TODO Auto-generated method stub
		return null;
	}
}