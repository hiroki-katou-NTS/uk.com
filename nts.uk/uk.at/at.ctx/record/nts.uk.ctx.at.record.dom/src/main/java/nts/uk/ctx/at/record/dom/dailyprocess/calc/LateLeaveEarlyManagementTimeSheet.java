package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.record.dom.daily.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.record.dom.worktime.primitivevalue.WorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.addsettingofworktime.WorkTimeCalcMethodDetailOfHoliday;

/**
 * 遅刻早退管理時間帯
 * @author ken_takasu
 *
 */
public class LateLeaveEarlyManagementTimeSheet {
	
	private List<LateTimeSheet> lateTimeSheet;
	private List<LeaveEarlyTimeSheet> leaveEarlyTimeSheet;
	private List<LateTimeOfDaily> lateTimeOfDaily;
	private List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily;
	
	/**
	 * 指定した勤務Noに一致する日別実績の遅刻時間を取得する
	 * @author ken_takasu
	 * @param workNo
	 * @return
	 */
	public Optional<LateTimeOfDaily> getLateTimeOfDaily(WorkNo workNo) {
		List<LateTimeOfDaily> lateTimeOfDailyList = this.lateTimeOfDaily.stream().filter(ts -> ts.getWorkNo()==workNo).collect(Collectors.toList());
		if(lateTimeOfDailyList==null) {
			return Optional.empty();
		}
		return Optional.of(lateTimeOfDailyList.get(0));
	}
	
	
	/**
	 * 日別実績の遅刻時間の時間休暇使用時間を取得する
	 * @author ken_takasu
	 * @return
	 */
	public TimevacationUseTimeOfDaily getTimevacationUseTimeOfDaily(WorkNo workNo) {
		LateTimeOfDaily lateTimeOfDaily = getLateTimeOfDaily(workNo).orElse(null);
		TimevacationUseTimeOfDaily timevacationUseTimeOfDaily = new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
		if(lateTimeOfDaily == null) {
			return timevacationUseTimeOfDaily;
		}
		timevacationUseTimeOfDaily = this.getLateTimeOfDaily(workNo).get().getTimePaidUseTime();
		return timevacationUseTimeOfDaily;
	}
	
	
	/**
	 * 指定した勤務Noに一致する日別実績の早退時間を取得する
	 * @author ken_takasu
	 * @param workNo
	 * @return
	 */
	public Optional<LeaveEarlyTimeOfDaily> getLeaveEarlyTimeOfDaily(WorkNo workNo) {
		List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDailyList = this.leaveEarlyTimeOfDaily.stream().filter(ts -> ts.getWorkNo()==workNo).collect(Collectors.toList());
		if(leaveEarlyTimeOfDailyList==null) {
			return Optional.empty();
		}
		return Optional.of(leaveEarlyTimeOfDailyList.get(0));
	}
	
	/**
	 * 日別実績の早退時間の時間休暇使用時間を取得する
	 * @author ken_takasu
	 * @return
	 */
	public TimevacationUseTimeOfDaily getleaveEarlyTimevacationUseTimeOfDaily(WorkNo workNo) {
		LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily = getLeaveEarlyTimeOfDaily(workNo).orElse(null);
		TimevacationUseTimeOfDaily timevacationUseTimeOfDaily = new TimevacationUseTimeOfDaily(new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0),new AttendanceTime(0));
		if(leaveEarlyTimeOfDaily == null) {
			return timevacationUseTimeOfDaily;
		}
		timevacationUseTimeOfDaily = this.getLateTimeOfDaily(workNo).get().getTimePaidUseTime();
		return timevacationUseTimeOfDaily;
	}
	
	
	/**
	 * 遅刻時間の計算
	 * 呼び出す時に勤務No分ループする前提で記載
	 * @author ken_takasu
	 * @return 日別実績の遅刻時間
	 */
	public LateTimeOfDaily calcLateTime(
			boolean clacification,  //日別実績の計算区分.遅刻早退の自動計算設定.遅刻
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday,//休暇の就業時間計算方法詳細
			WorkNo workNo) {
		
		//勤務Noに一致する遅刻時間をListで取得する
		LateTimeSheetList lateTimeSheetList = new LateTimeSheetList(this.lateTimeSheet.stream().filter(ts -> ts.getWorkNo()==workNo).collect(Collectors.toList()));
		
		return lateTimeSheetList.join()
				.map(lateTimeSheet -> {

					//遅刻計上時間の計算 
					TimeWithCalculation lateTime = lateTimeSheet.calcLateForRecordTime(clacification);
					
					//遅刻控除時間の計算 
					TimeWithCalculation lateDeductionTime = lateTimeSheet.calcLateForDeductionTime(workTimeCalcMethodDetailOfHoliday.getDeductLateLeaveEarly(), clacification);

					//相殺時間の計算
					DeductionOffSetTime deductionOffSetTime = lateTimeSheet.calcDeductionOffSetTime(
							getTimevacationUseTimeOfDaily(workNo),
							DeductionAtr.Appropriate);
					
					//計上用時間帯から相殺時間を控除する
					int time =  lateTime.getTime().valueAsMinutes()-deductionOffSetTime.getTotalOffSetTime();
					int calcTime = lateTime.getCalcTime().valueAsMinutes()-deductionOffSetTime.getTotalOffSetTime();
					lateTime = TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(time) , new AttendanceTime(calcTime));
					
					LateTimeOfDaily lateTimeOfDaily = new LateTimeOfDaily(lateTime,lateDeductionTime,workNo,getTimevacationUseTimeOfDaily(workNo));
					return lateTimeOfDaily;
				})
				.orElse(LateTimeOfDaily.noLate(workNo));
	}
	
	
	/**
	 * 早退時間の計算
	 * 呼び出す時に勤務No分ループする前提で記載
	 * @author ken_takasu
	 * @return 日別実績の早退時間
	 */
	public LeaveEarlyTimeOfDaily calcLeaveEarlyTime(
			boolean clacification,  //日別実績の計算区分.遅刻早退の自動計算設定.早退
			WorkTimeCalcMethodDetailOfHoliday workTimeCalcMethodDetailOfHoliday,//休暇の就業時間計算方法詳細
			WorkNo workNo) {
		
		//勤務Noに一致する早退時間をListで取得する
		LeaveEarlyTimeSheetList leaveEarlyTimeSheetList = new LeaveEarlyTimeSheetList(this.leaveEarlyTimeSheet.stream().filter(ts -> ts.getWorkNo()==workNo).collect(Collectors.toList()));
		
		return leaveEarlyTimeSheetList.join()
				.map(leaveEarlyTimeSheet -> {

					//早退計上時間の計算 
					TimeWithCalculation leaveEarlyTime = leaveEarlyTimeSheet.calcLeaveEarlyRecordTime(clacification);
					
					//早退控除時間の計算 
					TimeWithCalculation leaveEarlyDeductionTime = leaveEarlyTimeSheet.calcLeaveEarlyForDeductionTime(workTimeCalcMethodDetailOfHoliday.getDeductLateLeaveEarly(), clacification);

					//相殺時間の計算
					DeductionOffSetTime deductionOffSetTime = leaveEarlyTimeSheet.calcDeductionOffSetTime(
							getTimevacationUseTimeOfDaily(workNo),
							DeductionAtr.Appropriate);
					
					//計上用時間帯から相殺時間を控除する
					int time =  leaveEarlyTime.getTime().valueAsMinutes()-deductionOffSetTime.getTotalOffSetTime();
					int calcTime = leaveEarlyTime.getCalcTime().valueAsMinutes()-deductionOffSetTime.getTotalOffSetTime();
					leaveEarlyTime = TimeWithCalculation.createTimeWithCalculation(new AttendanceTime(time) , new AttendanceTime(calcTime));
					
					LeaveEarlyTimeOfDaily leaveEarlyTimeOfDaily = new LeaveEarlyTimeOfDaily(leaveEarlyTime,leaveEarlyDeductionTime,workNo,getTimevacationUseTimeOfDaily(workNo));
					return leaveEarlyTimeOfDaily;
				})
				.orElse(LeaveEarlyTimeOfDaily.noLeaveEarly(workNo));
	}
	
} 
