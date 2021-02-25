package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AchievementEarlyDto {
	/**
	 * 予定出勤時刻1
	 */
	private Integer scheAttendanceTime1;
	
	/**
	 * 予定出勤時刻2
	 */
	private Integer scheAttendanceTime2;
	
	/**
	 * 予定退勤時刻1
	 */
	private Integer scheDepartureTime1;
	
	/**
	 * 予定退勤時刻2
	 */
	private Integer scheDepartureTime2;
	
	public static AchievementEarlyDto fromDomain(AchievementEarly achievementEarly) {
		return new AchievementEarlyDto(
				achievementEarly.getScheAttendanceTime1().map(x -> x.v()).orElse(null),
				achievementEarly.getScheAttendanceTime2().map(x -> x.v()).orElse(null),
				achievementEarly.getScheDepartureTime1().map(x -> x.v()).orElse(null),
				achievementEarly.getScheDepartureTime2().map(x -> x.v()).orElse(null));
	}
	
	public AchievementEarly toDomain() {
		return new AchievementEarly(
				scheAttendanceTime1 == null ? Optional.empty() :  Optional.of(new TimeWithDayAttr(scheAttendanceTime1)),
				scheAttendanceTime2 == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(scheAttendanceTime2)), 
				scheDepartureTime1 == null ? Optional.empty() :  Optional.of(new TimeWithDayAttr(scheDepartureTime1)),
				scheDepartureTime2 == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(scheDepartureTime2)));
	}
}
