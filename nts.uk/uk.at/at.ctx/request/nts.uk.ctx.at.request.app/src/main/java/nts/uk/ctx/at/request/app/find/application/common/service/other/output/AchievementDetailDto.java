package nts.uk.ctx.at.request.app.find.application.common.service.other.output;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeLeaveTime;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.shared.app.find.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheetDto;
import nts.uk.ctx.at.shared.app.find.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheetDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AchievementDetailDto {
	/**
	 * 1勤務種類コード
	 */
	private String workTypeCD;
	
	/**
	 * 3就業時間帯コード
	 */
	private String workTimeCD;
	
	/**
	 * 休憩時間帯
	 */
	private List<BreakTimeSheetDto> breakTimeSheets;
	
	/**
	 * 勤怠時間内容
	 */
	private TimeContentDto timeContentOutput;
	
	/**
	 * 実績スケ区分
	 */
	private int trackRecordAtr;
	
	/**
	 * 打刻実績
	 */
	private StampRecordDto stampRecordOutput;
	
	/**
	 * 短時間勤務時間帯
	 */
	private List<ShortWorkingTimeSheetDto> shortWorkTimeLst;
	
	/**
	 * 遅刻早退実績
	 */
	private AchievementEarlyDto achievementEarly;
	
	/**
	 * 10退勤時刻2
	 */
	@Setter
	private Integer opDepartureTime2;
	
	/**
	 * 2勤務種類名称
	 */
	@Setter
	private String opWorkTypeName;
	
	/**
	 * 4就業時間帯名称
	 */
	@Setter
	private String opWorkTimeName;
	
	/**
	 * 5出勤時刻
	 */
	@Setter
	private Integer opWorkTime;
	
	/**
	 * 6退勤時刻
	 */
	@Setter
	private Integer opLeaveTime;
	
	/**
	 * 8実績状態
	 */
	@Setter
	private Integer opAchievementStatus;
	
	/**
	 * 9出勤時刻2
	 */
	@Setter
	private Integer opWorkTime2;
	
	/**
	 * 残業深夜時間
	 */
	@Setter
	private Integer opOvertimeMidnightTime;
	
	/**
	 * 法内休出深夜時間
	 */
	@Setter
	private Integer opInlawHolidayMidnightTime;
	
	/**
	 * 法外休出深夜時間
	 */
	@Setter
	private Integer opOutlawHolidayMidnightTime;
	
	/**
	 * 祝日休出深夜時間
	 */
	@Setter
	private Integer opPublicHolidayMidnightTime;
	
	/**
	 * 7勤怠時間
	 */
	@Setter
	private List<OvertimeLeaveTime> opOvertimeLeaveTimeLst;
	
	public static AchievementDetailDto fromDomain(AchievementDetail achievementDetail) {
		return new AchievementDetailDto(
				achievementDetail.getWorkTypeCD(), 
				achievementDetail.getWorkTimeCD(), 
				CollectionUtil.isEmpty(achievementDetail.getBreakTimeSheets()) ? Collections.emptyList() : achievementDetail.getBreakTimeSheets().stream().map(x -> BreakTimeSheetDto.fromDomain(x)).collect(Collectors.toList()),
				TimeContentDto.fromDomain(achievementDetail.getTimeContentOutput()), 
				achievementDetail.getTrackRecordAtr().value, 
				StampRecordDto.fromDomain(achievementDetail.getStampRecordOutput()), 
				CollectionUtil.isEmpty(achievementDetail.getShortWorkTimeLst()) ? Collections.emptyList() : achievementDetail.getShortWorkTimeLst().stream().map(x -> ShortWorkingTimeSheetDto.fromDomain(x)).collect(Collectors.toList()), 
				AchievementEarlyDto.fromDomain(achievementDetail.getAchievementEarly()), 
				achievementDetail.getOpDepartureTime2().orElse(null), 
				achievementDetail.getOpWorkTypeName().orElse(null), 
				achievementDetail.getOpWorkTimeName().orElse(null), 
				achievementDetail.getOpWorkTime().orElse(null), 
				achievementDetail.getOpLeaveTime().orElse(null), 
				achievementDetail.getOpAchievementStatus().orElse(null), 
				achievementDetail.getOpWorkTime2().orElse(null), 
				achievementDetail.getOpOvertimeMidnightTime().map(x -> x.v()).orElse(null), 
				achievementDetail.getOpInlawHolidayMidnightTime().map(x -> x.v()).orElse(null), 
				achievementDetail.getOpOutlawHolidayMidnightTime().map(x -> x.v()).orElse(null), 
				achievementDetail.getOpPublicHolidayMidnightTime().map(x -> x.v()).orElse(null), 
				achievementDetail.getOpOvertimeLeaveTimeLst().orElse(null));
	}
	
	public AchievementDetail toDomain() {
		return new AchievementDetail(
				workTypeCD, 
				workTimeCD, 
				breakTimeSheets.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				timeContentOutput.toDomain(), 
				EnumAdaptor.valueOf(trackRecordAtr, TrackRecordAtr.class), 
				stampRecordOutput.toDomain(), 
				shortWorkTimeLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				achievementEarly.toDomain(), 
				Optional.ofNullable(opDepartureTime2), 
				Optional.ofNullable(opWorkTypeName), 
				Optional.ofNullable(opWorkTimeName), 
				Optional.ofNullable(opWorkTime), 
				Optional.ofNullable(opLeaveTime), 
				Optional.ofNullable(opAchievementStatus), 
				Optional.ofNullable(opWorkTime2), 
				opOvertimeMidnightTime == null ? Optional.empty() : Optional.of(new AttendanceTime(opOvertimeMidnightTime)), 
				opInlawHolidayMidnightTime == null ? Optional.empty() : Optional.of(new AttendanceTime(opInlawHolidayMidnightTime)), 
				opOutlawHolidayMidnightTime == null ? Optional.empty() : Optional.of(new AttendanceTime(opOutlawHolidayMidnightTime)), 
				opPublicHolidayMidnightTime == null ? Optional.empty() : Optional.of(new AttendanceTime(opPublicHolidayMidnightTime)), 
				CollectionUtil.isEmpty(opOvertimeLeaveTimeLst) ? Optional.empty() : Optional.of(opOvertimeLeaveTimeLst));
	}
}
