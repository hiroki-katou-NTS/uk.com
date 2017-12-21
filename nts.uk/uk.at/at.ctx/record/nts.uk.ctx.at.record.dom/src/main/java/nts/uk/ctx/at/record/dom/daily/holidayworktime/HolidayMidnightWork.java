package nts.uk.ctx.at.record.dom.daily.holidayworktime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;

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
