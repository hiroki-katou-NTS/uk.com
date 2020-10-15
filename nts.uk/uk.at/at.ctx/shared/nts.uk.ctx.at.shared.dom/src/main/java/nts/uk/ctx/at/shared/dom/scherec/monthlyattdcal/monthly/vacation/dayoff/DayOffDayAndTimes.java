package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.dayoff;


import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.absenceleave.RemainDataDaysMonth;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayOffDayAndTimes extends DomainObject{
	/**	日数 */
	private RemainDataDaysMonth day;
	/**	時間 */
	private Optional<RemainDataTimesMonth> time;
}
