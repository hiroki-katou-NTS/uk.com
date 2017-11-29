package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 休出深夜
 * @author keisuke_hoshina
 *
 */
@Getter
@AllArgsConstructor
public class HolidayMidnightWork {
	private List<HolidayWorkMidNightTime> holidayWorkMidNightTime;
}
