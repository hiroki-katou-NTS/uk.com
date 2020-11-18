package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TimePlaceOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.TrackRecordAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampFrameNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
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
	
	public List<TimePlaceOutput> createTimePlace(int type) {
		List<TimePlaceOutput> list = new ArrayList<>();
//		介護時間帯
		if (type == 1) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(1), Optional.of(new TimeWithDayAttr(150)), Optional.of(new TimeWithDayAttr(450)));
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.empty(), Optional.empty());
			list.add(t1);
			list.add(t2);
		}
//		休憩時間帯
		if (type == 2) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(1), Optional.empty(), Optional.empty());
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.empty(), Optional.empty());
			TimePlaceOutput t3 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(3), Optional.empty(), Optional.empty());
			TimePlaceOutput t4 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(4), Optional.empty(), Optional.empty());
			TimePlaceOutput t5 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(5), Optional.empty(), Optional.empty());
			TimePlaceOutput t6 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(6), Optional.empty(), Optional.empty());
			TimePlaceOutput t7 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(7), Optional.empty(), Optional.empty());
			TimePlaceOutput t8 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(8), Optional.empty(), Optional.empty());
			TimePlaceOutput t9 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(9), Optional.empty(), Optional.empty());
			TimePlaceOutput t10 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(10), Optional.empty(), Optional.empty());
			list.add(t1);
			list.add(t2);
			list.add(t3);
			list.add(t4);
			list.add(t5);
			list.add(t6);
			list.add(t7);
			list.add(t8);
			list.add(t9);
			list.add(t10);
		}
//		勤務時間帯
		if (type == 3) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(1), Optional.of(new TimeWithDayAttr(450)), Optional.empty());
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.of(new TimeWithDayAttr(550)), Optional.of(new TimeWithDayAttr(450)));
			list.add(t1);
			list.add(t2);
		}
//		外出時間帯
		if (type == 4) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PRIVATE), new StampFrameNo(1), Optional.of(new TimeWithDayAttr(450)), Optional.of(new TimeWithDayAttr(100)));
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.COMPENSATION), new StampFrameNo(2), Optional.empty(), Optional.empty());
			TimePlaceOutput t3 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PUBLIC), new StampFrameNo(3), Optional.empty(), Optional.empty());
			TimePlaceOutput t4 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PRIVATE), new StampFrameNo(4), Optional.empty(), Optional.empty());
			TimePlaceOutput t5 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PUBLIC), new StampFrameNo(5), Optional.empty(), Optional.empty());
			TimePlaceOutput t6 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PUBLIC), new StampFrameNo(6), Optional.empty(), Optional.empty());
			TimePlaceOutput t7 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PRIVATE), new StampFrameNo(7), Optional.empty(), Optional.empty());
			TimePlaceOutput t8 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PUBLIC), new StampFrameNo(8), Optional.empty(), Optional.empty());
			TimePlaceOutput t9 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.PRIVATE), new StampFrameNo(9), Optional.empty(), Optional.empty());
			TimePlaceOutput t10 = new TimePlaceOutput(Optional.empty(), Optional.of(GoingOutReason.UNION), new StampFrameNo(10), Optional.empty(), Optional.empty());
			list.add(t1);
			list.add(t2);
			list.add(t3);
			list.add(t4);
			list.add(t5);
			list.add(t6);
			list.add(t7);
			list.add(t8);
			list.add(t9);
			list.add(t10);
		}
//		応援時間帯
		if (type == 5) {
			
		} 
//		育児時間帯
		if (type == 6) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(1), Optional.of(new TimeWithDayAttr(550)), Optional.of(new TimeWithDayAttr(100)));
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.of(new TimeWithDayAttr(800)), Optional.of(new TimeWithDayAttr(550)));
			list.add(t1);
			list.add(t2);
		}
//		 臨時時間帯
		if (type == 7) {
			TimePlaceOutput t1 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(1), Optional.of(new TimeWithDayAttr(550)), Optional.empty());
			TimePlaceOutput t2 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.empty(), Optional.empty());
			TimePlaceOutput t3 = new TimePlaceOutput(Optional.empty(), Optional.empty(), new StampFrameNo(2), Optional.empty(), Optional.empty());

			list.add(t1);
			list.add(t2);
			list.add(t3);
		}
		return list;
	}
	
	public StampRecordOutput createStampRecord() {
		
		/**
		 * 介護時間帯
		 */
		List<TimePlaceOutput> nursingTime = this.createTimePlace(1);
		
		/**
		 * 休憩時間帯
		 */
		List<TimePlaceOutput> breakTime = this.createTimePlace(2);
		
		/**
		 * 勤務時間帯
		 */
		List<TimePlaceOutput> workingTime = this.createTimePlace(3);
		
		/**
		 * 外出時間帯
		 */
		List<TimePlaceOutput> outingTime = this.createTimePlace(4);
		
		/**
		 * 応援時間帯
		 */
		List<TimePlaceOutput> supportTime;
		
		/**
		 * 育児時間帯
		 */
		List<TimePlaceOutput> parentingTime = this.createTimePlace(6);
		
		/**
		 * 臨時時間帯
		 */
		List<TimePlaceOutput> extraordinaryTime = this.createTimePlace(7);
		
		return new StampRecordOutput(
				nursingTime,
				breakTime,
				workingTime,
				outingTime,
				null,
				parentingTime,
				extraordinaryTime);
	}
	@Override
	public ActualContentDisplay getAchievement(String companyID, String applicantID, GeneralDate appDate) {
		//Imported(申請承認)「勤務実績」を取得する - (lấy thông tin Imported(appAproval)「DailyPerformance」) - RQ5
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfoRefactor(applicantID, appDate);
		String workTypeCD = Strings.EMPTY;
		String workTimeCD = Strings.EMPTY;
		List<BreakTimeSheet> breakTimeSheets = Collections.emptyList();
		TimeContentOutput timeContentOutput = null;
		TrackRecordAtr trackRecordAtr = null;
		StampRecordOutput stampRecordOutput = this.createStampRecord();
		List<ShortWorkingTimeSheet> shortWorkTimeLst = Collections.emptyList();
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
		if(recordWorkInfoImport.getWorkTypeCode()==null && recordWorkInfoImport.getWorkTimeCode()==null){
			//取得件数＝0件 ( số data lấy được  = 0)
			//スケジュールを管理するかチェックする(check có quản lý schedule hay không) - RQ536
			if(!scheduleManagementControlAdapter.isScheduleManagementAtr(applicantID, appDate)){//管理しない
				return new ActualContentDisplay(appDate, Optional.empty());
			}
			//管理する
			//Imported(申請承認)「勤務予定」を取得する(lấy thông tin imported(申請承認)「勤務予定」)
			ScBasicScheduleImport scBasicScheduleImport = scBasicScheduleAdapter.findByIDRefactor(applicantID, appDate);
			if(Strings.isBlank(scBasicScheduleImport.getWorkTypeCode()) && Strings.isBlank(scBasicScheduleImport.getWorkTimeCode().orElse(null))){
				//取得件数＝0件 ( số data lấy được  = 0)
				return new ActualContentDisplay(appDate, Optional.empty());
			}
			// 実績スケ区分＝スケジュール (Phân loại thực tế= Schedule)
			trackRecordAtr = TrackRecordAtr.SCHEDULE;
			//・実績詳細．1勤務種類コード＝OUTPUT．勤務予定．勤務種類コード
			workTypeCD = scBasicScheduleImport.getWorkTypeCode();
			//・実績詳細．3就業時間帯コード＝OUTPUT．勤務予定．勤務種類コード
			workTimeCD = scBasicScheduleImport.getWorkTimeCode().orElse(null);
			//・実績詳細．5出勤時刻＝OUTPUT．勤務予定．開始時刻1
			// check null to remove exception
			opWorkTime = scBasicScheduleImport.getScheduleStartClock1() == null ? Optional.empty() : Optional.of(scBasicScheduleImport.getScheduleStartClock1().v());
			//・実績詳細．6退勤時刻＝OUTPUT．勤務予定．終了時刻1
			// check null to remove exception
			opLeaveTime = scBasicScheduleImport.getScheduleEndClock1() == null ? Optional.empty() : Optional.of(scBasicScheduleImport.getScheduleEndClock1().v());
			//・実績詳細．9出勤時刻2＝OUTPUT．勤務予定．開始時刻2
			opWorkTime2 = scBasicScheduleImport.getScheduleStartClock2().map(x -> x.v());
			//・実績詳細．10退勤時刻2＝OUTPUT．勤務予定．終了時刻2
			opDepartureTime2 = scBasicScheduleImport.getScheduleEndClock2().map(x -> x.v());
			//・実績詳細．遅刻早退実績．予定出勤時刻1＝OUTPUT．勤務予定．開始時刻1
			//・実績詳細．遅刻早退実績．予定退勤時刻1＝OUTPUT．勤務予定．終了時刻1
			//・実績詳細．遅刻早退実績．予定出勤時刻2＝OUTPUT．勤務予定．開始時刻2
			//・実績詳細．遅刻早退実績．予定退勤時刻2＝OUTPUT．勤務予定．終了時刻2
			achievementEarly = new AchievementEarly(
					scBasicScheduleImport.getScheduleStartClock1(),
					scBasicScheduleImport.getScheduleStartClock2(),
					scBasicScheduleImport.getScheduleEndClock1(),
					scBasicScheduleImport.getScheduleEndClock2());
		} else {//取得件数＝1件(số data lấy được = 1)
			// 実績スケ区分＝日別実績 (Phân loại thực tế = Thực tế hàng ngày )
			trackRecordAtr = TrackRecordAtr.DAILY_RESULTS;
			//・実績詳細．1勤務種類コード＝OUTPUT．勤務実績．勤務種類コード
			workTypeCD = recordWorkInfoImport.getWorkTypeCode() == null ? null
					: recordWorkInfoImport.getWorkTypeCode().v();
			//・実績詳細．3就業時間帯コード＝OUTPUT．勤務実績．勤務種類コード
			workTimeCD = recordWorkInfoImport.getWorkTimeCode() == null ? null
					: recordWorkInfoImport.getWorkTimeCode().v();
			//・実績詳細．5出勤時刻＝OUTPUT．勤務実績．出勤時刻1
			opWorkTime = recordWorkInfoImport.getStartTime1() == null ? Optional.empty() 
					: recordWorkInfoImport.getStartTime1().map(x -> x.getTimeWithDay().map(y -> y.v())).orElse(Optional.empty());
			//・実績詳細．6退勤時刻＝OUTPUT．勤務実績．退勤時刻1
			opLeaveTime = recordWorkInfoImport.getEndTime1() == null ? Optional.empty()
					: recordWorkInfoImport.getEndTime1().map(x -> x.getTimeWithDay().map(y -> y.v())).orElse(Optional.empty());
			//・実績詳細．9出勤時刻2＝OUTPUT．勤務実績．出勤時刻2
			opWorkTime2 = recordWorkInfoImport.getStartTime2() == null ? Optional.empty()
					: recordWorkInfoImport.getStartTime2().map(x -> x.getTimeWithDay().map(y -> y.v())).orElse(Optional.empty());
			//・実績詳細．10退勤時刻2＝OUTPUT．勤務実績．退勤時刻2
			opDepartureTime2 = recordWorkInfoImport.getEndTime2() == null ? Optional.empty()
					: recordWorkInfoImport.getEndTime2().map(x -> x.getTimeWithDay().map(y -> y.v())).orElse(Optional.empty());
			//・実績詳細．遅刻早退実績．予定出勤時刻1＝OUTPUT．勤務実績．予定出勤時刻1
			//・実績詳細．遅刻早退実績．予定退勤時刻1＝OUTPUT．勤務実績．予定退勤時刻1
			//・実績詳細．遅刻早退実績．予定出勤時刻2＝OUTPUT．勤務実績．予定出勤時刻2
			//・実績詳細．遅刻早退実績．予定退勤時刻2＝OUTPUT．勤務実績．予定退勤時刻2
			achievementEarly = new AchievementEarly(
					recordWorkInfoImport.getScheduledAttendence1(),
					recordWorkInfoImport.getScheduledAttendence2(),
					recordWorkInfoImport.getScheduledDeparture1(),
					recordWorkInfoImport.getScheduledDeparture2());
			//・実績詳細．勤怠時間内容．早退時間＝OUTPUT．勤務実績．早退時間
			//・実績詳細．勤怠時間内容．遅刻時間＝OUTPUT．勤務実績．遅刻時間
			//・実績詳細．勤怠時間内容．早退時間2＝OUTPUT．勤務実績．早退時間2
			//・実績詳細．勤怠時間内容．遅刻時間2＝OUTPUT．勤務実績．遅刻時間2
			timeContentOutput = new TimeContentOutput(
					recordWorkInfoImport.getEarlyLeaveTime1(),
					recordWorkInfoImport.getEarlyLeaveTime2(),
					recordWorkInfoImport.getLateTime1(),
					recordWorkInfoImport.getLateTime2());
			//・実績詳細．勤怠時間内容．短時間勤務時間帯＝OUTPUT．勤務実績．短時間勤務時間帯
			shortWorkTimeLst = recordWorkInfoImport.getShortWorkingTimeSheets();
			//・実績詳細．休憩時間帯＝OUTPUT．勤務実績．休憩時間帯
			breakTimeSheets = recordWorkInfoImport.getBreakTimeSheets();
			//・実績詳細．残業深夜時間＝OUTPUT．勤務実績．残業深夜時間
			opOvertimeMidnightTime = recordWorkInfoImport.getOverTimeMidnight().map(x -> x.getCalcTime());
			//・実績詳細．法内休出深夜時間＝OUTPUT．勤務実績．法内休出深夜時間
			opInlawHolidayMidnightTime = recordWorkInfoImport.getMidnightOnHoliday().map(x -> x.getCalcTime());
			//・実績詳細．法外休出深夜時間＝OUTPUT．勤務実績．法外休出深夜時間
			opOutlawHolidayMidnightTime = recordWorkInfoImport.getOutOfMidnight().map(x -> x.getCalcTime());
			//・実績詳細．祝日休出深夜時間＝OUTPUT．勤務実績．祝日休出深夜時間
			opPublicHolidayMidnightTime = recordWorkInfoImport.getMidnightPublicHoliday().map(x -> x.getCalcTime());
			// 打刻実績．育児時間帯．時刻開始/時刻終了
			if(CollectionUtil.isEmpty(recordWorkInfoImport.getChildCareShortWorkingTimeList())) {
				stampRecordOutput.setParentingTime(Collections.emptyList());
			} else {
				stampRecordOutput.setParentingTime(recordWorkInfoImport.getChildCareShortWorkingTimeList().stream()
						.map(x -> new TimePlaceOutput(
								Optional.empty(), 
								Optional.empty(), 
								new StampFrameNo(x.getShortWorkTimeFrameNo().v()), 
								Optional.of(x.getEndTime()), 
								Optional.of(x.getStartTime())))
						.collect(Collectors.toList()));
			}
			// 打刻実績．介護時間帯．時刻開始/時刻終了
			if(CollectionUtil.isEmpty(recordWorkInfoImport.getCareShortWorkingTimeList())) {
				stampRecordOutput.setNursingTime(Collections.emptyList());
			} else {
				stampRecordOutput.setNursingTime(recordWorkInfoImport.getCareShortWorkingTimeList().stream()
						.map(x -> new TimePlaceOutput(
								Optional.empty(), 
								Optional.empty(), 
								new StampFrameNo(x.getShortWorkTimeFrameNo().v()), 
								Optional.of(x.getEndTime()), 
								Optional.of(x.getStartTime())))
						.collect(Collectors.toList()));
			}
			// 打刻実績．勤務時間帯
			Optional<TimeWithDayAttr> opWorkingTimeStartTime = recordWorkInfoImport.getStartTime1() == null ? Optional.empty() 
					: recordWorkInfoImport.getStartTime1().map(x -> x.getTimeWithDay()).orElse(Optional.empty());
			Optional<TimeWithDayAttr> opWorkingTimeEndTime = recordWorkInfoImport.getEndTime1() == null ? Optional.empty()
					: recordWorkInfoImport.getEndTime1().map(x -> x.getTimeWithDay()).orElse(Optional.empty());
			stampRecordOutput.setWorkingTime(Arrays.asList(new TimePlaceOutput(
					Optional.empty(), 
					Optional.empty(), 
					new StampFrameNo(1), 
					opWorkingTimeEndTime, 
					opWorkingTimeStartTime)));
			// 打刻実績．臨時時間帯
			if(CollectionUtil.isEmpty(recordWorkInfoImport.getTimeLeavingWorks())) {
				stampRecordOutput.setExtraordinaryTime(Collections.emptyList());
			} else {
				stampRecordOutput.setExtraordinaryTime(recordWorkInfoImport.getTimeLeavingWorks().stream()
						.map(x -> new TimePlaceOutput(
								Optional.empty(), 
								Optional.empty(), 
								new StampFrameNo(x.getWorkNo().v()), 
								x.getLeaveStamp().map(y -> y.getStamp().map(z -> z.getTimeDay().getTimeWithDay()).orElse(Optional.empty())).orElse(Optional.empty()), 
								x.getAttendanceStamp().map(y -> y.getStamp().map(z -> z.getTimeDay().getTimeWithDay()).orElse(Optional.empty())).orElse(Optional.empty())))
						.collect(Collectors.toList()));
			}
			// 打刻実績．外出時間帯
			if(CollectionUtil.isEmpty(recordWorkInfoImport.getOutHoursLst())) {
				stampRecordOutput.setOutingTime(Collections.emptyList());
			} else {
				stampRecordOutput.setOutingTime(recordWorkInfoImport.getOutHoursLst().stream()
						.map(x -> new TimePlaceOutput(
								Optional.empty(), 
								Optional.of(x.getReasonForGoOut()), 
								new StampFrameNo(x.getOutingFrameNo().v()), 
								x.getComeBack().map(y -> y.getStamp().map(z -> z.getTimeDay().getTimeWithDay()).orElse(Optional.empty())).orElse(Optional.empty()), 
								x.getGoOut().map(y -> y.getStamp().map(z -> z.getTimeDay().getTimeWithDay()).orElse(Optional.empty())).orElse(Optional.empty())))
						.collect(Collectors.toList()));
			}
			// 打刻実績．休憩時間帯
			if(CollectionUtil.isEmpty(recordWorkInfoImport.getBreakTimeSheets())) {
				stampRecordOutput.setBreakTime(Collections.emptyList());
			} else {
				stampRecordOutput.setBreakTime(recordWorkInfoImport.getBreakTimeSheets().stream()
						.map(x -> new TimePlaceOutput(
								Optional.empty(), 
								Optional.empty(), 
								new StampFrameNo(x.getBreakFrameNo().v()), 
								Optional.of(x.getEndTime()), 
								Optional.of(x.getStartTime())))
						.collect(Collectors.toList()));
			}
		}
		//ドメインモデル「勤務種類」を1件取得する - (lấy 1 dữ liệu của domain 「WorkType」)
		opWorkTypeName = workTypeRepository.findByPK(companyID, workTypeCD).map(x -> x.getName().v());
		//ドメインモデル「就業時間帯」を1件取得する - (lấy 1 dữ liệu của domain 「WorkTime」)
		opWorkTimeName = WorkTimeRepository.findByCode(companyID, workTimeCD).map(x -> x.getWorkTimeDisplayName().getWorkTimeName().v());
		AchievementDetail achievementDetail = new AchievementDetail(
				workTypeCD,
				workTimeCD,
				breakTimeSheets,
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
//			if(appType==ApplicationType.OVER_TIME_APPLICATION || appType==ApplicationType.HOLIDAY_WORK_APPLICATION) {
//				continue;
//			}
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
