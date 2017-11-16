package nts.uk.ctx.at.record.dom.daily;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;

/**
 * 日別実績の法定外時間
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class ExcessOfStatutoryTimeOfDaily {
	@Setter
	private ExcessOfStatutoryMidNightTime ExcessOfStatutoryMidNightTime;
	private Optional<OverTimeWorkOfDaily> OverTimeWork;
	private Optional<HolidayWorkTimeOfDaily> WorkHolidayTime;

}
