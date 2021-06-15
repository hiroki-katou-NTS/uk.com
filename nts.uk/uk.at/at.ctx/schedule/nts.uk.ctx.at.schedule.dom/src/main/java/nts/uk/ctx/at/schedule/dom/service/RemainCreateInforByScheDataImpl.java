package nts.uk.ctx.at.schedule.dom.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.ScheRemainCreateInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TimeDigestionUsageInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.TreatmentOfVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.VacationTimeInforNew;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByScheData;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
@Stateless
public class RemainCreateInforByScheDataImpl implements RemainCreateInforByScheData{
	@Inject
	private BasicScheduleRepository scheRepos;
	
	@Inject
	private WorkScheduleRepository workScheRepo;
	@Override
	public List<ScheRemainCreateInfor> createRemainInfor(CacheCarrier cacheCarrier, String cid, String sid, DatePeriod dateData) {
		//ドメインモデル「勤務予定基本情報」を取得する
		List<BasicSchedule> lstScheData = scheRepos.getBasicScheduleBySidPeriodDate(sid, dateData);
		
		return this.lstResult(lstScheData, sid);
	}

	private List<ScheRemainCreateInfor> lstResult(List<BasicSchedule> lstScheData, String sid){
		List<ScheRemainCreateInfor> lstOutputData = new ArrayList<>();
		lstScheData.stream().forEach(scheData -> {
			ScheRemainCreateInfor outData = new ScheRemainCreateInfor(sid,
					scheData.getDate(),
					scheData.getWorkTypeCode(),
					scheData.getWorkTimeCode() == null ? Optional.empty() : Optional.of(scheData.getWorkTimeCode()),
					0,
					0,
					Collections.emptyList(),
					Optional.ofNullable(null),
					null,
					Optional.of(TreatmentOfVacation.AFTERNOONPICKUP),//TODO xac nhan lai vi domain duoc sua nhung chua code
					false,
					scheData.getConfirmedAtr() == ConfirmedAtr.CONFIRMED ? true : false); //TODO xac nhna lai
			lstOutputData.add(outData);
		});		
		return lstOutputData;
	}
	
	@Override
	public List<ScheRemainCreateInfor> createRemainInforNew(String sid,
			List<GeneralDate> dates) {
		
		DatePeriod period = new DatePeriod(dates.stream().min(Comparator.comparing(GeneralDate::date)).get(),
				dates.stream().max(Comparator.comparing(GeneralDate::date)).get());
		// 勤務予定を取得する
		List<WorkSchedule> sches = this.workScheRepo.getListBySid(sid, period);
		//(Imported)「残数作成元の勤務予定を取得する」
		//残数作成元情報を返す
		return this.lstResultFromSche(sches, sid);
	}
	/**
	 * 
	 * @param sches
	 * @param sid
	 * @return
	 */
	private List<ScheRemainCreateInfor> lstResultFromSche(List<WorkSchedule> sches, String sid) {
		//残数作成元情報Listを作成する
		return	sches.stream()
			.map(
					//残数作成元情報を作成する
						x -> {
							ScheRemainCreateInfor result = new ScheRemainCreateInfor();
							WorkTimeCode wkTime = x.getWorkInfo().getRecordInfo().getWorkTimeCode();
							result.setSid(sid);
							result.setYmd(x.getYmd());
							result.setWorkTypeCode(x.getWorkInfo().getRecordInfo().getWorkTypeCode().v());
							result.setWorkTimeCode(
									Optional.ofNullable(wkTime == null? null: wkTime.v()));
							// 残業振替時間の合計を算出する
							result.setTransferOvertimesTotal(getTotalOvertimeTransferTime(x.getOptAttendanceTime()));
							// 休出振替時間の合計を算出する
							result.setTransferTotal(getTransferTotal(x.getOptAttendanceTime()));
							// 時間休暇使用情報を作成する
							result.setLstVacationTimeInfor(getLstVacationTimeInfor(x.getOptAttendanceTime()));
							// 時間消化使用情報を作成する
							result.setTimeDigestionUsageInfor(getTimeDigestionUsageInfor(x.getOptAttendanceTime()));
							// 振休振出として扱う日数：日別勤怠の勤務情報．振休振出として扱う日数
							result.setNumberDaySuspension(x.getWorkInfo().getNumberDaySuspension());
							return result;
							// 作成された残数作成元情報をListに追加する
						})
				.collect(Collectors.toList());
	}

	public static int getTotalOvertimeTransferTime(Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime) {

		if (!optAttendanceTime.isPresent()) {
			return 0;
		}

		Optional<OverTimeOfDaily> overTimeWork = optAttendanceTime.get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork();
		Integer overTimes = 0;
		if (overTimeWork.isPresent()) {
			List<OverTimeFrameTime> overTimeWorkFrameTime = overTimeWork.get().getOverTimeWorkFrameTime();
			for (OverTimeFrameTime overTimeFrameTime : overTimeWorkFrameTime) {
				overTimes += overTimeFrameTime.getTransferTime().getTime().v();
			}
		}
		return overTimes;
	}
	
	public static int getTransferTotal(Optional<AttendanceTimeOfDailyAttendance> optAttendanceTime) {
		if (!optAttendanceTime.isPresent()) {
			return 0;
		}

		Optional<HolidayWorkTimeOfDaily> workHolidayTime = optAttendanceTime.get().getActualWorkingTimeOfDaily()
				.getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime();
		Integer transferTotal = 0;
		if (workHolidayTime.isPresent()) {
			List<HolidayWorkFrameTime> holidayWorkFrameTime = workHolidayTime.get().getHolidayWorkFrameTime();
			for (HolidayWorkFrameTime holidayWork : holidayWorkFrameTime) {
				transferTotal += holidayWork.getTransferTime().isPresent()
						? holidayWork.getTransferTime().get().getTime().v() : 0;
			}
		}
		return transferTotal;
	}
	
	/**
	 * 時間休暇使用情報を作成する
	 * @param optional 
	 * @return List<時間休暇使用情報>
	 */
	public List<VacationTimeInforNew> getLstVacationTimeInfor(Optional<AttendanceTimeOfDailyAttendance> attenTime){

		List<VacationTimeInforNew> result = new ArrayList<VacationTimeInforNew>();		
		
		if(!attenTime.isPresent()){
			return result;
		}
		
		List<LateTimeOfDaily> lateTimes =  attenTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily();
		
		if (!lateTimes.isEmpty()) {
			// 日別勤怠の遅刻時間.勤怠NO = 1
			lateTimes.stream().filter(x -> x.getWorkNo().v() == 1).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromLateDomain(x,AppTimeType.ATWORK));});
		
			// 日別勤怠の遅刻時間.勤怠NO = 2
			lateTimes.stream().filter(x -> x.getWorkNo().v() == 2).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromLateDomain(x,AppTimeType.ATWORK2));});
		}
		
		List<LeaveEarlyTimeOfDaily> earlyTimes =  attenTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily();
		
		if (!earlyTimes.isEmpty()) {
			// 日別勤怠の早退時間.勤怠NO = 1
			earlyTimes.stream().filter(x -> x.getWorkNo().v() == 1).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromEarlyDomain(x,AppTimeType.OFFWORK));});
					
			// 日別勤怠の早退時間.勤怠NO = 2
			earlyTimes.stream().filter(x -> x.getWorkNo().v() == 2).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromEarlyDomain(x,AppTimeType.OFFWORK2));});
		}
		
		List<OutingTimeOfDaily> outingTimes =  attenTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getOutingTimeOfDailyPerformance();
		
		if (!outingTimes.isEmpty()) {
			// 日別勤怠の外出時間.外出時間 = 私用
			outingTimes.stream().filter(x -> x.getReason().equals(GoingOutReason.PRIVATE)).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromOutDomain(x,AppTimeType.PRIVATE));});
					
			// 日別勤怠の外出時間.外出時間 = 組合
			outingTimes.stream().filter(x -> x.getReason().equals(GoingOutReason.UNION)).findFirst().ifPresent(x -> {result.add(VacationTimeInforNew.fromOutDomain(x,AppTimeType.UNION));});
		}
		
		return result;
	}
	/**
	 * 時間消化使用情報を作成する
	 * @param attenTime 
	 * @return 時間消化使用情報
	 */
	public TimeDigestionUsageInfor getTimeDigestionUsageInfor(Optional<AttendanceTimeOfDailyAttendance> attenTime) {
		
		TimeDigestionUsageInfor result = new  TimeDigestionUsageInfor();
		if(!attenTime.isPresent()){
			return result;
		}
		
		HolidayOfDaily holiday = attenTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily();
		//時間年休使用時間 = 年休.時間消化休暇使用時間
		result.setNenkyuTime(holiday.getAnnual().getDigestionUseTime().v());
		//時間代休使用時間 = 代休.時間消化休暇使用時間
		result.setKyukaTime(holiday.getSubstitute().getDigestionUseTime().v());
		//60H超休使用時間 = 超過有休.超過有休使用時間
		result.setHChoukyuTime(holiday.getOverSalary().getDigestionUseTime().v());
		//子の看護使用時間 = 子の看護介護．有償休暇．子の看護＋子の看護介護．無償休暇．子の看護
		result.setChildCareTime(0);
		//介護使用時間 = 子の看護介護．有償休暇．介護＋子の看護介護．無償休暇．介護
		result.setLongCareTime(0);
		
		return result;
	}
}
