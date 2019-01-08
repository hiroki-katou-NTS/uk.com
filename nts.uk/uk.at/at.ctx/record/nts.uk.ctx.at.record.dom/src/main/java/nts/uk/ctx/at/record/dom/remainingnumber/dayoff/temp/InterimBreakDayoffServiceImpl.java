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
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.monthly.WorkTypeDaysCountTable;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset.GetDesignatedTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：暫定代休・休出管理データ
 * @author shuichu_ishida
 */
@Stateless
@Transactional(value = TxType.REQUIRED)
public class InterimBreakDayoffServiceImpl implements InterimBreakDayoffService {

	/** 暫定残数管理データ */
	@Inject
	private InterimRemainRepository interimRemainRepo;
	/** 暫定代休・休出管理データ */
	@Inject
	private InterimBreakDayOffMngRepository interimBreakDayoffMngRepo;
	/** 日別実績の勤務情報 */
	@Inject
	private WorkInformationRepository workInfoOfDailyRepo;
	/** 日別実績の勤怠時間 */
	@Inject
	private AttendanceTimeRepository attendanceTimeOfDailyRepo;
	/** 勤務情報の取得 */
	@Inject
	private WorkTypeRepository workTypeRepo;
	/** 休暇加算設定の取得 */
	@Inject
	private GetVacationAddSet getVacationAddSet;
	/** （代休振替設定）指定時間の取得 */
	@Inject
	private GetDesignatedTime getDesignatedTime;
	
	/** 作成 */
	@Override
	public void create(String companyId, String employeeId, DatePeriod period,
			Optional<List<WorkInfoOfDailyPerformance>> workInfoOfDailyList,
			Optional<List<AttendanceTimeOfDailyPerformance>> attendanceTimeOfDailyList) {
		
		// 日別実績の勤務情報を取得
		List<WorkInfoOfDailyPerformance> targetWorkInfos = new ArrayList<>();
		if (workInfoOfDailyList.isPresent()){
			targetWorkInfos = workInfoOfDailyList.get();
		}
		else {
			targetWorkInfos = this.workInfoOfDailyRepo.findByPeriodOrderByYmd(employeeId, period);
		}
		if (targetWorkInfos.size() == 0) return;
		
		// 日別実績の勤怠時間を取得
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
		
		// 勤務情報　取得
		val workTypes = this.workTypeRepo.findByCompanyId(companyId);
		Map<WorkTypeCode, WorkType> workTypeMap = new HashMap<>();
		for (val workType : workTypes){
			val workTypeCode = workType.getWorkTypeCode();
			workTypeMap.putIfAbsent(workTypeCode, workType);
		}

		// 休暇加算設定　取得
		VacationAddSet vacationAddSet = this.getVacationAddSet.get(companyId);
		
		for (val targetWorkInfo : targetWorkInfos){

			// 勤務種類から代休・休出の日数を取得
			if (targetWorkInfo.getRecordInfo() == null) continue;
			val workTypeCode = targetWorkInfo.getRecordInfo().getWorkTypeCode();
			if (workTypeCode == null) continue;
			if (!workTypeMap.containsKey(workTypeCode)) return;
			val workType = workTypeMap.get(workTypeCode);
			val workTypeDaysCountTable = new WorkTypeDaysCountTable(workType, vacationAddSet, Optional.empty());

			double dayoffDays = workTypeDaysCountTable.getCompensatoryLeaveDays().v();
			if (dayoffDays > 0.0){
				
				// 暫定代休管理データを作成
				String dayoffGuid = IdentifierUtil.randomUniqueId();
				InterimRemain remain = new InterimRemain(
						dayoffGuid,
						employeeId,
						targetWorkInfo.getYmd(),
						CreateAtr.RECORD,
						RemainType.SUBHOLIDAY,
						RemainAtr.SINGLE);
				InterimDayOffMng dayoffMng = new InterimDayOffMng(
						dayoffGuid,
						new RequiredTime(0),
						new RequiredDay(dayoffDays),
						new UnOffsetTime(0),
						new UnOffsetDay(dayoffDays));
				this.interimRemainRepo.persistAndUpdateInterimRemain(remain);
				this.interimBreakDayoffMngRepo.persistAndUpdateInterimDayOffMng(dayoffMng);
			}
			
			double breakDays = workTypeDaysCountTable.getHolidayWorkDays().v();
			if (breakDays > 0.0 && targetAttdTimeMap.containsKey(targetWorkInfo.getYmd())){
				val targetAttdTime = targetAttdTimeMap.get(targetWorkInfo.getYmd());
				
				boolean isTarget = true;
				
				// 振替時間の合計を確認
				int totalTransferMinutes = 0;
				val totalWorkingTime = targetAttdTime.getActualWorkingTimeOfDaily().getTotalWorkingTime();
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

				// 就業時間帯コードを確認
				String workTimeCd = null;
				if (isTarget){
					if (targetWorkInfo.getRecordInfo() != null){
						if (targetWorkInfo.getRecordInfo().getWorkTimeCode() != null){
							workTimeCd = targetWorkInfo.getRecordInfo().getWorkTimeCode().v();
						}
					}
				}
				if (workTimeCd == null) isTarget = false;
				
				// （代休振替設定）指定時間を取得
				DesignatedTime designatedTime = null;
				if (isTarget){
					val designatedTimeOpt = this.getDesignatedTime.get(companyId, workTimeCd);
					if (designatedTimeOpt.isPresent()) designatedTime = designatedTimeOpt.get().getDesignatedTime();
				}
				if (designatedTime == null) isTarget = false;
				
				if (isTarget){
					
					// 1日 or 半日か判断
					breakDays = 0.5;
					if (totalTransferMinutes >= designatedTime.getOneDayTime().v()){
						breakDays = 1.0;
					}
					
					// 暫定休出管理データを作成
					String breakGuid = IdentifierUtil.randomUniqueId();
					InterimRemain remain = new InterimRemain(
							breakGuid,
							employeeId,
							targetWorkInfo.getYmd(),
							CreateAtr.RECORD,
							RemainType.BREAK,
							RemainAtr.SINGLE);
					InterimBreakMng breakMng = new InterimBreakMng(
							breakGuid,
							new AttendanceTime(designatedTime.getOneDayTime().v()),
							GeneralDate.ymd(9999, 12, 31),
							new OccurrenceTime(0),
							new OccurrenceDay(breakDays),
							new AttendanceTime(designatedTime.getHalfDayTime().v()),
							new UnUsedTime(0),
							new UnUsedDay(breakDays));
					this.interimRemainRepo.persistAndUpdateInterimRemain(remain);
					this.interimBreakDayoffMngRepo.persistAndUpdateInterimBreakMng(breakMng);
				}
			}
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, DatePeriod period) {

		// 暫定代休管理データを削除
		val dayoffList = this.interimRemainRepo.getRemainBySidPriod(employeeId, period, RemainType.SUBHOLIDAY);
		for (val dayoffData : dayoffList){
			val targetId = dayoffData.getRemainManaID();
			this.interimBreakDayoffMngRepo.deleteInterimDayOffMng(targetId);
			this.interimBreakDayoffMngRepo.deleteBreakDayOffById(targetId, false);
		}
		this.interimRemainRepo.deleteBySidPeriodType(employeeId, period, RemainType.SUBHOLIDAY);

		// 暫定休出管理データを削除
		val breakList = this.interimRemainRepo.getRemainBySidPriod(employeeId, period, RemainType.BREAK);
		for (val breakData : breakList){
			val targetId = breakData.getRemainManaID();
			this.interimBreakDayoffMngRepo.deleteInterimBreakMng(targetId);
			this.interimBreakDayoffMngRepo.deleteBreakDayOffById(targetId, true);
		}
		this.interimRemainRepo.deleteBySidPeriodType(employeeId, period, RemainType.BREAK);
	}
}
