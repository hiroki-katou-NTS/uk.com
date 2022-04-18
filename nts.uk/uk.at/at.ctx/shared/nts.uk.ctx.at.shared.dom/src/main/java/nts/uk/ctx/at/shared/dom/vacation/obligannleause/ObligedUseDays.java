package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 年休使用義務日数
 */
@Getter
@AllArgsConstructor
public class ObligedUseDays extends ValueObject {

	/** 年休使用義務日数 */
	private AnnualLeaveUsedDayNumber obligedUseDays;

	/**
	 * 使用日数が足りているか
	 * @param useDays
	 * @return
	 */
	public boolean enoughUseDays(AnnualLeaveUsedDayNumber useDays){
		return obligedUseDays.lessThanOrEqualTo(useDays.v());
	}
}
