package nts.uk.ctx.at.record.dom.daily;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.holidaywork.HolidayWorkTimeOfDaily;

/**
 * 日別実績の法定外時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ExcessOfStatutoryTimeOfDaily {
	private OverTimeWorkOfDaily WorkHolidayTime;
	private HolidayWorkTimeOfDaily OverTimeWork;
	@Setter
	private ExcessOverTimeWorkMidNightTime ExcessOfStatutoryMidNightTime;
}
