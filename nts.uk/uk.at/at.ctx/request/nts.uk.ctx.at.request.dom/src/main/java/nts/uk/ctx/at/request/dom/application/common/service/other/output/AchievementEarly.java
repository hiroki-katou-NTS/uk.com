package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.遅刻早退実績
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AchievementEarly {
	
	/**
	 * 予定出勤時刻1
	 */
	private Optional<TimeWithDayAttr> scheAttendanceTime1 = Optional.empty();
	
	/**
	 * 予定出勤時刻2
	 */
	private Optional<TimeWithDayAttr> scheAttendanceTime2 = Optional.empty();
	
	/**
	 * 予定退勤時刻1
	 */
	private Optional<TimeWithDayAttr> scheDepartureTime1 = Optional.empty();
	
	/**
	 * 予定退勤時刻2
	 */
	private Optional<TimeWithDayAttr> scheDepartureTime2 = Optional.empty();
	
}
