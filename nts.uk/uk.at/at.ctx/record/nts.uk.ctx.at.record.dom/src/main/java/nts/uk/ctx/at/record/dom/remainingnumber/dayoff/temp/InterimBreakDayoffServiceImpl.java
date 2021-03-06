package nts.uk.ctx.at.record.dom.remainingnumber.dayoff.temp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetDesignatedTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * ?????????????????????????????????????????????
 * @author shuichu_ishida
 */
@Stateless
@Transactional(value = TxType.REQUIRED)
public class InterimBreakDayoffServiceImpl implements InterimBreakDayoffService {

	/** ???????????????????????????????????? */
	@Inject
	private InterimBreakDayOffMngRepository interimBreakDayoffMngRepo;
	/** ??????????????????????????? */
	@Inject
	private WorkInformationRepository workInfoOfDailyRepo;
	/** ??????????????????????????? */
	@Inject
	private AttendanceTimeRepository attendanceTimeOfDailyRepo;
	/** ????????????????????? */
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject 
	private RecordDomRequireService requireService;
	
	/** ?????? */
	@Override
	public void create(String companyId, String employeeId, DatePeriod period,
			Optional<List<WorkInfoOfDailyPerformance>> workInfoOfDailyList,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailyList) {
		val require = requireService.createRequire();
	
		// ????????????????????????????????????
		List<WorkInfoOfDailyPerformance> targetWorkInfos = new ArrayList<>();
		if (workInfoOfDailyList.isPresent()){
			targetWorkInfos = workInfoOfDailyList.get();
		}
		else {
			targetWorkInfos = this.workInfoOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		}
		if (targetWorkInfos.size() == 0) return;
		
		// ????????????????????????????????????
		List<AttendanceTimeOfDailyPerformance> targetAttdTimes = new ArrayList<>();
		if (attendanceTimeOfDailyList.isPresent()){
			targetAttdTimes = attendanceTimeOfDailyList.get();
		}
		else {
			targetAttdTimes = this.attendanceTimeOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		}
		Map<GeneralDate, AttendanceTimeOfDailyPerformance> targetAttdTimeMap = new HashMap<>();
		for (val targetAttdTime : targetAttdTimes){
			targetAttdTimeMap.putIfAbsent(targetAttdTime.getYmd(), targetAttdTime);
		}
		
		// ?????????????????????
		val workTypes = this.workTypeRepo.findByCompanyId(companyId);
		Map<WorkTypeCode, WorkType> workTypeMap = new HashMap<>();
		for (val workType : workTypes){
			val workTypeCode = workType.getWorkTypeCode();
			workTypeMap.putIfAbsent(workTypeCode, workType);
		}

		// ???????????????????????????
		VacationAddSet vacationAddSet = GetVacationAddSet.get(require, companyId);
		
		for (val targetWorkInfo : targetWorkInfos){

			// ???????????????????????????????????????????????????
			if (targetWorkInfo.getWorkInformation().getRecordInfo() == null) continue;
			val workTypeCode = targetWorkInfo.getWorkInformation().getRecordInfo().getWorkTypeCode();
			if (workTypeCode == null) continue;
			if (!workTypeMap.containsKey(workTypeCode)) return;
			val workType = workTypeMap.get(workTypeCode);
			val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, vacationAddSet, Optional.empty());

			double dayoffDays = workTypeDaysCountTable.getCompensatoryLeaveDays().v();
			if (dayoffDays > 0.0){
				
				// ????????????????????????????????????
				String dayoffGuid = IdentifierUtil.randomUniqueId();
				InterimDayOffMng dayoffMng = new InterimDayOffMng(
						dayoffGuid,
						employeeId,
						targetWorkInfo.getYmd(),
						CreateAtr.RECORD,
						RemainType.SUBHOLIDAY,
						new RequiredTime(0),
						new RequiredDay(dayoffDays),
						new UnOffsetTime(0),
						new UnOffsetDay(dayoffDays),
						Optional.empty()
						);
				this.interimBreakDayoffMngRepo.persistAndUpdateInterimDayOffMng(dayoffMng);
			}
			
			double breakDays = workTypeDaysCountTable.getHolidayWorkDays().v();
			if (breakDays > 0.0 && targetAttdTimeMap.containsKey(targetWorkInfo.getYmd())){
				val targetAttdTime = targetAttdTimeMap.get(targetWorkInfo.getYmd());
				
				boolean isTarget = true;
				
				// ??????????????????????????????
				int totalTransferMinutes = 0;
				val totalWorkingTime = targetAttdTime.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime();
				val holidayWorkOpt = totalWorkingTime.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime();
				if (holidayWorkOpt.isPresent()){
					for (val holidayWorkFrame : holidayWorkOpt.get().getHolidayWorkFrameTime()){
						if (holidayWorkFrame.getTransferTime().get() != null){
							val transferTime = holidayWorkFrame.getTransferTime().get();
							if (transferTime.getTime() != null){
								totalTransferMinutes += transferTime.getTime().v();
							}
						}
					}
				}
				if (totalTransferMinutes <= 0) isTarget = false;

				// ?????????????????????????????????
				String workTimeCd = null;
				if (isTarget){
					if (targetWorkInfo.getWorkInformation().getRecordInfo() != null){
						if (targetWorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode() != null){
							workTimeCd = targetWorkInfo.getWorkInformation().getRecordInfo().getWorkTimeCode().v();
						}
					}
				}
				if (workTimeCd == null) isTarget = false;
				
				// ?????????????????????????????????????????????
				DesignatedTime designatedTime = null;
				if (isTarget){
					val designatedTimeOpt = GetDesignatedTime.get(require, companyId, workTimeCd);
					if (designatedTimeOpt.isPresent()) designatedTime = designatedTimeOpt.get().getDesignatedTime();
				}
				if (designatedTime == null) isTarget = false;
				
				if (isTarget){
					
					// 1??? or ???????????????
					breakDays = 0.5;
					if (totalTransferMinutes >= designatedTime.getOneDayTime().v()){
						breakDays = 1.0;
					}
					
					// ????????????????????????????????????
					String breakGuid = IdentifierUtil.randomUniqueId();
					InterimBreakMng breakMng = new InterimBreakMng(
							breakGuid,
							employeeId,
							targetWorkInfo.getYmd(),
							CreateAtr.RECORD,
							RemainType.BREAK,
							new AttendanceTime(designatedTime.getOneDayTime().v()),
							GeneralDate.ymd(9999, 12, 31),
							new OccurrenceTime(0),
							new OccurrenceDay(breakDays),
							new AttendanceTime(designatedTime.getHalfDayTime().v()),
							new UnUsedTime(0),
							new UnUsedDay(breakDays));
					this.interimBreakDayoffMngRepo.persistAndUpdateInterimBreakMng(breakMng);
				}
			}
		}
	}
	
	/** ?????? */
	@Override
	public void remove(String employeeId, DatePeriod period) {
		
		
		period.datesBetween().forEach(x -> {
			// ????????????????????????????????????
			this.interimBreakDayoffMngRepo.deleteInterimBreakMngBySidAndYmd(employeeId, x);
			// ????????????????????????????????????
			this.interimBreakDayoffMngRepo.deleteInterimDayOffMngBySidAndYmd(employeeId, x);
		});
	}
}
