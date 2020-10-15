package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 36協定1年間時間
 * @author nampt
 */
@TimeRange(max="8784:00", min = "00:00")
public class AgreementOneYearTime extends TimeDurationPrimitiveValue<AgreementOneYearTime> {
	
	public AgreementOneYearTime(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;

}
