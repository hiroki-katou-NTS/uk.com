package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedulemanagementcontrol.ScheduleManagementControlAdapter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.OvertimeLeaveTime;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementDetail;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementEarly;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.StampRecordOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimeContentOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime.ShortWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectAchievementImpl implements CollectAchievement {
	
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private ScheduleManagementControlAdapter scheduleManagementControlAdapter;

	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository WorkTimeRepository;
	
	@Override
	public ActualContentDisplay getAchievement(String companyID, String applicantID, GeneralDate appDate) {
		//Imported(申請承認)「勤務実績」を取得する - (lấy thông tin Imported(appAproval)「DailyPerformance」) - RQ5
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(applicantID, appDate);
		String workTypeCD = Strings.EMPTY;
		String workTimeCD = Strings.EMPTY;
		TimeContentOutput timeContentOutput = null;
		TrackRecordAtr trackRecordAtr = null;
		StampRecordOutput stampRecordOutput = null;
		List<ShortWorkTime> shortWorkTimeLst = Collections.emptyList();
		AchievementEarly achievementEarly = null;
		Optional<Integer> opDepartureTime2 = Optional.empty();
		Optional<String> opWorkTypeName = Optional.empty();
		Optional<String> opWorkTimeName = Optional.empty();
		Optional<Integer> opWorkTime = Optional.empty();
		Optional<Integer> opLeaveTime = Optional.empty();
		Optional<Integer> opAchievementStatus = Optional.empty();
		Optional<Integer> opWorkTime2 = Optional.empty();
		Optional<AttendanceTime> opOvertimeMidnightTime = Optional.empty();
		Optional<AttendanceTime> opInlawHolidayMidnightTime = Optional.empty();
		Optional<AttendanceTime> opOutlawHolidayMidnightTime = Optional.empty();
		Optional<AttendanceTime> opPublicHolidayMidnightTime = Optional.empty();
		Optional<List<OvertimeLeaveTime>> opOvertimeLeaveTimeLst = Optional.empty();
		if(Strings.isBlank(recordWorkInfoImport.getWorkTypeCode()) && Strings.isBlank(recordWorkInfoImport.getWorkTimeCode())){
			//取得件数＝0件 ( số data lấy được  = 0)
			//スケジュールを管理するかチェックする(check có quản lý schedule hay không) - RQ536
			if(!scheduleManagementControlAdapter.isScheduleManagementAtr(applicantID, appDate)){//管理しない
				return new ActualContentDisplay(appDate, Optional.empty());
			}
			//管理する
			//Imported(申請承認)「勤務予定」を取得する(lấy thông tin imported(申請承認)「勤務予定」)
			Optional<ScBasicScheduleImport> opScBasicScheduleImport = scBasicScheduleAdapter.findByID(applicantID, appDate);
			if(!opScBasicScheduleImport.isPresent()){//取得件数＝0件 ( số data lấy được  = 0)
				return new ActualContentDisplay(appDate, Optional.empty());
			}
			//取得件数＝1件(số data lấy được = 1)
			ScBasicScheduleImport scBasicScheduleImport = opScBasicScheduleImport.get();
			// 実績スケ区分＝スケジュール (Phân loại thực tế= Schedule)
			trackRecordAtr = TrackRecordAtr.SCHEDULE;
			//・実績詳細．1勤務種類コード＝OUTPUT．勤務予定．勤務種類コード
			workTypeCD = scBasicScheduleImport.getWorkTypeCode();
			//・実績詳細．3就業時間帯コード＝OUTPUT．勤務予定．勤務種類コード
			workTimeCD = scBasicScheduleImport.getWorkTimeCode();
			//・実績詳細．5出勤時刻＝OUTPUT．勤務予定．開始時刻1
			opWorkTime = scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 1)
					.findAny().map(x -> x.getScheduleStartClock());
			//・実績詳細．6退勤時刻＝OUTPUT．勤務予定．終了時刻1
			opLeaveTime = scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 1)
					.findAny().map(x -> x.getScheduleEndClock());
			//・実績詳細．9出勤時刻2＝OUTPUT．勤務予定．開始時刻2
			opWorkTime2 = scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 2)
					.findAny().map(x -> x.getScheduleStartClock());
			//・実績詳細．10退勤時刻2＝OUTPUT．勤務予定．終了時刻2
			opDepartureTime2 = scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 2)
					.findAny().map(x -> x.getScheduleEndClock());
			//・実績詳細．遅刻早退実績．予定出勤時刻1＝OUTPUT．勤務予定．開始時刻1
			//・実績詳細．遅刻早退実績．予定退勤時刻1＝OUTPUT．勤務予定．終了時刻1
			//・実績詳細．遅刻早退実績．予定出勤時刻2＝OUTPUT．勤務予定．開始時刻2
			//・実績詳細．遅刻早退実績．予定退勤時刻2＝OUTPUT．勤務予定．終了時刻2
			achievementEarly = new AchievementEarly(
					scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 1)
					.findAny().map(x -> new TimeWithDayAttr(x.getScheduleStartClock())).orElse(null), 
					scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 2)
					.findAny().map(x -> new TimeWithDayAttr(x.getScheduleStartClock())).orElse(null), 
					scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 1)
					.findAny().map(x -> new TimeWithDayAttr(x.getScheduleEndClock())).orElse(null), 
					scBasicScheduleImport.getWorkScheduleTimeZones().stream().filter(x -> x.getScheduleCnt() == 2)
					.findAny().map(x -> new TimeWithDayAttr(x.getScheduleEndClock())).orElse(null));
		} else {//取得件数＝1件(số data lấy được = 1)
			// 実績スケ区分＝日別実績 (Phân loại thực tế = Thực tế hàng ngày )
			trackRecordAtr = TrackRecordAtr.DAILY_RESULTS;
			//・実績詳細．1勤務種類コード＝OUTPUT．勤務実績．勤務種類コード
			workTypeCD = recordWorkInfoImport.getWorkTypeCode();
			//・実績詳細．3就業時間帯コード＝OUTPUT．勤務実績．勤務種類コード
			workTimeCD = recordWorkInfoImport.getWorkTimeCode();
			//・実績詳細．5出勤時刻＝OUTPUT．勤務実績．出勤時刻1
			
			//・実績詳細．6退勤時刻＝OUTPUT．勤務実績．退勤時刻1
			
			//・実績詳細．9出勤時刻2＝OUTPUT．勤務実績．出勤時刻2
			
			//・実績詳細．10退勤時刻2＝OUTPUT．勤務実績．退勤時刻2
			
			//・実績詳細．遅刻早退実績．予定出勤時刻1＝OUTPUT．勤務実績．予定出勤時刻1
			//・実績詳細．遅刻早退実績．予定退勤時刻1＝OUTPUT．勤務実績．予定退勤時刻1
			//・実績詳細．遅刻早退実績．予定出勤時刻2＝OUTPUT．勤務実績．予定出勤時刻2
			//・実績詳細．遅刻早退実績．予定退勤時刻2＝OUTPUT．勤務実績．予定退勤時刻2
			
			//・実績詳細．勤怠時間内容．早退時間＝OUTPUT．勤務実績．早退時間
			//・実績詳細．勤怠時間内容．遅刻時間＝OUTPUT．勤務実績．遅刻時間
			//・実績詳細．勤怠時間内容．早退時間2＝OUTPUT．勤務実績．早退時間2
			//・実績詳細．勤怠時間内容．遅刻時間2＝OUTPUT．勤務実績．遅刻時間2
			timeContentOutput = new TimeContentOutput(
					new AttendanceTime(recordWorkInfoImport.getLeaveEarlyTime1()), 
					new AttendanceTime(recordWorkInfoImport.getLeaveEarlyTime2()), 
					new AttendanceTime(recordWorkInfoImport.getLateTime1()), 
					new AttendanceTime(recordWorkInfoImport.getLateTime2()));
			//・実績詳細．勤怠時間内容．短時間勤務時間帯＝OUTPUT．勤務実績．短時間勤務時間帯
			
			//・実績詳細．休憩時間帯＝OUTPUT．勤務実績．休憩時間帯
			
			//・実績詳細．残業深夜時間＝OUTPUT．勤務実績．残業深夜時間
			
			//・実績詳細．法内休出深夜時間＝OUTPUT．勤務実績．法内休出深夜時間
			
			//・実績詳細．法外休出深夜時間＝OUTPUT．勤務実績．法外休出深夜時間
			
			//・実績詳細．祝日休出深夜時間＝OUTPUT．勤務実績．祝日休出深夜時間
			
		}
		//ドメインモデル「勤務種類」を1件取得する - (lấy 1 dữ liệu của domain 「WorkType」)
		opWorkTypeName = workTypeRepository.findByPK(companyID, workTypeCD).map(x -> x.getName().v());
		//ドメインモデル「就業時間帯」を1件取得する - (lấy 1 dữ liệu của domain 「WorkTime」)
		opWorkTimeName = WorkTimeRepository.findByCode(companyID, workTimeCD).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v());
		AchievementDetail achievementDetail = new AchievementDetail(
				workTypeCD, 
				workTimeCD, 
				timeContentOutput, 
				trackRecordAtr, 
				stampRecordOutput, 
				shortWorkTimeLst, 
				achievementEarly, 
				opDepartureTime2, 
				opWorkTypeName, 
				opWorkTimeName, 
				opWorkTime, 
				opLeaveTime, 
				opAchievementStatus, 
				opWorkTime2, 
				opOvertimeMidnightTime, 
				opInlawHolidayMidnightTime, 
				opOutlawHolidayMidnightTime, 
				opPublicHolidayMidnightTime, 
				opOvertimeLeaveTimeLst);
		return new ActualContentDisplay(appDate, Optional.of(achievementDetail));
	}

	@Override
	public List<ActualContentDisplay> getAchievementContents(String companyID, String employeeID,
			List<GeneralDate> dateLst, ApplicationType appType) {
		List<ActualContentDisplay> result = new ArrayList<>();
		// INPUT．申請対象日リストをチェックする
		if(CollectionUtil.isEmpty(dateLst)) {
			return Collections.emptyList();
		}
		// INPUT．申請対象日リストを先頭から最後へループする
		for(GeneralDate loopDate : dateLst) {
			// INPUT．申請種類をチェックする
			if(appType==ApplicationType.OVER_TIME_APPLICATION || appType==ApplicationType.LEAVE_TIME_APPLICATION) {
				continue;
			}
			// 実績の取得
			ActualContentDisplay actualContentDisplay = this.getAchievement(companyID, employeeID, loopDate);
			// 取得した実績をOutput「表示する実績内容」に追加する
			result.add(actualContentDisplay);
		}
		return result;
	}

	@Override
	public List<PreAppContentDisplay> getPreAppContents(String companyID, String employeeID, List<GeneralDate> dateLst,
			ApplicationType appType) {
		List<PreAppContentDisplay> result = new ArrayList<>();
		// INPUT．申請対象日リストをチェックする
		if(CollectionUtil.isEmpty(dateLst)) {
			return Collections.emptyList();
		}
		// INPUT．申請対象日リストを先頭から最後へループする
		for(GeneralDate loopDate : dateLst) {
			// ドメインモデル「申請」を取得
			if(appType == ApplicationType.ABSENCE_APPLICATION) {
				// AppAbsence appAbsence = appAbsenceRepository.getAbsenceById(companyID, "").get();
				// result.add(appAbsence);
			}
		}
		return result;
	}

}
