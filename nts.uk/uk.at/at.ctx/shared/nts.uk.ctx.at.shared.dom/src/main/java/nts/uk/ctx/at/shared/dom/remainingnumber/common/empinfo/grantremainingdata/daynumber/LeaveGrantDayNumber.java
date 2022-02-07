package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import java.util.Optional;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 月別休暇付与日数
 * @author masaaki_jinno
 *
 */
@HalfIntegerRange(min = 0, max = 99.5)
public class LeaveGrantDayNumber extends HalfIntegerPrimitiveValue<LeaveGrantDayNumber>{

	private static final long serialVersionUID = 6651196653684992015L;

	public LeaveGrantDayNumber(Double rawValue) {
		super(rawValue);
	}

	@Override
	protected Double reviseRawValue(Double rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99.5) rawValue = 99.5;
		if (rawValue < 0.0) rawValue = 0.0;
		return super.reviseRawValue(rawValue);
	}
	
	/**
	 * 休暇付与数作成
	 * @return
	 */
	public LeaveGrantNumber toLeaveGrantNumber() {
		return LeaveGrantNumber.of(this, Optional.empty());
	}
	
	/**
	 * 休暇残数作成
	 * @return
	 */
	public LeaveRemainingNumber toLeaveRemainingNumber() {
		return LeaveRemainingNumber.of(new LeaveRemainingDayNumber(this.v()), Optional.empty());
	}
}