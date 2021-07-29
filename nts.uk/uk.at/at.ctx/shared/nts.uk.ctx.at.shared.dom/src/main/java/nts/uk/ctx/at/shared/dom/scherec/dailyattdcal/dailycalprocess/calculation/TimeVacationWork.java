package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * 時間休暇WORK
 * @author shuichi_ishida
 */
@Getter
public class TimeVacationWork implements Cloneable {

	/** 時間NO毎の時間 */
	private List<TimeVacationWorkEachNo> eachNo;
	/** 私用外出 */
	private TimevacationUseTimeOfDaily privateOuting;
	/** 組合外出 */
	private TimevacationUseTimeOfDaily unionOuting;
	
	private TimeVacationWork(){
		this.eachNo = new ArrayList<>();
		this.privateOuting = TimevacationUseTimeOfDaily.defaultValue();
		this.unionOuting = TimevacationUseTimeOfDaily.defaultValue();
	}
	
	public static TimeVacationWork of(
			List<TimeVacationWorkEachNo> eachNo,
			TimevacationUseTimeOfDaily privateOuting,
			TimevacationUseTimeOfDaily unionOuting){
	
		TimeVacationWork myclass = new TimeVacationWork();
		myclass.eachNo = eachNo;
		myclass.privateOuting = privateOuting;
		myclass.unionOuting = unionOuting;
		return myclass;
	}
	
	@Override
	public TimeVacationWork clone() {
		TimeVacationWork clone = null;
		try {
			clone = (TimeVacationWork)super.clone();
			clone.eachNo = new ArrayList<>(this.eachNo.stream().map(c -> c.clone()).collect(Collectors.toList()));
			clone.privateOuting = this.privateOuting.clone();
			clone.unionOuting = this.unionOuting.clone();
		}
		catch (Exception e){
			throw new RuntimeException("TimeVacationWorkEachNo clone error.");
		}
		return clone;
	}
	
	public static TimeVacationWork defaultValue(){
		return new TimeVacationWork();
	}
	
	/**
	 * 遅刻と相殺する時間休暇を取得
	 * @param workNo 勤務NO
	 * @return 日別勤怠の時間休暇使用時間
	 */
	public Optional<TimevacationUseTimeOfDaily> getOffsetTimevacationForLate(WorkNo workNo){
		return this.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).map(c -> c.getLate()).findFirst();
	}
	
	/**
	 * 早退と相殺する時間休暇を取得
	 * @param workNo 勤務NO
	 * @return 日別勤怠の時間休暇使用時間
	 */
	public Optional<TimevacationUseTimeOfDaily> getOffsetTimevacationForLeaveEarly(WorkNo workNo){
		return this.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).map(c -> c.getLeaveEarly()).findFirst();
	}

	/**
	 * 加算
	 * @param target 加算対象
	 * @return 加算後
	 */
	public TimeVacationWork add(TimeVacationWork target){
		// 私用外出
		TimevacationUseTimeOfDaily privateOuting = this.privateOuting.add(target.privateOuting);
		// 組合外出
		TimevacationUseTimeOfDaily unionOuting = this.unionOuting.add(target.unionOuting);
		// 勤務NO
		List<WorkNo> workNoList = new ArrayList<>();
		for (TimeVacationWorkEachNo item : this.eachNo)
			if (!workNoList.contains(item.getWorkNo())) workNoList.add(item.getWorkNo()); 
		for (TimeVacationWorkEachNo item : target.eachNo)
			if (!workNoList.contains(item.getWorkNo())) workNoList.add(item.getWorkNo()); 
		// 勤務NO毎の時間
		List<TimeVacationWorkEachNo> newEachNo = new ArrayList<>();
		for (WorkNo workNo : workNoList){
			Optional<TimeVacationWorkEachNo> src = this.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst();
			Optional<TimeVacationWorkEachNo> des = target.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst();
			TimeVacationWorkEachNo adding = null;
			if (src.isPresent()){
				if (des.isPresent()){
					adding = TimeVacationWorkEachNo.of(
							workNo,
							src.get().getLate().add(des.get().getLate()),
							src.get().getLeaveEarly().add(des.get().getLeaveEarly()));
				}
				else{
					adding = TimeVacationWorkEachNo.of(
							workNo,
							src.get().getLate().clone(),
							src.get().getLeaveEarly().clone());
				}
			}
			else{
				if (des.isPresent()){
					adding = TimeVacationWorkEachNo.of(
							workNo,
							des.get().getLate().clone(),
							des.get().getLeaveEarly().clone());
				}
			}
			if (adding != null) newEachNo.add(adding);
		}
		return TimeVacationWork.of(newEachNo, privateOuting, unionOuting);
	}

	/**
	 * 減算
	 * @param target 減算対象
	 * @return 減算後
	 */
	public TimeVacationWork minus(TimeVacationWork target){
		// 私用外出
		TimevacationUseTimeOfDaily privateOuting = this.privateOuting.minus(target.privateOuting);
		// 組合外出
		TimevacationUseTimeOfDaily unionOuting = this.unionOuting.minus(target.unionOuting);
		// 勤務NO
		List<WorkNo> workNoList = new ArrayList<>();
		for (TimeVacationWorkEachNo item : this.eachNo)
			if (!workNoList.contains(item.getWorkNo())) workNoList.add(item.getWorkNo()); 
		for (TimeVacationWorkEachNo item : target.eachNo)
			if (!workNoList.contains(item.getWorkNo())) workNoList.add(item.getWorkNo()); 
		// 勤務NO毎の時間
		List<TimeVacationWorkEachNo> newEachNo = new ArrayList<>();
		for (WorkNo workNo : workNoList){
			Optional<TimeVacationWorkEachNo> src = this.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst();
			Optional<TimeVacationWorkEachNo> des = target.eachNo.stream().filter(c -> c.getWorkNo().equals(workNo)).findFirst();
			TimeVacationWorkEachNo adding = null;
			if (src.isPresent()){
				if (des.isPresent()){
					adding = TimeVacationWorkEachNo.of(
							workNo,
							src.get().getLate().minus(des.get().getLate()),
							src.get().getLeaveEarly().minus(des.get().getLeaveEarly()));
				}
				else{
					adding = TimeVacationWorkEachNo.of(
							workNo,
							src.get().getLate().clone(),
							src.get().getLeaveEarly().clone());
				}
			}
			if (adding != null) newEachNo.add(adding);
		}
		return TimeVacationWork.of(newEachNo, privateOuting, unionOuting);
	}
	
	/**
	 * 就業時間に加算する時間のみ取得
	 * @param holidayAddSet 休暇加算時間設定
	 * @return 時間休暇WORK
	 */
	public TimeVacationWork getValueForAddWorkTime(HolidayAddtionSet holidayAddSet){
		return TimeVacationWork.of(
				this.eachNo.stream().map(c -> c.getValueForAddWorkTime(holidayAddSet)).collect(Collectors.toList()),
				this.privateOuting.getValueForAddWorkTime(holidayAddSet),
				this.unionOuting.getValueForAddWorkTime(holidayAddSet));
	}
	
	/**
	 * 合計
	 * @return 合計時間
	 */
	public AttendanceTime total(){
		int totalMinutes = 0;
		// 私用外出
		totalMinutes += this.privateOuting.totalVacationAddTime();
		// 組合外出
		totalMinutes += this.unionOuting.totalVacationAddTime();
		// 勤務NO毎の時間
		for (TimeVacationWorkEachNo item : this.eachNo){
			// 遅刻
			totalMinutes += item.getLate().totalVacationAddTime();
			// 早退
			totalMinutes += item.getLeaveEarly().totalVacationAddTime();
		}
		return new AttendanceTime(totalMinutes);
	}
}
