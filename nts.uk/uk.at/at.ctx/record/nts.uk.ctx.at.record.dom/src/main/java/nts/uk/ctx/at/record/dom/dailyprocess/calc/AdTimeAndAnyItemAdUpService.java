package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;

/**
 * 日別実績の勤怠時間と任意項目を同時更新し、ストアドを実行するためのサービス
 * @author keisuke_hoshina
 *
 */
public interface AdTimeAndAnyItemAdUpService {
	public void addAndUpdate(AttendanceTimeOfDailyPerformance attendanceTime ,Optional<AnyItemValueOfDaily> anyItem);
}
