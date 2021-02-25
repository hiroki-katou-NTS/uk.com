package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * ３６協定１ヶ月時間
 * @author nampt
 */
@TimeRange(max="744:00", min = "00:00")
public class AgreementOneMonthTime extends TimeDurationPrimitiveValue<AgreementOneMonthTime>{
	
	public AgreementOneMonthTime(int rawValue) {
		super(rawValue);
	}

	private static final long serialVersionUID = 1L;
	
	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(0);
		if (rawValue > 744 * 60) rawValue = 744 * 60;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}
}
