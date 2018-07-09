package nts.uk.ctx.at.record.dom.dailyprocess.calc.ootsuka;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayCalculation;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSetting;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSetCheck;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * 大塚専用処理(実装)
 * @author keisuke_hoshina
 *
 */
@Stateless
public class OotsukaProcessServiceImpl implements OotsukaProcessService{

	@Override
	public WorkType getOotsukaWorkType(WorkType workType,
									   Optional<FixedWorkCalcSetting> calcMethodOfFixWork,
									   TimeLeavingOfDailyPerformance attendanceLeaving,
									   HolidayCalculation holidayCalculation) {
		if(decisionOotsukaMode(workType,calcMethodOfFixWork,attendanceLeaving,holidayCalculation)) {
			return createOotsukaWorkType(workType);
		}
		else {
			return workType;
		}
	}
	
	/**
	 * 大塚用勤務種類の作成
	 * @param workType マスタから取得した勤務種類
	 * @return 作成した勤務種類
	 */
	private WorkType createOotsukaWorkType(WorkType workType) {
		if(workType.getDailyWork().getWorkTypeUnit().isOneDay()) {
			val workTypeSet = workType.getWorkTypeSetByAtr(WorkAtr.OneDay);
		
			val dailyWork = new DailyWork(WorkTypeUnit.OneDay, 
									  WorkTypeClassification.Attendance, 
									  workType.getDailyWork().getMorning(), 
									  workType.getDailyWork().getAfternoon());
		
		
			val createWorkType = new WorkType(workType.getCompanyId(), 
									workType.getWorkTypeCode(), 
									workType.getSymbolicName(), 
									workType.getName(), 
									workType.getAbbreviationName(), 
									workType.getMemo(), 
									dailyWork, 
									workType.getDeprecate(), 
									workType.getCalculateMethod());
			WorkTypeSet createWorkTypeSet = new WorkTypeSet(workTypeSet.getCompanyId(), 
														workTypeSet.getWorkTypeCd(), 
														workTypeSet.getWorkAtr(), 
														workTypeSet.getDigestPublicHd(), 
														workTypeSet.getHolidayAtr(), 
														workTypeSet.getCountHodiday(), 
														workTypeSet.getCloseAtr(), 
														workTypeSet.getSumAbsenseNo(), 
														workTypeSet.getSumSpHodidayNo(), 
														WorkTypeSetCheck.NO_CHECK, 
														WorkTypeSetCheck.NO_CHECK, 
														workTypeSet.getGenSubHodiday(),
														WorkTypeSetCheck.NO_CHECK
														);
			createWorkType.addWorkTypeSet(createWorkTypeSet);
			return createWorkType;
		}
		else {
			val workTypeSetMorning = workType.getWorkTypeSetByAtr(WorkAtr.Monring);
			val workTypeSetAfternoon = workType.getWorkTypeSetByAtr(WorkAtr.Afternoon);
			
			val dailyWork = new DailyWork(WorkTypeUnit.MonringAndAfternoon, 
										  workType.getDailyWork().getOneDay(), 
										  WorkTypeClassification.Attendance, 
										  WorkTypeClassification.Attendance);


			val createWorkType = new WorkType(workType.getCompanyId(), 
											  workType.getWorkTypeCode(), 
											  workType.getSymbolicName(), 
											  workType.getName(), 
											  workType.getAbbreviationName(), 
											  workType.getMemo(), 
											  dailyWork, 
											  workType.getDeprecate(), 
											  workType.getCalculateMethod());
			WorkTypeSet createWorkTypeSetMorning = new WorkTypeSet(workTypeSetMorning.getCompanyId(), 
																   workTypeSetMorning.getWorkTypeCd(), 
																   workTypeSetMorning.getWorkAtr(), 
																   workTypeSetMorning.getDigestPublicHd(), 
																   workTypeSetMorning.getHolidayAtr(), 
																   workTypeSetMorning.getCountHodiday(), 
																   workTypeSetMorning.getCloseAtr(), 
																   workTypeSetMorning.getSumAbsenseNo(), 
																   workTypeSetMorning.getSumSpHodidayNo(), 
																   WorkTypeSetCheck.NO_CHECK, 
																   WorkTypeSetCheck.NO_CHECK, 
																   workTypeSetMorning.getGenSubHodiday(),
																   WorkTypeSetCheck.NO_CHECK
																	);			
			
			WorkTypeSet createWorkTypeSetAfternoon = new WorkTypeSet(workTypeSetAfternoon.getCompanyId(), 
																	 workTypeSetAfternoon.getWorkTypeCd(), 
																	 workTypeSetAfternoon.getWorkAtr(), 
																	 workTypeSetAfternoon.getDigestPublicHd(), 
																	 workTypeSetAfternoon.getHolidayAtr(), 
																	 workTypeSetAfternoon.getCountHodiday(), 
																	 workTypeSetAfternoon.getCloseAtr(), 
																	 workTypeSetAfternoon.getSumAbsenseNo(), 
																	 workTypeSetAfternoon.getSumSpHodidayNo(), 
																	 WorkTypeSetCheck.NO_CHECK, 
																	 WorkTypeSetCheck.NO_CHECK, 
																	 workTypeSetAfternoon.getGenSubHodiday(),
																	 WorkTypeSetCheck.NO_CHECK
																	 );
			createWorkType.addWorkTypeSet(createWorkTypeSetMorning);
			createWorkType.addWorkTypeSet(createWorkTypeSetAfternoon);
			return createWorkType;
		}
	}

	/**
	 * 大塚モード判断処理
	 * @param holidayCalculation 
	 * @return
	 */
	@Override
	public boolean decisionOotsukaMode(WorkType workType,
										Optional<FixedWorkCalcSetting> calcMethodOfFixWork,
										TimeLeavingOfDailyPerformance attendanceLeaving, HolidayCalculation holidayCalculation) {
		//勤務計算をする　＆＆　打刻漏れをしていない
		if(decisionAbleCalc(workType,calcMethodOfFixWork,holidayCalculation) && !attendanceLeaving.isLeakageStamp()) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 日勤務で計算するかどうか判断
	 * @param workType 勤務種類
	 * @param holidayCalculation 
	 * @param isCalcInVacation 休暇時の計算
	 * @return
	 */
	private boolean decisionAbleCalc(WorkType workType,Optional<FixedWorkCalcSetting> calcMethodOfFixWork, HolidayCalculation holidayCalculation) {
		//休暇時の計算を取得
		if(workType != null && holidayCalculation.getIsCalculate().isUse()) {//calcMethodOfFixWork.isPresent()) {
			return workType.getDailyWork().isOneOrHalfAnnualHoliday()
					|| workType.getDailyWork().isOneOrHalfDaySpecHoliday()
					|| workType.getDailyWork().isOneOrHalfDayYearlyReserved();
		}
		//しない
		else {
			return false;
		}
	}
	
	/**
	 * 大塚モード(PCログオン時間で計算した値の埋め込み)
	 */
	@Override
	public IntegrationOfDaily integrationConverter(IntegrationOfDaily fromStamp, IntegrationOfDaily fromPcLogInfo) {
		if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().isPresent()
		 &&fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily() != null
		 &&fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
			//所定内深夜時間の置き換え
			if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily() != null) {
				fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getWithinStatutoryMidNightTime().getTime()
					 .replaceTimeAndCalcDiv(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily().getWithinStatutoryMidNightTime().getTime().getCalcTime());
			}
			if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null) {
				fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime()
					 .replaceTimeAndCalcDiv(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getExcessOfStatutoryMidNightTime().getTime().getCalcTime());
				//残業時間、振替時間、所定外残業深夜時間、フレックスの置き換え
				if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()) {
					//残業、振替
					fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()
				         .setPCLogOnValue(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getOverTimeWorkFrameTime());
					//所定外残業深夜
					if(fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().isPresent()) {
						fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().get().getTime()
						
					         .replaceTimeAndCalcDiv(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getExcessOverTimeWorkMidNightTime().get().getTime().getCalcTime());
					}
					//フレックス時間の置き換え
					fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime()
					
						 .replaceTimeAndCalcDiv(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get().getFlexTime().getFlexTime().getCalcTime());
				}
				if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent()) {
					//休出、振替
					fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get()
					
						 .setPCLogOnValue(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayWorkFrameTime());
					//休出深夜時間
					if(fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayMidNightWork().isPresent()) {
						fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayMidNightWork().get()
						
							 .replaceValueBypcLogInfo(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime());
					
					}
				}
			}
			//ここのみ、現在乖離時間が存在しない 2018.07.05
			if(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor() != null) {
				//加給、所定内加給、所定外加給、特定日加給、所定内特定日加給、所定外特定日加給の置き換え
				fromStamp.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor()
						 .replaceValueByPCLogInfo(fromPcLogInfo.getAttendanceTimeOfDailyPerformance().get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getRaiseSalaryTimeOfDailyPerfor());
			}
		}
		return fromStamp;
	}
}
