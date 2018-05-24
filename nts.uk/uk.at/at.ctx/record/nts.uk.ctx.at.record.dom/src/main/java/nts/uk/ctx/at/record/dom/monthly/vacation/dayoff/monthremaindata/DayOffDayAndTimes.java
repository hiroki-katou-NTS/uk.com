package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.TimeDayoffRemain;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffDayAndTimes extends DomainObject{
	/**	日数 */
	private AttendanceDaysMonth day;
	/**	時間 */
	private Optional<TimeDayoffRemain> time;
}
