package nts.uk.ctx.at.record.dom.daily;

import java.util.Optional;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 法定外深夜時間
 * @author keisuke_hoshina
 *
 */
@Getter
public class ExcessOfStatutoryMidNightTime {
	//時間
	private TimeDivergenceWithCalculation time;
	//事前時間
	private AttendanceTime beforeApplicationTime;
	
	public ExcessOfStatutoryMidNightTime(TimeDivergenceWithCalculation time, AttendanceTime beforeApplicationTime) {
		super();
		this.time = time;
		this.beforeApplicationTime = beforeApplicationTime;
	}
	
	
	public void replaceCalcTime(AttendanceTime calcTime) {
		this.time = TimeDivergenceWithCalculation.createTimeWithCalculation(this.getTime().getTime(), calcTime);
	}
	
	/**
	 * 所定外深夜時間の計算 
	 */
	public static ExcessOfStatutoryMidNightTime calcExcessTime(Optional<OverTimeOfDaily> overDaily,Optional<HolidayWorkTimeOfDaily> holidayDaily) {
		TimeDivergenceWithCalculation overTime = TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0));
		TimeDivergenceWithCalculation holidayTime = TimeDivergenceWithCalculation.sameTime(new AttendanceTime(0));
		//残業深夜
		if(overDaily.isPresent() && overDaily.get().getExcessOverTimeWorkMidNightTime().isPresent())
			overTime = overDaily.get().getExcessOverTimeWorkMidNightTime().get().getTime();
		
		//休出深夜
		if(holidayDaily.isPresent() && holidayDaily.get().getHolidayMidNightWork().isPresent())
			holidayTime = holidayDaily.get().getHolidayMidNightWork().get().calcTotalTime();
		//return
		TimeDivergenceWithCalculation totalTime = overTime.addMinutes(holidayTime.getTime(), holidayTime.getCalcTime());
		return new ExcessOfStatutoryMidNightTime(totalTime, new AttendanceTime(0));
	}
	
	/**
	 * 実績超過乖離時間の計算
	 * @return
	 */
	public int calcOverLimitDivergenceTime() {
		return this.getTime().getDivergenceTime().valueAsMinutes();
	}

	/**
	 * 実績超過乖離時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isOverLimitDivergenceTime() {
		return this.calcOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 事前申請超過時間の計算
	 * @return
	 */
	public int calcPreOverLimitDivergenceTime() {
		return this.getTime().getTime().valueAsMinutes() - this.getBeforeApplicationTime().valueAsMinutes();
	}

	/**
	 * 事前申請超過時間が発生しているか判定する
	 * @return 乖離時間が発生している
	 */
	public boolean isPreOverLimitDivergenceTime() {
		return this.calcPreOverLimitDivergenceTime() > 0 ? true:false;
	}
	
	/**
	 * 乖離時間のみ再計算
	 * @return
	 */
	public ExcessOfStatutoryMidNightTime calcDiverGenceTime() {
		return new ExcessOfStatutoryMidNightTime(this.time==null?TimeDivergenceWithCalculation.emptyTime():this.time.calcDiverGenceTime(),this.beforeApplicationTime);
	}
	
	/**
	 * 深夜時間の上限時間調整処理
	 * @param upperTime 上限時間
	 */
	public void controlUpperTime(AttendanceTime upperTime) {
		this.time = TimeDivergenceWithCalculation.createTimeWithCalculation(this.time.getTime().greaterThan(upperTime)?upperTime:this.time.getTime(), 
																			this.time.getCalcTime().greaterThan(upperTime)?upperTime:this.time.getCalcTime()); 
	}



}
