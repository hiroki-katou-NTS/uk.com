package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

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
	private int scheAttendanceTime1;
	
	/**
	 * 予定出勤時刻2
	 */
	private int scheAttendanceTime2;
	
	/**
	 * 予定退勤時刻1
	 */
	private int scheDepartureTime1;
	
	/**
	 * 予定退勤時刻2
	 */
	private int scheDepartureTime2;
	
	public static AchievementEarlyDto fromDomain(AchievementEarly achievementEarly) {
		return new AchievementEarlyDto(
				achievementEarly.getScheAttendanceTime1().v(), 
				achievementEarly.getScheAttendanceTime2().v(), 
				achievementEarly.getScheDepartureTime1().v(), 
				achievementEarly.getScheDepartureTime2().v());
	}
	
	public AchievementEarly toDomain() {
		return new AchievementEarly(
				new TimeWithDayAttr(scheAttendanceTime1), 
				new TimeWithDayAttr(scheAttendanceTime2), 
				new TimeWithDayAttr(scheDepartureTime1), 
				new TimeWithDayAttr(scheDepartureTime2));
	}
}
