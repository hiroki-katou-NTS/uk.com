package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

@TimeRange(min = "00:00", max = "48:00")
public class SearchTime extends TimeDurationPrimitiveValue<SearchTime> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SearchTime(int timeAsMinutes) {
		super(timeAsMinutes);
		// TODO Auto-generated constructor stub
	}

}
