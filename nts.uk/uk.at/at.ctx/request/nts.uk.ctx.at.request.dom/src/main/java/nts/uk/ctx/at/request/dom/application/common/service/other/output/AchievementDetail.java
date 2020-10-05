package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeLeaveTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.実績内容の取得.実績詳細
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AchievementDetail {
	/**
	 * 1勤務種類コード
	 */
	@Setter
	private String workTypeCD;
	
	/**
	 * 3就業時間帯コード
	 */
	@Setter
	private String workTimeCD;
	
	/**
	 * 休憩時間帯
	 */
	private List<BreakTimeSheet> breakTimeSheets;
	
	/**
	 * 勤怠時間内容
	 */
	private TimeContentOutput timeContentOutput;
	
	/**
	 * 実績スケ区分
	 */
	private TrackRecordAtr trackRecordAtr;
	
	/**
	 * 打刻実績
	 */
	private StampRecordOutput stampRecordOutput;
	
	/**
	 * 短時間勤務時間帯
	 */
	private List<ShortWorkingTimeSheet> shortWorkTimeLst;
	
	/**
	 * 遅刻早退実績
	 */
	private AchievementEarly achievementEarly;
	
	/**
	 * 10退勤時刻2
	 */
	@Setter
	private Optional<Integer> opDepartureTime2;
	
	/**
	 * 2勤務種類名称
	 */
	@Setter
	private Optional<String> opWorkTypeName;
	
	/**
	 * 4就業時間帯名称
	 */
	@Setter
	private Optional<String> opWorkTimeName;
	
	/**
	 * 5出勤時刻
	 */
	@Setter
	private Optional<Integer> opWorkTime;
	
	/**
	 * 6退勤時刻
	 */
	@Setter
	private Optional<Integer> opLeaveTime;
	
	/**
	 * 8実績状態
	 */
	@Setter
	private Optional<Integer> opAchievementStatus;
	
	/**
	 * 9出勤時刻2
	 */
	@Setter
	private Optional<Integer> opWorkTime2;
	
	/**
	 * 残業深夜時間
	 */
	@Setter
	private Optional<AttendanceTime> opOvertimeMidnightTime;
	
	/**
	 * 法内休出深夜時間
	 */
	@Setter
	private Optional<AttendanceTime> opInlawHolidayMidnightTime;
	
	/**
	 * 法外休出深夜時間
	 */
	@Setter
	private Optional<AttendanceTime> opOutlawHolidayMidnightTime;
	
	/**
	 * 祝日休出深夜時間
	 */
	@Setter
	private Optional<AttendanceTime> opPublicHolidayMidnightTime;
	
	/**
	 * 7勤怠時間
	 */
	@Setter
	private Optional<List<OvertimeLeaveTime>> opOvertimeLeaveTimeLst;
	
}
