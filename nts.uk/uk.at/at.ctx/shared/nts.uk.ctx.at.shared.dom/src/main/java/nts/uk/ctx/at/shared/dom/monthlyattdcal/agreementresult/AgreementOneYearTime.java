package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 
 * @author quang.nh1
 *
 */
@TimeRange(max="8784:00", min = "00:00")
public class AgreementOneYearTime extends TimeDurationPrimitiveValue<AgreementOneYearTime> {

	public AgreementOneYearTime(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
	
}
