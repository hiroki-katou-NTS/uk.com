package nts.uk.ctx.at.record.dom.monthly.vacation.dayoff.monthremaindata;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.record.dom.monthly.TimeDayoffRemain;
import nts.uk.ctx.at.record.dom.monthly.vacation.absenceleave.monthremaindata.RemainDataDaysMonth;
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffDayAndTimes extends DomainObject{
	/**	日数 */
	private RemainDataDaysMonth day;
	/**	時間 */
	private Optional<RemainDataTimesMonth> time;
}
