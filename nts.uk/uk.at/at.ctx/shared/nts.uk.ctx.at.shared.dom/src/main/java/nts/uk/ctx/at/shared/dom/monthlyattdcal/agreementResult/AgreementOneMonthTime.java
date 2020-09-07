package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementResult;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * ３６協定１ヶ月時間
 * 
 * @author quang.nh1
 *
 */

@TimeRange(max = "744:00", min = "00:00")
public class AgreementOneMonthTime extends TimeDurationPrimitiveValue<AgreementOneMonthTime> {
	private static final long serialVersionUID = 1L;

	public AgreementOneMonthTime(int rawValue) {
		super(rawValue);
	}

}
