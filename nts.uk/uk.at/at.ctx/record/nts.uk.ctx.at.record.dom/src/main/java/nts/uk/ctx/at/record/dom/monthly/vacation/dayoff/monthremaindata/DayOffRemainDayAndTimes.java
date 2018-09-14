package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.AttendanceDaysMonthToTal;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffRemainDayAndTimes extends DomainObject{
	/**	残日数 */
	private AttendanceDaysMonthToTal days;
	/**残時間	 */
	private Optional<RemainingMinutes> times;
}
