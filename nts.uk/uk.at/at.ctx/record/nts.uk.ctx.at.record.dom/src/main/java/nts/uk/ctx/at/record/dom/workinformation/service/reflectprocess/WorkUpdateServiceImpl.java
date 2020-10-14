package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.repository.BreakTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerform;
import nts.uk.ctx.at.record.dom.daily.remarks.RemarksOfDailyPerformRepo;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime.BreakTimeParam;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.OverTimeRecordAtr;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayMidnightWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RecordRemarks;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.remarks.RemarksOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.HolidayWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.worktime.algorithm.getcommonset.GetCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class WorkUpdateServiceImpl implements WorkUpdateService{
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	@Inject
	private RemarksOfDailyPerformRepo remarksOfDailyRepo;
	@Inject
	private WorkTimeSettingService workTimeSetting;
	@Inject
	private BreakTimeOfDailyPerformanceRepository breakTimeOfDailyRepo;
	@Inject
	private CompensLeaveComSetRepository comSetRepo;
	@Inject
	private WorkTypeRepository worktypeRepo;
	@Inject 
	private RecordDomRequireService requireService;
	@Override
	public void updateWorkTimeType(ReflectParameter para, boolean scheUpdate, IntegrationOfDaily dailyInfo) {
		WorkInformation workInfor = new WorkInformation(para.getWorkTypeCode(), para.getWorkTimeCode());
		List<Integer> lstItem = new ArrayList<>();
		if(scheUpdate) {
			if(para.isWorkChange()) {
				this.dailyInfo(para.getWorkTimeCode(), para.getWorkTypeCode(),  dailyInfo.getWorkInformation());
			}
			dailyInfo.getWorkInformation().setScheduleInfo(workInfor);
			
			lstItem.add(2);	
			lstItem.add(1);
		} else {
			if(para.getWorkTimeCode() == null) {
				this.updateTimeNotReflect(para.getEmployeeId(), para.getDateData());
			}
			lstItem.add(29);	
			lstItem.add(28);
			dailyInfo.getWorkInformation().setRecordInfo(workInfor);
		}
		
		//日別実績の編集状態		
		this.editStateOfDailyPerformance(para.getEmployeeId(), para.getDateData(), dailyInfo.getEditState(), lstItem);
	}
	/**
	 * 申請の時刻がなくて実績の勤務種類区分が休日の場合
	 * 時刻反映する前に予定時間帯を追加しないはいけない
	 * @return
	 */
	private void dailyInfo(String workTimeCode, String workTypeCode, WorkInfoOfDailyAttendance dailyInfo) {
		String companyId = AppContexts.user().companyId();
		List<ScheduleTimeSheet> scheduleTimeSheets = new ArrayList<>();
		if(workTimeCode != null) {				
			PredetermineTimeSetForCalc predetermine = workTimeSetting.getPredeterminedTimezone(companyId, workTimeCode, workTypeCode, 1);
			List<TimezoneUse> lstTimezone = predetermine.getTimezones();
			
			lstTimezone.stream().forEach(x -> {
				ScheduleTimeSheet scheIn = new ScheduleTimeSheet(x.getWorkNo(), x.getStart().v(), x.getEnd().v());
				scheduleTimeSheets.add(scheIn);				
			});	
		}
		dailyInfo.setScheduleTimeSheets(scheduleTimeSheets);
	}
	/**
	 * 日別実績の編集状態
	 * @param employeeId
	 * @param dateData
	 * @param lstItem
	 */
	@Override
	public void editStateOfDailyPerformance(String sid, GeneralDate ymd, 
			List<EditStateOfDailyAttd> lstEditState, List<Integer> lstItem) {		
		lstItem.stream().forEach(z -> {
			List<EditStateOfDailyAttd> optItemData = lstEditState.stream().filter(a -> a.getAttendanceItemId() == z)
					.collect(Collectors.toList());
			if(!optItemData.isEmpty()) {
				EditStateOfDailyAttd itemData = optItemData.get(0);
				itemData.setEditStateSetting(EditStateSetting.REFLECT_APPLICATION);
			}else {
				EditStateOfDailyPerformance insertData = new EditStateOfDailyPerformance(sid, z, ymd, EditStateSetting.REFLECT_APPLICATION);
				lstEditState.add(insertData.getEditState());
			}
		});
	}
	
	@Override
	public void updateScheStartEndTime(TimeReflectPara para, IntegrationOfDaily dailyPerfor) {
		if(!para.isStart()
				&& !para.isEnd()) {
			return;
		}
		ScheduleTimeSheet timeSheet;
		if(dailyPerfor.getWorkInformation().getScheduleTimeSheets().isEmpty()) {
			timeSheet = new ScheduleTimeSheet(1, 
					para.isStart() && para.getStartTime() != null ? para.getStartTime(): 0,
							para.isEnd() && para.getEndTime() != null ? para.getEndTime() : 0);
			dailyPerfor.getWorkInformation().setScheduleTimeSheets(Arrays.asList(timeSheet));
		} else {
			List<ScheduleTimeSheet> lstTimeSheetFrameNo = dailyPerfor.getWorkInformation().getScheduleTimeSheets().stream()					
					.filter(x -> x.getWorkNo().v() == para.getFrameNo()).collect(Collectors.toList());
			if(lstTimeSheetFrameNo.isEmpty()) {
				timeSheet = new ScheduleTimeSheet(para.getFrameNo(), 
						para.isStart() ? para.getStartTime() == null ? 0 : para.getStartTime() : 0,
						para.isEnd() ? para.getEndTime() == null ? 0 : para.getEndTime() : 0);
				dailyPerfor.getWorkInformation().getScheduleTimeSheets().add(timeSheet);
			} else {
				dailyPerfor.getWorkInformation().getScheduleTimeSheets().stream().forEach(x -> {
					if(x.getWorkNo().v() == para.getFrameNo()) {
						if(para.isStart()) {
							x.setAttendance(new TimeWithDayAttr(para.getStartTime() == null ? 0 : para.getStartTime()));
						}
						if(para.isEnd()) {
							x.setLeaveWork(new TimeWithDayAttr(para.getEndTime() == null ? 0 : para.getEndTime()));
						}
					}
				});
			}
		}
		//日別実績の編集状態
		//予定開始時刻の項目ID
		List<Integer> lstItem = new ArrayList<Integer>();
		if(para.getFrameNo() == 1) {
			if(para.isStart()) {
				lstItem.add(3);	
			}
			if(para.isEnd()) {
				lstItem.add(4);	
			}
		} else {
			if(para.isStart()) {
				lstItem.add(5);	
			}
			if(para.isEnd()) {
				lstItem.add(6);	
			}
		}
		this.editStateOfDailyPerformance(para.getEmployeeId(), para.getDateData(), dailyPerfor.getEditState(), lstItem);
	}	
	@Override
	public void reflectOffOvertime(String employeeId, GeneralDate dateData, Map<Integer, Integer> mapOvertime, 
			boolean isPre, IntegrationOfDaily dailyInfor) {
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyInfor.getAttendanceTimeOfDailyPerformance().get();
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();
		// ドメインモデル「日別実績の残業時間」を取得する
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		
		Optional<OverTimeOfDaily> optOverTimeOfDaily = excessOfStatutory.getOverTimeWork();
		if(!optOverTimeOfDaily.isPresent()) {
			return;
		}
		OverTimeOfDaily overTimeOfDaily = optOverTimeOfDaily.get();
		List<OverTimeFrameTime> lstOverTimeWorkFrameTime = overTimeOfDaily.getOverTimeWorkFrameTime();
		if(lstOverTimeWorkFrameTime.isEmpty()) {
			return;
		}
		if(isPre) {			
			lstOverTimeWorkFrameTime.stream().forEach(x -> {
				if(mapOvertime.containsKey(x.getOverWorkFrameNo().v())) {
					x.setBeforeApplicationTime(new AttendanceTime(mapOvertime.get(x.getOverWorkFrameNo().v())));
				}
			});	
		} else {
			lstOverTimeWorkFrameTime.stream().forEach(x -> {
				if(mapOvertime.containsKey(x.getOverWorkFrameNo().v())) {
					x.getOverTimeWork().setTime(new AttendanceTime(mapOvertime.get(x.getOverWorkFrameNo().v())));
				}
			});
		}
		//残業時間の編集状態を更新する
		//日別実績の編集状態  予定項目ID=残業時間(枠番)の項目ID
		List<Integer> lstOverTemp = new ArrayList<>();
		if(isPre) {
			lstOverTemp = this.lstPreOvertimeItem();
			for(int i = 1; i <= 10; i++) {
				if(!mapOvertime.containsKey(i)) {
					Integer item = this.lstPreOvertimeItem().get(i - 1); 
					lstOverTemp.remove(item);
				}
			}	
		} else {
			lstOverTemp = this.lstAfterOvertimeItem();
			for(int i = 1; i <= 10; i++) {
				if(!mapOvertime.containsKey(i)) {
					Integer item = this.lstAfterOvertimeItem().get(i - 1); 
					lstOverTemp.remove(item);
				}
			}	
		}
		
		this.editStateOfDailyPerformance(employeeId, dateData,dailyInfor.getEditState(), lstOverTemp);
	}
	/**
	 * 予定項目ID=残業時間(枠番)の項目ID: 事前申請
	 * @return
	 */
	@Override
	public List<Integer> lstPreOvertimeItem(){
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(220);
		lstItem.add(225);
		lstItem.add(230);
		lstItem.add(235);
		lstItem.add(240);
		lstItem.add(245);
		lstItem.add(250);
		lstItem.add(255);
		lstItem.add(260);
		lstItem.add(265);
		return lstItem;		
	}
	/**
	 * 予定項目ID=残業時間(枠番)の項目ID: 事後申請
	 * @return
	 */
	@Override
	public List<Integer> lstAfterOvertimeItem(){
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(216);
		lstItem.add(221);
		lstItem.add(226);
		lstItem.add(231);
		lstItem.add(236);
		lstItem.add(241);
		lstItem.add(245);
		lstItem.add(251);
		lstItem.add(256);
		lstItem.add(261);
		return lstItem;		
	}
	
	@Override
	public void updateTimeShiftNight(String employeeId, GeneralDate dateData, Integer timeNight, boolean isPre,
			IntegrationOfDaily dailyInfor) {
		if(timeNight < 0) {
			return;
		}
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyInfor.getAttendanceTimeOfDailyPerformance().get();
		// 所定外深夜時間を反映する		
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();
		// ドメインモデル「日別実績の残業時間」を取得する
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		ExcessOfStatutoryMidNightTime exMidNightTime = excessOfStatutory.getExcessOfStatutoryMidNightTime();
		ExcessOfStatutoryMidNightTime tmp = new ExcessOfStatutoryMidNightTime(exMidNightTime.getTime(), new AttendanceTime(timeNight));
		if(isPre) {
			excessOfStatutory.setExcessOfStatutoryMidNightTime(tmp);
		} else {
			excessOfStatutory.getExcessOfStatutoryMidNightTime().getTime().setTime(new AttendanceTime(timeNight));
		}
		//attendanceTime.updateFlush(attendanceTimeData);
		//所定外深夜時間の編集状態を更新する
		List<Integer> lstNightItem = new ArrayList<Integer>();//所定外深夜時間の項目ID
		if(isPre) {
			lstNightItem.add(565);
		} else {
			lstNightItem.add(563);
		}
				
		this.editStateOfDailyPerformance(employeeId, dateData, dailyInfor.getEditState(), lstNightItem);
		
		//休出時間(深夜)の反映
		//this.updateBreakNight(employeeId, dateData);
	}
	@Override
	public void updateBreakNight(String employeeId, GeneralDate dateData, IntegrationOfDaily dailyInfo) {
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyInfo.getAttendanceTimeOfDailyPerformance().get();
		// 所定外深夜時間を反映する
		ActualWorkingTimeOfDaily actualWorkingTimeOfDaily = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime = actualWorkingTimeOfDaily.getTotalWorkingTime();		
		ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		Optional<HolidayWorkTimeOfDaily> optWorkHolidayTime = excessOfStatutoryTimeOfDaily.getWorkHolidayTime();
		if(!optWorkHolidayTime.isPresent()) {
			return;
		}
		HolidayWorkTimeOfDaily workHolidayTime = optWorkHolidayTime.get();
		Finally<HolidayMidnightWork> holidayMidNightWork = workHolidayTime.getHolidayMidNightWork();
		if(!holidayMidNightWork.isPresent()) {
			return;
		}
		HolidayMidnightWork holidayWorkMidNightTime = holidayMidNightWork.get();
		List<HolidayWorkMidNightTime> lstHolidayWorkMidNightTime = holidayWorkMidNightTime.getHolidayWorkMidNightTime();
		if(lstHolidayWorkMidNightTime.isEmpty()) {
			return;
		}
		lstHolidayWorkMidNightTime.stream().forEach(x -> {
			x.getTime().setTime(new AttendanceTime(0));
		});
		
		//attendanceTime.updateFlush(attendanceTimeData);
		//休出時間(深夜)(法内)の編集状態を更新する
		List<Integer> lstItem = new ArrayList<Integer>();
		//(法定区分=法定外休出)の時間の項目ID ???
		//(法定区分=祝日休出)の時間の項目ID
		//(法定区分=法定外休出)の時間の項目ID
		lstItem.add(570);
		lstItem.add(567);
		lstItem.add(572);
		this.editStateOfDailyPerformance(employeeId, dateData,dailyInfo.getEditState(), lstItem);
	}
	@Override
	public void updateFlexTime(String employeeId, GeneralDate dateData, Integer flexTime, boolean isPre,
			IntegrationOfDaily dailyInfor) {
		if(flexTime < 0) {
			return;
		}
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyInfor.getAttendanceTimeOfDailyPerformance().get();
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();		
		//ドメインモデル「日別実績の所定外時間」を取得する
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		// ドメインモデル「日別実績の残業時間」を取得する
		Optional<OverTimeOfDaily> optOverTimeOfDaily = excessOfStatutory.getOverTimeWork();
		if(!optOverTimeOfDaily.isPresent()) {
			return;
		}
		OverTimeOfDaily workHolidayTime = optOverTimeOfDaily.get();
		FlexTime flexTimeData = workHolidayTime.getFlexTime();
		FlexTime temp = new FlexTime(flexTimeData.getFlexTime(), new AttendanceTime(flexTime));
		if(isPre) {
			workHolidayTime.setFlexTime(temp);	
		} else {
			workHolidayTime.getFlexTime().getFlexTime().setTime(new AttendanceTimeOfExistMinus(flexTime));
		}
				
		//attendanceTime.updateFlush(attendanceTimeData);
		//フレックス時間の編集状態を更新する
		//日別実績の編集状態
		List<Integer> lstItem = new ArrayList<Integer>();//フレックス時間の項目ID
		if(isPre) {
			lstItem.add(555);	
		} else {
			lstItem.add(556);
		}
		
		this.editStateOfDailyPerformance(employeeId, dateData, dailyInfor.getEditState(), lstItem);
		
	}
	@Override
	public void updateRecordWorkType(String employeeId, GeneralDate dateData, String workTypeCode, boolean scheUpdate, IntegrationOfDaily dailyPerfor) {
		//日別実績の勤務情報
		List<Integer> lstItem = new ArrayList<>();
		WorkInfoOfDailyAttendance workInformation = dailyPerfor.getWorkInformation();
		if(scheUpdate) {
			lstItem.add(1);
			WorkInformation sched = workInformation.getScheduleInfo().clone();
			sched.setWorkTypeCode(new WorkTypeCode(workTypeCode));
			
			dailyPerfor.getWorkInformation().setScheduleInfo(sched);
		} else {
			lstItem.add(28);
			WorkInformation record = workInformation.getRecordInfo().clone();
			record.setWorkTypeCode(new WorkTypeCode(workTypeCode));
			
			dailyPerfor.getWorkInformation().setRecordInfo(record);			
		}
		//日別実績の編集状態
		this.editStateOfDailyPerformance(employeeId, dateData, dailyPerfor.getEditState(), lstItem);
		
	}
	@Override
	public IntegrationOfDaily updateWorkTimeFrame(String employeeId, GeneralDate dateData, Map<Integer, Integer> worktimeFrame,
			boolean isPre, IntegrationOfDaily dailyData, boolean isRec) {
		if(dailyData == null || !dailyData.getAttendanceTimeOfDailyPerformance().isPresent()) {
			return dailyData;
		}
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyData.getAttendanceTimeOfDailyPerformance().get();
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();		
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		//日別実績の休出時間
		Optional<HolidayWorkTimeOfDaily> optWorkHolidayTime = excessOfStatutory.getWorkHolidayTime();
		if(!optWorkHolidayTime.isPresent()) {
			return dailyData;
		}
		HolidayWorkTimeOfDaily workHolidayTime = optWorkHolidayTime.get();
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = workHolidayTime.getHolidayWorkFrameTime();
		List<Integer> lstWorktimeFrameTemp = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveComSetting comSetting = comSetRepo.find(companyId);
		List<CompensatoryOccurrenceSetting> eachCompanyTimeSet = comSetting.getCompensatoryOccurrenceSetting().stream()
				.filter(x -> x.getOccurrenceType() == CompensatoryOccurrenceDivision.WorkDayOffTime).collect(Collectors.toList());
		Optional<CompensatoryOccurrenceSetting> optOccurenceSetting = eachCompanyTimeSet.isEmpty() ? Optional.empty() : Optional.of(eachCompanyTimeSet.get(0));
		String workTimeCode = "";
		if(isPre) {
			workTimeCode = dailyData.getWorkInformation().getScheduleInfo().getWorkTimeCode() != null ? 
					dailyData.getWorkInformation().getScheduleInfo().getWorkTimeCode().v() : "";
		} else {
			workTimeCode = dailyData.getWorkInformation().getRecordInfo().getWorkTimeCode() != null ?
					dailyData.getWorkInformation().getRecordInfo().getWorkTimeCode().v() : "";
		}
		Optional<WorkType> workTypeInfor = worktypeRepo.findByPK(companyId, dailyData.getWorkInformation().getRecordInfo().getWorkTypeCode().v());
		if(!workTypeInfor.isPresent()) {
			return dailyData;
		}
		List<WorkTimezoneOtherSubHolTimeSet> lstWorkTime = this.subhol(companyId, workTimeCode).stream()
				.filter(x -> x.getOriginAtr() == nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision.WorkDayOffTime)
				.collect(Collectors.toList());
		Optional<WorkTimezoneOtherSubHolTimeSet> subHolSet = lstWorkTime.isEmpty() ? Optional.empty() : Optional.of(lstWorkTime.get(0));
		if(isPre) {
			lstHolidayWorkFrameTime.stream().forEach(a -> {
				if(worktimeFrame.containsKey(a.getHolidayFrameNo().v())) {
					AttendanceTime worktimeTmp = new AttendanceTime(worktimeFrame.get(a.getHolidayFrameNo().v()));
					a.setBeforeApplicationTime(Finally.of(worktimeTmp));
				}
			});			
		} else {
			lstHolidayWorkFrameTime.stream().forEach(a -> {
				if(worktimeFrame.containsKey(a.getHolidayFrameNo().v())) {
					Finally<TimeDivergenceWithCalculation> holidayWorkTime = a.getHolidayWorkTime();
					if(holidayWorkTime.isPresent()) {
						TimeDivergenceWithCalculation holidayWorkTimeData = holidayWorkTime.get();
						holidayWorkTimeData.setTime(new AttendanceTime(worktimeFrame.get(a.getHolidayFrameNo().v())));
					}
				}
			});
		}
		List<HolidayWorkFrameTime> result = HolidayWorkTimeSheet.transProcess(workTypeInfor.get(), lstHolidayWorkFrameTime, 
				subHolSet, optOccurenceSetting);
		attendanceTimeData
			.getActualWorkingTimeOfDaily().getTotalWorkingTime()
			.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().setHolidayWorkFrameTime(result);
		dailyData.setAttendanceTimeOfDailyPerformance(Optional.of(attendanceTimeData));
		//↓ fix bug 103077
		if(!isRec) {
			if(isPre) {
				lstWorktimeFrameTemp.addAll(this.lstPreWorktimeFrameItem());
			} else {
				lstWorktimeFrameTemp.addAll(this.lstAfterWorktimeFrameItem());
				lstWorktimeFrameTemp.addAll(this.lstTranfertimeFrameItem());
			}
			this.editStateOfDailyPerformance(employeeId, dateData,dailyData.getEditState(), lstWorktimeFrameTemp);
		}
		//↑ fix bug 103077
		return dailyData;
	}
	private List<WorkTimezoneOtherSubHolTimeSet> subhol(String companyId, String workTimeCode){
		val require = requireService.createRequire();
		
		Optional<WorkTimezoneCommonSet> optWorktimezone = GetCommonSet.workTimezoneCommonSet(require, companyId, workTimeCode);
		if(!optWorktimezone.isPresent()) {
			return new ArrayList<>();
		}
		WorkTimezoneCommonSet commonSet = optWorktimezone.get();
		List<WorkTimezoneOtherSubHolTimeSet> subHolTimeSet = commonSet.getSubHolTimeSet();
		return subHolTimeSet;
	}
	/**
	 * 事前休日出勤時間の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstPreWorktimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(270);
		lstItem.add(275);
		lstItem.add(280);
		lstItem.add(285);
		lstItem.add(290);
		lstItem.add(295);
		lstItem.add(300);
		lstItem.add(305);
		lstItem.add(310);
		lstItem.add(315);
		return lstItem;
	}
	/**
	 * 事後休日出勤時間帯の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstAfterWorktimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();
		lstItem.add(266);
		lstItem.add(271);
		lstItem.add(276);
		lstItem.add(281);
		lstItem.add(286);
		lstItem.add(291);
		lstItem.add(296);
		lstItem.add(301);
		lstItem.add(306);
		lstItem.add(311);
		return lstItem;
	}
	/**
	 * 振替時間の項目ID
	 * @return
	 */
	@Override
	public List<Integer> lstTranfertimeFrameItem(){
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(267);
		lstItem.add(272);
		lstItem.add(277);
		lstItem.add(282);
		lstItem.add(287);
		lstItem.add(292);
		lstItem.add(297);
		lstItem.add(302);
		lstItem.add(307);
		lstItem.add(312);
		return lstItem;
	}
	
	@Override
	public void updateRecordStartEndTimeReflect(TimeReflectPara data, IntegrationOfDaily dailyData) {
		Optional<TimeLeavingOfDailyAttd> optTimeLeaving = dailyData.getAttendanceLeave();
		TimeLeavingOfDailyAttd timeDaily = null;
		if(optTimeLeaving.isPresent()) {
			timeDaily = optTimeLeaving.get();
		}
		if(!data.isStart()
				&& !data.isEnd()) {
			return;
		}
		//開始時刻を反映する
		List<TimeLeavingWork> lstTimeLeavingWorks = new ArrayList<>();
		if(timeDaily != null) {
			lstTimeLeavingWorks = timeDaily.getTimeLeavingWorks().stream()
					.filter(x -> x.getWorkNo().v() == data.getFrameNo()).collect(Collectors.toList());
		}
		TimeLeavingWork timeLeavingWork = null;
		if(lstTimeLeavingWorks.isEmpty()) {
			if(data.getStartTime() == null || data.getEndTime() == null) {
				List<Integer> lstItem = new ArrayList<Integer>();
				if(data.isStart()) {
					if(data.getFrameNo() == 1) {
						lstItem.add(31);
					} else {
						lstItem.add(41);
					}
				}
				if(data.isEnd()) {
					if(data.getFrameNo() == 1) {
						lstItem.add(34);
					} else {
						lstItem.add(44);
					}
				}
				this.editStateOfDailyPerformance(data.getEmployeeId(), data.getDateData(), dailyData.getEditState(), lstItem);
				return;
			}
			WorkStamp workStamp = new WorkStamp(new TimeWithDayAttr(data.getStartTime()),
                    new TimeWithDayAttr(data.getStartTime()),
                    null,
                    TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
			WorkStamp endWorkStamp = new WorkStamp(new TimeWithDayAttr(data.getEndTime()),
                    new TimeWithDayAttr(data.getEndTime()),
                    null,
                    TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
            TimeActualStamp timeActualStamp = new TimeActualStamp(null, workStamp, 0);
            TimeActualStamp endtimeActualStamp = new TimeActualStamp(null, endWorkStamp, 0);
            timeLeavingWork = new TimeLeavingWork(new WorkNo(1), timeActualStamp, endtimeActualStamp);
            lstTimeLeavingWorks.add(timeLeavingWork);
		} else {
			timeLeavingWork = lstTimeLeavingWorks.get(0);
		}
		lstTimeLeavingWorks.stream().forEach(a -> {
			a.getAttendanceStamp().ifPresent(x -> {
				if(data.isStart()) {
					x.getStamp().ifPresent(y -> {
						y.getTimeDay().setTimeWithDay(data.getStartTime() != null ?Optional.of( new TimeWithDayAttr(data.getStartTime())) : null);
						y.setAfterRoundingTime(data.getStartTime() != null ? new TimeWithDayAttr(data.getStartTime()) : null);
						y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION);
					});
					if(!x.getStamp().isPresent() && data.getStartTime() != null) {
						x.setStamp(Optional.ofNullable(new WorkStamp(new TimeWithDayAttr(data.getStartTime()),
								new TimeWithDayAttr(data.getStartTime()),
								null,
								TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null)));
					}	
				}				
			});
			a.getLeaveStamp().ifPresent(x -> {
				if(data.isEnd()) {
					x.getStamp().ifPresent(y -> {
						y.getTimeDay().setTimeWithDay(data.getEndTime() != null ? Optional.of(new TimeWithDayAttr(data.getEndTime())) : null);
						y.setAfterRoundingTime(data.getEndTime() != null ? new TimeWithDayAttr(data.getEndTime()) : null);
						y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION);
					});
					if(!x.getStamp().isPresent() && data.getEndTime() != null) {
						x.setStamp(Optional.ofNullable(new WorkStamp(new TimeWithDayAttr(data.getEndTime()),
								new TimeWithDayAttr(data.getEndTime()),
								null,
								TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null)));
					}
				}
			});
		});		
		if(timeDaily == null) {
			timeDaily = new TimeLeavingOfDailyAttd( Arrays.asList(timeLeavingWork), new WorkTimes(1));
		}
		dailyData.setAttendanceLeave(Optional.of(timeDaily));
		//開始時刻の編集状態を更新する
		//予定項目ID=出勤の項目ID	
		List<Integer> lstItem = new ArrayList<Integer>();
		if(data.isStart()) {
			if(data.getFrameNo() == 1) {
				lstItem.add(31);
			} else {
				lstItem.add(41);
			}
		}
		if(data.isEnd()) {
			if(data.getFrameNo() == 1) {
				lstItem.add(34);
			} else {
				lstItem.add(44);
			}
		}
		this.editStateOfDailyPerformance(data.getEmployeeId(), data.getDateData(), dailyData.getEditState(), lstItem);
	}
	@Override
	public IntegrationOfDaily updateWorkTimeTypeHoliwork(ReflectParameter para, boolean scheUpdate,
			IntegrationOfDaily dailyData) {		
		WorkInfoOfDailyAttendance dailyPerfor = dailyData.getWorkInformation();
		WorkInformation workInfor = new WorkInformation(para.getWorkTypeCode(), para.getWorkTimeCode());
		List<Integer> lstItem = new ArrayList<>();
		if(scheUpdate) {
			this.dailyInfo(para.getWorkTimeCode(), para.getWorkTypeCode(), dailyPerfor);
			lstItem.add(1);
			lstItem.add(2);
			dailyPerfor.setScheduleInfo(workInfor);
			dailyData.setWorkInformation(dailyPerfor);
		} else {
			lstItem.add(28);
			lstItem.add(29);
			dailyPerfor.setRecordInfo(workInfor);
			dailyData.setWorkInformation(dailyPerfor);
		}
		//日別実績の編集状態	
		this.editStateOfDailyPerformance(para.getEmployeeId(), para.getDateData(), dailyData.getEditState(), lstItem);
		return dailyData;
	}
	@Override
	public IntegrationOfDaily updateScheStartEndTimeHoliday(TimeReflectPara para, IntegrationOfDaily dailyData) {
		if(para.getStartTime() == null
				|| para.getEndTime() == null) {
			return dailyData;
		}		
		//日別実績の勤務情報
		WorkInfoOfDailyAttendance dailyPerfor = dailyData.getWorkInformation();
		ScheduleTimeSheet timeSheet;
		if(dailyPerfor.getScheduleTimeSheets().isEmpty()) {
			timeSheet = new ScheduleTimeSheet(1, 
					para.getStartTime(),
					para.getEndTime());
		} else {
			List<ScheduleTimeSheet> lstTimeSheetFrameNo = dailyPerfor.getScheduleTimeSheets().stream()
					.filter(x -> x.getWorkNo().v() == para.getFrameNo()).collect(Collectors.toList());
			if(lstTimeSheetFrameNo.isEmpty()) {
				return dailyData;
			}
			ScheduleTimeSheet timeSheetFrameNo = lstTimeSheetFrameNo.get(0);
			
			timeSheet = new ScheduleTimeSheet(timeSheetFrameNo.getWorkNo().v(), 
					para.isStart() ? para.getStartTime() : timeSheetFrameNo.getAttendance().v(),
					para.isEnd() ? para.getEndTime() : timeSheetFrameNo.getLeaveWork().v());

			dailyPerfor.getScheduleTimeSheets().remove(timeSheetFrameNo);
		}
		
		dailyPerfor.getScheduleTimeSheets().add(timeSheet);
		dailyData.setWorkInformation(dailyPerfor);
		//workRepository.updateByKeyFlush(dailyPerfor);
		//日別実績の編集状態
		//予定開始時刻の項目ID
		List<Integer> lstItem = new ArrayList<Integer>();
		if(para.getFrameNo() == 1) {
			if(para.isStart()) {
				lstItem.add(3);	
			}
			if(para.isEnd()) {
				lstItem.add(4);	
			}
		} else {
			if(para.isStart()) {
				lstItem.add(5);	
			}
			if(para.isEnd()) {
				lstItem.add(6);	
			}
		}
		this.editStateOfDailyPerformance(para.getEmployeeId(), para.getDateData(), dailyData.getEditState(), lstItem);
		return dailyData;
	}
	@Override
	public IntegrationOfDaily updateTimeShiftNightHoliday(String employeeId, GeneralDate dateData, Integer timeNight,
			boolean isPre, IntegrationOfDaily dailyData) {
		if(timeNight == null || timeNight < 0) {
			return dailyData;
		}
		// 所定外深夜時間を反映する		
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyData.getAttendanceTimeOfDailyPerformance().get();		
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();
		// ドメインモデル「日別実績の残業時間」を取得する
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		ExcessOfStatutoryMidNightTime exMidNightTime = excessOfStatutory.getExcessOfStatutoryMidNightTime();
		ExcessOfStatutoryMidNightTime tmp = new ExcessOfStatutoryMidNightTime(exMidNightTime.getTime(), new AttendanceTime(timeNight));
		if(isPre) {
			excessOfStatutory.setExcessOfStatutoryMidNightTime(tmp);
		} else {
			excessOfStatutory.getExcessOfStatutoryMidNightTime().getTime().setTime(new AttendanceTime(timeNight));
		}
		dailyData.setAttendanceTimeOfDailyPerformance(Optional.of(attendanceTimeData));
		//attendanceTime.updateFlush(attendanceTimeData);
		//所定外深夜時間の編集状態を更新する
		List<Integer> lstNightItem = new ArrayList<Integer>();//所定外深夜時間の項目ID
		if(isPre) {
			lstNightItem.add(565);
		} else {
			lstNightItem.add(563);
		}
		this.editStateOfDailyPerformance(employeeId, dateData, dailyData.getEditState(), lstNightItem);
		return dailyData;
	}
	@Override
	public void updateRecordWorkTime(String employeeId, GeneralDate dateData, String workTimeCode, boolean scheUpdate,
			IntegrationOfDaily dailyPerfor) {
		//日別実績の勤務情報
		List<Integer> lstItem = new ArrayList<>();
		WorkInfoOfDailyAttendance workInformation = dailyPerfor.getWorkInformation();
		if(scheUpdate) {
			lstItem.add(2);
			WorkInformation sched = workInformation.getScheduleInfo().clone();
			
			sched.setWorkTimeCode(workTimeCode);
			
			dailyPerfor.getWorkInformation().setScheduleInfo(sched);
			//workRepository.updateByKeyFlush(dailyPerfor);
		} else {
			lstItem.add(29);
			WorkInformation record = workInformation.getRecordInfo();
			record.setWorkTimeCode(workTimeCode);
			
			dailyPerfor.getWorkInformation().setRecordInfo(record);
			//workRepository.updateByKeyFlush(dailyPerfor);
		}
		//日別実績の編集状態
		this.editStateOfDailyPerformance(employeeId, dateData, dailyPerfor.getEditState(), lstItem);
		
	}
	@Override
	public void updateTransferTimeFrame(String employeeId, GeneralDate dateData,
			Map<Integer, Integer> transferTimeFrame, IntegrationOfDaily dailyPerfor) {
		AttendanceTimeOfDailyAttendance attendanceTimeData = dailyPerfor.getAttendanceTimeOfDailyPerformance().get();
		ActualWorkingTimeOfDaily actualWorkingTime = attendanceTimeData.getActualWorkingTimeOfDaily();
		TotalWorkingTime totalWorkingTime =  actualWorkingTime.getTotalWorkingTime();		
		ExcessOfStatutoryTimeOfDaily excessOfStatutory = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
		//日別実績の休出時間
		Optional<HolidayWorkTimeOfDaily> optWorkHolidayTime = excessOfStatutory.getWorkHolidayTime();
		if(!optWorkHolidayTime.isPresent()) {
			return;
		}
		HolidayWorkTimeOfDaily workHolidayTime = optWorkHolidayTime.get();
		List<HolidayWorkFrameTime> lstHolidayWorkFrameTime = workHolidayTime.getHolidayWorkFrameTime();
		if(lstHolidayWorkFrameTime.isEmpty()) {
			return;
		}
		lstHolidayWorkFrameTime.stream().forEach(x -> {
			if(transferTimeFrame.containsKey(x.getHolidayFrameNo().v())) {
				Finally<TimeDivergenceWithCalculation> finTransferTime = x.getTransferTime();
				TimeDivergenceWithCalculation transferTime = finTransferTime.get();
				transferTime.setTime(new AttendanceTime(transferTimeFrame.get(x.getHolidayFrameNo().v())));
			}
		});		
		dailyPerfor.setAttendanceTimeOfDailyPerformance(Optional.of(attendanceTimeData));
	}
	@Override
	public void updateRecordStartEndTimeReflectRecruitment(TimeReflectPara data
			, IntegrationOfDaily dailyData) {
		Optional<TimeLeavingOfDailyAttd> optTimeLeaving = dailyData.getAttendanceLeave();
		TimeLeavingOfDailyAttd timeDaily = null;
		if(optTimeLeaving.isPresent()) {
			timeDaily = optTimeLeaving.get();
		}
		if(!data.isStart()
				&& !data.isEnd()) {
			return;
		}
		//開始時刻を反映する
		List<TimeLeavingWork> lstTimeLeavingWorks = new ArrayList<>();
		if(timeDaily != null) {
			lstTimeLeavingWorks = timeDaily.getTimeLeavingWorks().stream()
					.filter(x -> x.getWorkNo().v() == data.getFrameNo()).collect(Collectors.toList());
		}
		TimeLeavingWork timeLeavingWork = null;
		if(lstTimeLeavingWorks.isEmpty()) {
			WorkStamp workStamp = new WorkStamp(new TimeWithDayAttr(data.getStartTime()),
                    new TimeWithDayAttr(data.getStartTime()),
                    null,
                    TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
			WorkStamp endWorkStamp = new WorkStamp(new TimeWithDayAttr(data.getEndTime()),
                    new TimeWithDayAttr(data.getEndTime()),
                    null,
                    TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
            TimeActualStamp timeActualStamp = new TimeActualStamp(null, workStamp, 0);
            TimeActualStamp endtimeActualStamp = new TimeActualStamp(null, endWorkStamp, 0);
            timeLeavingWork = new TimeLeavingWork(new WorkNo(1), timeActualStamp, endtimeActualStamp);
		} else {
			timeLeavingWork = lstTimeLeavingWorks.get(0);
		}
		Optional<TimeActualStamp> optTimeAttendanceStart = timeLeavingWork.getAttendanceStamp();
		Optional<TimeActualStamp> optTimeAttendanceEnd = timeLeavingWork.getLeaveStamp();
		if(data.isStart() && optTimeAttendanceStart.isPresent()) {
			TimeActualStamp timeAttendanceStart= optTimeAttendanceStart.get();
			Optional<WorkStamp> optStamp = timeAttendanceStart.getStamp();
			if(optStamp.isPresent()) {
				WorkStamp stamp = optStamp.get();
				WorkStamp stampTmp = new WorkStamp(new TimeWithDayAttr(data.getStartTime()),
						new TimeWithDayAttr(data.getStartTime()),
						stamp.getLocationCode().isPresent() ? stamp.getLocationCode().get() : null,
								TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
				TimeActualStamp timeActualStam = new TimeActualStamp(timeAttendanceStart.getActualStamp().isPresent() ? timeAttendanceStart.getActualStamp().get() : null,
						stampTmp,
						timeAttendanceStart.getNumberOfReflectionStamp());
				optTimeAttendanceStart = Optional.of(timeActualStam);
			}
		}
		if(data.isEnd() && optTimeAttendanceEnd.isPresent()) {			
			TimeActualStamp timeAttendanceEnd = optTimeAttendanceEnd.get();
			Optional<WorkStamp> optStamp = timeAttendanceEnd.getStamp();
			if(optStamp.isPresent()) {				
				WorkStamp stamp = optStamp.get();
				WorkStamp stampTmp = new WorkStamp(new TimeWithDayAttr(data.getStartTime()),
						new TimeWithDayAttr(data.getEndTime()),
						stamp.getLocationCode().isPresent() ? stamp.getLocationCode().get() : null,
								TimeChangeMeans.DIRECT_BOUNCE_APPLICATION,null);
				TimeActualStamp timeActualStam = new TimeActualStamp(timeAttendanceEnd.getActualStamp().isPresent() ? timeAttendanceEnd.getActualStamp().get() : null,
						stampTmp,
						timeAttendanceEnd.getNumberOfReflectionStamp());
				optTimeAttendanceEnd = Optional.of(timeActualStam);
			}
		}
		TimeLeavingWork timeLeavingWorkTmp = new TimeLeavingWork(timeLeavingWork.getWorkNo(),
				optTimeAttendanceStart.get(),
				optTimeAttendanceEnd.get());
		if(!lstTimeLeavingWorks.isEmpty()) {
			timeDaily.getTimeLeavingWorks().remove(timeLeavingWork);
			timeDaily.getTimeLeavingWorks().add(timeLeavingWorkTmp);
		} else {
			timeDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWorkTmp),new WorkTimes(1));
		}
		TimeLeavingOfDailyPerformance datatimeDailyPer = new TimeLeavingOfDailyPerformance(dailyData.getEmployeeId(), dailyData.getYmd(), timeDaily);
		timeLeavingOfDaily.updateFlush(datatimeDailyPer);
		dailyData.setAttendanceLeave(Optional.of(timeDaily));
		//開始時刻の編集状態を更新する
		//予定項目ID=出勤の項目ID	
		List<Integer> lstItem = new ArrayList<Integer>();
		if(data.isStart()) {
			if(data.getFrameNo() == 1) {
				lstItem.add(31);
			} else {
				lstItem.add(41);
			}
		}
		if(data.isEnd()) {
			if(data.getFrameNo() == 1) {
				lstItem.add(34);
			} else {
				lstItem.add(44);
			}
		}
		this.editStateOfDailyPerformance(data.getEmployeeId(), data.getDateData(), dailyData.getEditState(), lstItem);
	}

	@Override
	public void reflectReason(String sid, GeneralDate appDate, String appReason, OverTimeRecordAtr overTimeAtr
			, IntegrationOfDaily dailyData) {
		//備考の編集状態を更新する
		List<Integer> lstItem = new ArrayList<>();
		appReason = appReason.replaceAll(System.lineSeparator(), "　").replaceAll("\n", "　");
		int columnNo = 4;
		//残業区分をチェックする
		lstItem.add(836);	
		//日別実績の備考を存在チェックする
		Optional<RemarksOfDailyPerform> optRemark = remarksOfDailyRepo.getByKeys(sid, appDate, columnNo);		
		if(optRemark.isPresent()) {
			RemarksOfDailyAttd remarkData = optRemark.get().getRemarks();
			String remarkStr = remarkData.getRemarks().v() + "　" + appReason;
			//申請理由の文字の長さをチェックする
			if(remarkStr.length() > 100) {
				remarkStr = remarkStr.substring(0, 100);
			}
			remarkData.setRemarks(new RecordRemarks(remarkStr));
			RemarksOfDailyPerform newRemark = new RemarksOfDailyPerform(dailyData.getEmployeeId(), dailyData.getYmd(), remarkData);
			//日別実績の備考を変更する
			remarksOfDailyRepo.update(newRemark);
		} else {
			if(appReason.length() > 100) {
				appReason = appReason.substring(0, 100);
			}
			RemarksOfDailyPerform remarkInfo = new RemarksOfDailyPerform(sid,
					appDate, 
					new RecordRemarks(appReason), 
					columnNo);
			//日別実績の備考を追加する
			remarksOfDailyRepo.add(remarkInfo);
		}

		this.editStateOfDailyPerformance(sid, appDate,dailyData.getEditState(), lstItem);
	}
	@Override
	public void updateTimeNotReflect(String employeeId, GeneralDate dateData) {
		Optional<TimeLeavingOfDailyPerformance> optTimeLeaving = timeLeavingOfDaily.findByKey(employeeId, dateData);
		if(!optTimeLeaving.isPresent()) {
			return;
		}
		TimeLeavingOfDailyAttd timeDaily = optTimeLeaving.get().getAttendance();

		//開始時刻を反映する
		List<TimeLeavingWork> lstTimeLeavingWorks = timeDaily.getTimeLeavingWorks().stream()
					.filter(x -> x.getWorkNo().v() == 1).collect(Collectors.toList());
		TimeLeavingWork timeLeavingWork = null;
		if(!lstTimeLeavingWorks.isEmpty()) {
			timeLeavingWork = lstTimeLeavingWorks.get(0);
		} else {
			return;
		}
		Optional<TimeActualStamp> optTimeAttendanceStart = timeLeavingWork.getAttendanceStamp();
		
		Optional<TimeActualStamp> optTimeAttendanceEnd = timeLeavingWork.getLeaveStamp();
		if(optTimeAttendanceStart.isPresent()) {
			TimeActualStamp timeAttendanceStart= optTimeAttendanceStart.get();
			Optional<WorkStamp> optStamp = timeAttendanceStart.getStamp();
			WorkStamp stampTmp = null;
			if(optStamp.isPresent()) {
				TimeActualStamp timeActualStam = new TimeActualStamp(timeAttendanceStart.getActualStamp().isPresent() ? timeAttendanceStart.getActualStamp().get() : null,
						stampTmp,
						timeAttendanceStart.getNumberOfReflectionStamp());
				optTimeAttendanceStart = Optional.of(timeActualStam);
			}
		}
		if(optTimeAttendanceEnd.isPresent()) {			
			TimeActualStamp timeAttendanceEnd = optTimeAttendanceEnd.get();
			Optional<WorkStamp> optStamp = timeAttendanceEnd.getStamp();
			WorkStamp stampTmp = null;
			if(optStamp.isPresent()) {				
				TimeActualStamp timeActualStam = new TimeActualStamp(timeAttendanceEnd.getActualStamp().isPresent() ? timeAttendanceEnd.getActualStamp().get() : null,
						stampTmp,
						timeAttendanceEnd.getNumberOfReflectionStamp());
				optTimeAttendanceEnd = Optional.of(timeActualStam);
			}
		}
		TimeLeavingWork timeLeavingWorkTmp = new TimeLeavingWork(timeLeavingWork.getWorkNo(),
				optTimeAttendanceStart.get(),
				optTimeAttendanceEnd.get());
		if(!lstTimeLeavingWorks.isEmpty()) {
			timeDaily.getTimeLeavingWorks().remove(timeLeavingWork);
			timeDaily.getTimeLeavingWorks().add(timeLeavingWorkTmp);
		} else {
			timeDaily = new TimeLeavingOfDailyAttd(Arrays.asList(timeLeavingWorkTmp), new WorkTimes(1));
		}
		TimeLeavingOfDailyPerformance dataTimeDaily = new TimeLeavingOfDailyPerformance(employeeId, dateData, timeDaily);
		timeLeavingOfDaily.updateFlush(dataTimeDaily);
				
	}
	@Override
	public List<Integer> lstTransferTimeOtItem() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(269);
		lstItem.add(274);
		lstItem.add(279);
		lstItem.add(284);
		lstItem.add(289);
		lstItem.add(294);
		lstItem.add(299);
		lstItem.add(304);
		lstItem.add(309);
		lstItem.add(314);
		return lstItem;
	}
	@Override
	public void updateBreakTime(Map<Integer, BreakTimeParam> mapBreakTimeFrame, boolean recordReflectBreakFlg,
			boolean isPre, IntegrationOfDaily daily) {
		if((!isPre && !recordReflectBreakFlg)) {
			return;
		}
		List<BreakTimeOfDailyPerformance> breakTime = daily.getBreakTime().stream().map(c-> new BreakTimeOfDailyPerformance(daily.getEmployeeId(), daily.getYmd(), c)).collect(Collectors.toList());
		if(breakTime.isEmpty()) {
			List<BreakTimeSheet> lstBreakTime = new ArrayList<>();
			mapBreakTimeFrame.forEach((a,b) ->{
				BreakTimeSheet timeSheet = new BreakTimeSheet(new BreakFrameNo(a),
						new TimeWithDayAttr(b.getStartTime()), 
						new TimeWithDayAttr(b.getEndTime()));
				lstBreakTime.add(timeSheet);
			});
			if(!lstBreakTime.isEmpty()) {
				if(!isPre) {
					BreakTimeOfDailyPerformance breakTimeOfDaily = new BreakTimeOfDailyPerformance(daily.getEmployeeId(),
							BreakType.REFER_WORK_TIME, 
							lstBreakTime, 
							daily.getYmd());
					daily.getBreakTime().add(breakTimeOfDaily.getTimeZone());
					breakTimeOfDailyRepo.insert(breakTimeOfDaily);	
				} else {
					BreakTimeOfDailyPerformance breakTimeOfDailySche = new BreakTimeOfDailyPerformance(daily.getEmployeeId(),
							BreakType.REFER_SCHEDULE, 
							lstBreakTime, 
							daily.getYmd());
					daily.getBreakTime().add(breakTimeOfDailySche.getTimeZone());
					breakTimeOfDailyRepo.insert(breakTimeOfDailySche);	
				}
			}
			
		} else {
			if(mapBreakTimeFrame.isEmpty()) {
				if(isPre) {
					List<BreakTimeOfDailyPerformance> breakTimePre = breakTime.stream().filter(x -> x.getTimeZone().getBreakType() == BreakType.REFER_SCHEDULE).collect(Collectors.toList());
					for (BreakTimeOfDailyPerformance x : breakTimePre) {
						breakTime.remove(x);
					}					
				} else {
					List<BreakTimeOfDailyPerformance> breakTimeNotPre = breakTime.stream().filter(x -> x.getTimeZone().getBreakType() == BreakType.REFER_WORK_TIME).collect(Collectors.toList());
					for (BreakTimeOfDailyPerformance x : breakTimeNotPre) {
						breakTime.remove(x);
					}	
				}				
			} else {
				if(isPre) {
					breakTime = breakTime.stream().filter(x -> x.getTimeZone().getBreakType() == BreakType.REFER_SCHEDULE).collect(Collectors.toList());
				} else {
					breakTime = breakTime.stream().filter(x -> x.getTimeZone().getBreakType() == BreakType.REFER_WORK_TIME).collect(Collectors.toList());
				}
				
				//休日が予定か実績は反映しました。
				if(breakTime.isEmpty()) {
					List<BreakTimeSheet> lstBreakTime = new ArrayList<>();
					mapBreakTimeFrame.forEach((a,b) ->{
						BreakTimeSheet timeSheet = new BreakTimeSheet(new BreakFrameNo(a),
								new TimeWithDayAttr(b.getStartTime()), 
								new TimeWithDayAttr(b.getEndTime()));
						lstBreakTime.add(timeSheet);
					});
					if(isPre) {
						BreakTimeOfDailyPerformance breakTimeOfDailySche = new BreakTimeOfDailyPerformance(daily.getEmployeeId(),
								BreakType.REFER_SCHEDULE, 
								lstBreakTime, 
								daily.getYmd());
						daily.getBreakTime().add(breakTimeOfDailySche.getTimeZone());
					} else {
						BreakTimeOfDailyPerformance breakTimeOfDaily = new BreakTimeOfDailyPerformance(daily.getEmployeeId(),
								BreakType.REFER_WORK_TIME, 
								lstBreakTime, 
								daily.getYmd());
						daily.getBreakTime().add(breakTimeOfDaily.getTimeZone());
					}
				}
				for (BreakTimeOfDailyPerformance breakTimeSheet : breakTime) {
					List<BreakTimeSheet> lstBreakTimeData  = breakTimeSheet.getTimeZone().getBreakTimeSheets();
					mapBreakTimeFrame.forEach((a,b) ->{
						boolean isSet = false;
						for (BreakTimeSheet x : lstBreakTimeData) {
							if(x.getBreakFrameNo().v() == a) {
								x.setStartTime(new TimeWithDayAttr(b.getStartTime()));
								x.setEndTime(new TimeWithDayAttr(b.getEndTime()));
								isSet = true;
								break;
							}
						}
						if(!isSet) {
							BreakTimeSheet timeSheet = new BreakTimeSheet(new BreakFrameNo(a), new TimeWithDayAttr(b.getStartTime()), new TimeWithDayAttr(b.getEndTime()));
							lstBreakTimeData.add(timeSheet);
						}
						
					});
				}
			}
		}
		if(isPre) {
			this.editStateOfDailyPerformance(daily.getEmployeeId(),
					daily.getYmd(),
					daily.getEditState(),
					this.lstScheBreakStartTime());
			this.editStateOfDailyPerformance(daily.getEmployeeId(),
					daily.getYmd(),
					daily.getEditState(),
					this.lstScheBreakEndTime());	
		} else {
			this.editStateOfDailyPerformance(daily.getEmployeeId(),
					daily.getYmd(),
					daily.getEditState(),
					this.lstBreakStartTime());
			this.editStateOfDailyPerformance(daily.getEmployeeId(),
					daily.getYmd(),
					daily.getEditState(),
					this.lstBreakEndTime());
		}
		
		
	}
	@Override
	public List<Integer> lstBreakStartTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(157);
		lstItem.add(163);
		lstItem.add(169);
		lstItem.add(175);
		lstItem.add(181);
		lstItem.add(187);
		lstItem.add(193);
		lstItem.add(199);
		lstItem.add(205);
		lstItem.add(211);
		return lstItem;
	}
	@Override
	public List<Integer> lstBreakEndTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(159);
		lstItem.add(165);
		lstItem.add(171);
		lstItem.add(177);
		lstItem.add(183);
		lstItem.add(189);
		lstItem.add(195);
		lstItem.add(201);
		lstItem.add(207);
		lstItem.add(213);
		return lstItem;
	}
	@Override
	public List<Integer> lstScheBreakStartTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(7);
		lstItem.add(9);
		lstItem.add(11);
		lstItem.add(13);
		lstItem.add(15);
		lstItem.add(17);
		lstItem.add(19);
		lstItem.add(21);
		lstItem.add(23);
		lstItem.add(25);
		return lstItem;
	}
	@Override
	public List<Integer> lstScheBreakEndTime() {
		List<Integer> lstItem = new ArrayList<>();		
		lstItem.add(8);
		lstItem.add(10);
		lstItem.add(12);
		lstItem.add(14);
		lstItem.add(16);
		lstItem.add(18);
		lstItem.add(20);
		lstItem.add(22);
		lstItem.add(24);
		lstItem.add(26);
		return lstItem;
	}
	@Override
	public void cleanRecordTimeData(String employeeId, GeneralDate baseDate, IntegrationOfDaily dailyInfor) {
		Optional<TimeLeavingOfDailyAttd> optTimeLeaving = dailyInfor.getAttendanceLeave();
		TimeLeavingOfDailyAttd timeDaily = null;
		if(optTimeLeaving.isPresent()) {
			timeDaily = optTimeLeaving.get();
		}
		
		List<TimeLeavingWork> lstTimeLeavingWorks = new ArrayList<>();
		if(timeDaily != null) {
			lstTimeLeavingWorks = timeDaily.getTimeLeavingWorks().stream()
					.filter(x -> x.getWorkNo().v() == 1).collect(Collectors.toList());
		}
		lstTimeLeavingWorks.stream().forEach(c -> {
			c.getAttendanceStamp().ifPresent(a -> {
				a.getStamp().ifPresent(x -> {
					x.getTimeDay().setTimeWithDay(Optional.empty());
					x.setAfterRoundingTime(null);
					x.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION);
				});
			});
			c.getLeaveStamp().ifPresent(y -> {
				y.getStamp().ifPresent(z -> {
					z.getTimeDay().setTimeWithDay(Optional.empty());
					z.setAfterRoundingTime(null);
					z.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.DIRECT_BOUNCE_APPLICATION);
				});
			});
		});
		
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(31);
		lstItem.add(34);			
		this.editStateOfDailyPerformance(employeeId, baseDate, dailyInfor.getEditState(), lstItem);
	}
	@Override
	public void cleanScheTime(String employeeId, GeneralDate baseDate, IntegrationOfDaily dailyInfor) {
		List<ScheduleTimeSheet> lstScheduleTimeSheet = dailyInfor.getWorkInformation().getScheduleTimeSheets();
		List<ScheduleTimeSheet> lstScheduleTimeSheetNo1 = lstScheduleTimeSheet.stream()
				.filter(x -> x.getWorkNo().v() == 1).collect(Collectors.toList());
		if(!lstScheduleTimeSheetNo1.isEmpty()) {
			lstScheduleTimeSheet.remove(0);
			dailyInfor.getWorkInformation().setScheduleTimeSheets(lstScheduleTimeSheet);
		}
		List<Integer> lstItem = new ArrayList<Integer>();
		lstItem.add(3);
		lstItem.add(4);
		this.editStateOfDailyPerformance(employeeId, baseDate, dailyInfor.getEditState(), lstItem);
	}
	
}
