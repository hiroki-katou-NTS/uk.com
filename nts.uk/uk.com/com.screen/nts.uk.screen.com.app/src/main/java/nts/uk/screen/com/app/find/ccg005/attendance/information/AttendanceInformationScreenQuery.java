package nts.uk.screen.com.app.find.ccg005.attendance.information;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.bs.person.dom.person.personal.avatar.AvatarRepository;
import nts.uk.ctx.bs.person.dom.person.personal.avatar.UserAvatar;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiStateRepository;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformation;
import nts.uk.ctx.office.dom.goout.GoOutEmployeeInformationRepository;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.ctx.office.dom.status.ActivityStatusRepository;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentService;
import nts.uk.ctx.office.dom.status.service.AttendanceStatusJudgmentServiceRequireImpl;
import nts.uk.query.pub.ccg005.comment.CommentQueryExport;
import nts.uk.query.pub.ccg005.comment.CommentQueryPub;
import nts.uk.query.pub.ccg005.work.information.WorkInformationQueryPub;
import nts.uk.query.pub.ccg005.work.information.dto.EmployeeDailyPerErrorDto;
import nts.uk.query.pub.ccg005.work.information.EmployeeWorkInformationExport;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.ApplicationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.AttendanceDetailDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.EmployeeCommentInformationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.EmployeeEmojiStateDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.dto.UserAvatarDto;
import nts.uk.screen.com.app.find.ccg005.goout.GoOutEmployeeInformationDto;
import nts.uk.shr.com.i18n.TextResource;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.在席情報を取得.在席情報を取得
 */
public class AttendanceInformationScreenQuery {

//	private WorkInformationQuery workInforQuery;

	@Inject
	private ApplicationRepository appRepo;

	@Inject
	private CommentQueryPub commentQueryQub;

	@Inject
	private GoOutEmployeeInformationRepository goOutRepo;

	@Inject
	private ActivityStatusRepository statusRepo;

	@Inject
	private EmployeeEmojiStateRepository emojiRepo;

	@Inject
	private AvatarRepository avatarRepo;

	@Inject
	private WorkInformationQueryPub workInfoQueryPub;

	private List<EmployeeEmojiState> emojiList = Collections.emptyList();

	public List<AttendanceInformationDto> getAttendanceInformation(List<String> sids, List<String> pids,
			GeneralDate baseDate, boolean emojiUsage) {

		// 1: 出退勤・申請情報を取得( 社員ID, 年月日): List<出退勤・申請情報>
		List<EmployeeWorkInformationExport> workInfoList = workInfoQueryPub.getWorkInformationQuery(sids, baseDate);

		// 2: 在席のステータスの判断(Require, 社員ID, 年月日, 日別実績の勤務情報, 日別実績の出退勤, 勤務種類):
		AttendanceStatusJudgmentServiceRequireImpl rq = new AttendanceStatusJudgmentServiceRequireImpl(
				goOutRepo,
				statusRepo);

		// 3: 申請情報を取得する(社員IDリスト, 期間, 反映状態リスト): Map<社員ID、List<申請>>
		List<Integer> listReflecInfor = new ArrayList<>();
		listReflecInfor.add(ReflectedState.REFLECTED.value);
		listReflecInfor.add(ReflectedState.REMAND.value);
		listReflecInfor.add(ReflectedState.DENIAL.value);
		Map<String, List<Application>> mapListApplication = appRepo.getMapListApplicationNew(sids,
				new DatePeriod(baseDate, baseDate), listReflecInfor);

		// 4: コメントを取得する(社員ID, 年月日): Map<社員ID、社員のコメント情報>
		Map<String, CommentQueryExport> commentData = commentQueryQub.getComment(sids, baseDate);

		// 5: get(社員IDリスト、年月日): List<社員の外出情報>
		List<GoOutEmployeeInformation> goOutList = goOutRepo.getByListSidAndDate(sids, baseDate);

		// 6: [感情状態を利用する＝する]: get(社員IDリスト、年月日): List<社員の感情状態>
		if (emojiUsage) {
			emojiList = emojiRepo.getByListSidAndDate(sids, baseDate);
		}

		// 7: get(個人IDリスト): List<個人の顔写真>
		List<UserAvatar> avatarList = avatarRepo.getAvatarByPersonalIds(pids);

		// 8: create()
		return sids.stream().map(sid -> {
			// ApplicationDto
			List<Application> applications = mapListApplication.get(sid);
			List<ApplicationDto> applicationDtos = applications.stream().map(item -> ApplicationDto.toDto(item))
					.collect(Collectors.toList());

			// AttendanceDetailDto
			Optional<EmployeeWorkInformationExport> workInformation = workInfoList.stream()
					.filter(wi -> wi.getSid().equalsIgnoreCase(sid)).findFirst();
			AttendanceDetailDto attendanceDetailDto = AttendanceDetailDto.builder().build();
			workInformation.ifPresent(workInfo -> {
				// １．勤務区分
				Integer workDivision = null;
				List<Integer> notIn = new ArrayList<>();
				notIn.add(WorkTypeClassification.Attendance.value);
				notIn.add(WorkTypeClassification.HolidayWork.value);
				notIn.add(WorkTypeClassification.Shooting.value);
				notIn.add(WorkTypeClassification.ContinuousWork.value);

				if (workInfo.getWorkTypeDto() == null) {
					workDivision = null;
				} else if (workInfo.getWorkTypeDto().getDailyWork().getOneDay() != null
						&& !notIn.contains(workInfo.getWorkTypeDto().getDailyWork().getOneDay())) {
					workDivision = WorkDivision.HOLIDAY.value;
				} else {
					workDivision = WorkDivision.WORK.value;
				}

				// 2.勤務色
				Integer workColor = DisplayColor.ACHIEVEMENT.value;
				if (baseDate.after(GeneralDate.today())) {
					workColor = DisplayColor.SCHEDULED.value;
				}

				// ３．勤務名
				String workName = "";
				if (workDivision == null) {
					workName = "";
				} else if (workDivision == WorkDivision.HOLIDAY.value) {
					workName = TextResource.localize("CCG005_24");
				} else {
					workName = workInfo.getWorkTypeDto().getDisplayName();
				}

				// 4. 開始時刻 AND 開始の色
				String checkInTime;
				Integer checkInColor;
				Integer actualAttendance = workInfo.getTimeLeavingOfDailyPerformanceDto() != null
						? workInfo.getTimeLeavingOfDailyPerformanceDto().getAttendanceTime()
						: null;
				boolean actualStraight = workInfo.getWorkPerformanceDto() != null
						? workInfo.getWorkPerformanceDto().getGoStraightAtr() == 1
						: false;
				Integer futureAttendance = workInfo.getWorkScheduleDto() != null
						? workInfo.getWorkScheduleDto().getAttendanceTime()
						: null;
				boolean futureStraight = workInfo.getWorkScheduleDto() != null
						? workInfo.getWorkScheduleDto().getGoStraightAtr() == 1
						: false;

				if (actualAttendance != null) {
					checkInTime = actualStraight ? TextResource.localize("CCG005_25") + actualAttendance
							: String.valueOf(actualAttendance);
					checkInColor = DisplayColor.ACHIEVEMENT.value;
				} else {
					checkInTime = futureStraight ? TextResource.localize("CCG005_25") + futureAttendance
							: String.valueOf(futureAttendance);
					checkInColor = DisplayColor.SCHEDULED.value;
				}

				// ５．終了時刻 AND 終了の色
				String checkOutTime;
				Integer checkOutColor;
				Integer actualLeave = workInfo.getTimeLeavingOfDailyPerformanceDto() != null
						? workInfo.getTimeLeavingOfDailyPerformanceDto().getLeaveTime()
						: null;
				boolean actualLeaveStraight = workInfo.getWorkPerformanceDto() != null
						? workInfo.getWorkPerformanceDto().getBackStraightAtr() == 1
						: false;
				Integer futureLeave = workInfo.getWorkScheduleDto() != null
						? workInfo.getWorkScheduleDto().getLeaveTime()
						: null;
				boolean futureLeaveStraight = workInfo.getWorkScheduleDto() != null
						? workInfo.getWorkScheduleDto().getBackStraightAtr() == 1
						: false;

				if (actualLeave != null) {
					checkOutTime = actualLeaveStraight ? TextResource.localize("CCG005_25") + actualLeave
							: String.valueOf(actualLeave);
					checkOutColor = DisplayColor.ACHIEVEMENT.value;
				} else {
					checkOutTime = futureLeaveStraight ? TextResource.localize("CCG005_25") + futureLeave
							: String.valueOf(futureLeave);
					checkOutColor = DisplayColor.SCHEDULED.value;
				}

				// ６．アラーム色
				List<EmployeeDailyPerErrorDto> list007 = workInfo.getEmployeeDailyPerErrorDtos().stream()
						.filter(i -> i.getErrorAlarmWorkRecordCode().equalsIgnoreCase("007")).collect(Collectors.toList());
				List<EmployeeDailyPerErrorDto> list008 = workInfo.getEmployeeDailyPerErrorDtos().stream()
						.filter(i -> i.getErrorAlarmWorkRecordCode().equalsIgnoreCase("008")).collect(Collectors.toList());
				if (workInfo.getEmployeeDailyPerErrorDtos().size() > 0 && list007.size() > 0) {
					checkInColor = DisplayColor.ALARM.value;
				}
				if (workInfo.getEmployeeDailyPerErrorDtos().size() > 0 && list008.size() > 0) {
					checkOutColor = DisplayColor.ALARM.value;
				}
				attendanceDetailDto.setWorkColor(workColor);
				attendanceDetailDto.setWorkColor(workColor);
				attendanceDetailDto.setWorkName(workName);
				attendanceDetailDto.setCheckOutColor(checkOutColor);
				attendanceDetailDto.setCheckOutTime(checkOutTime);
				attendanceDetailDto.setCheckInColor(checkInColor);
				attendanceDetailDto.setCheckInTime(checkInTime);
				attendanceDetailDto.setWorkDivision(workDivision);
			});
			// UserAvatarDto TODO
			Optional<UserAvatar> avatarDomain = avatarList.stream().filter(ava -> ava.getPersonalId() == sid).findAny();
			UserAvatarDto avatarDto = UserAvatarDto.builder().build();
			
			// TODO 2: 在席のステータスの判断(Require, 社員ID, 年月日, 日別実績の勤務情報, 日別実績の出退勤, 勤務種類):
			Optional<ActivityStatus> activityStatus = AttendanceStatusJudgmentService.getActivityStatus(rq, sid, baseDate);
			ActivityStatusDto activityStatusDto = ActivityStatusDto.builder().build();
			activityStatus.ifPresent(domain -> domain.setMemento(activityStatusDto));
			
			// commentDto
			CommentQueryExport commentExp = commentData.get(sid);
			EmployeeCommentInformationDto commentDto = EmployeeCommentInformationDto.builder()
					.comment(commentExp.getComment()).date(commentExp.getDate()).sid(commentExp.getSid()).build();

			// goOutDto
			Optional<GoOutEmployeeInformation> goOutDomain = goOutList.stream().filter(goOut -> goOut.getSid() == sid)
					.findAny();
			GoOutEmployeeInformationDto goOutDto = GoOutEmployeeInformationDto.builder().build();
			goOutDomain.ifPresent(item -> item.setMemento(goOutDto));

			// emojiDto
			Optional<EmployeeEmojiState> emojiDomain = emojiList.stream().filter(emoji -> emoji.getSid() == sid)
					.findAny();
			EmployeeEmojiStateDto emojiDto = EmployeeEmojiStateDto.builder().build();
			emojiDomain.ifPresent(consumer -> consumer.setMemento(emojiDto));

			return AttendanceInformationDto.builder()
					.applicationDtos(applicationDtos)
					.sid(sid)
					.attendanceDetailDto(attendanceDetailDto)
					.avatarDto(avatarDto)
					.activityStatusDto(activityStatusDto)
					.commentDto(commentDto)
					.goOutDto(goOutDto)
					.emojiDto(emojiDto)
					.build();
		}).collect(Collectors.toList());
	}

	// 勤務区分
	private enum WorkDivision {
		// 出勤
		WORK(0),

		// 休み
		HOLIDAY(1);

		public final int value;

		WorkDivision(int val) {
			this.value = val;
		}
	}

	// 表示色区分
	private enum DisplayColor {
		// 実績色
		ACHIEVEMENT(0),

		// 予定色
		SCHEDULED(1),

		// アラーム色
		ALARM(2);

		public final int value;

		DisplayColor(int val) {
			this.value = val;
		}
	}
}
