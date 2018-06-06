package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffRemainDayAndTimes extends DomainObject{
	/**	残日数 */
	private ReserveLeaveRemainingDayNumber days;
	/**残時間	 */
	private Optional<RemainingMinutes> times;
}
