package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.CompanyHolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.holidaypriorityorder.HolidayPriorityOrder;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別勤怠の時間休暇使用時間
 * @author ken_takasu
 */
@Getter
@Setter
public class TimevacationUseTimeOfDaily implements Cloneable {
	
	//時間年休使用時間
	private AttendanceTime timeAnnualLeaveUseTime;
	//時間代休使用時間
	private AttendanceTime timeCompensatoryLeaveUseTime;
	//超過有休使用時間
	private AttendanceTime sixtyHourExcessHolidayUseTime;
	//特別休暇使用時間
	private AttendanceTime timeSpecialHolidayUseTime;
	//特別休暇枠NO
	private Optional<SpecialHdFrameNo> specialHolidayFrameNo;
	//子の看護休暇使用時間
	private AttendanceTime timeChildCareHolidayUseTime;
	//介護休暇使用時間
	private AttendanceTime timeCareHolidayUseTime;
	
	/**
	 * Constructor 
	 */
	public TimevacationUseTimeOfDaily(
			AttendanceTime timeAnnualLeaveUseTime,
			AttendanceTime timeCompensatoryLeaveUseTime, 
			AttendanceTime sixtyHourExcessHolidayUseTime,
			AttendanceTime timeSpecialHolidayUseTime,
			Optional<SpecialHdFrameNo> specialHolidayFrameNo,
			AttendanceTime timeChildCareHolidayUseTime,
			AttendanceTime timeCareHolidayUseTime) {
		super();
		this.timeAnnualLeaveUseTime = timeAnnualLeaveUseTime;
		this.timeCompensatoryLeaveUseTime = timeCompensatoryLeaveUseTime;
		this.sixtyHourExcessHolidayUseTime = sixtyHourExcessHolidayUseTime;
		this.timeSpecialHolidayUseTime = timeSpecialHolidayUseTime;
		
		this.specialHolidayFrameNo = specialHolidayFrameNo.isPresent()?specialHolidayFrameNo:Optional.empty();
		
		this.timeChildCareHolidayUseTime = timeChildCareHolidayUseTime;
		this.timeCareHolidayUseTime = timeCareHolidayUseTime;
	}

	@Override
	public TimevacationUseTimeOfDaily clone() {
		TimevacationUseTimeOfDaily clone = null;
		try {
			clone = (TimevacationUseTimeOfDaily)super.clone();
			clone.timeAnnualLeaveUseTime = new AttendanceTime(this.timeAnnualLeaveUseTime.valueAsMinutes());
			clone.timeCompensatoryLeaveUseTime = new AttendanceTime(this.timeCompensatoryLeaveUseTime.valueAsMinutes());
			clone.sixtyHourExcessHolidayUseTime = new AttendanceTime(this.sixtyHourExcessHolidayUseTime.valueAsMinutes());
			clone.timeSpecialHolidayUseTime = new AttendanceTime(this.timeSpecialHolidayUseTime.valueAsMinutes());
			clone.specialHolidayFrameNo = this.specialHolidayFrameNo.isPresent() ?
					Optional.of(new SpecialHdFrameNo(this.specialHolidayFrameNo.get().v())) : Optional.empty();
			clone.timeChildCareHolidayUseTime = new AttendanceTime(this.timeChildCareHolidayUseTime.valueAsMinutes());
			clone.timeCareHolidayUseTime = new AttendanceTime(this.timeCareHolidayUseTime.valueAsMinutes());
		}
		catch (Exception e){
			throw new RuntimeException("TimevacationUseTimeOfDaily clone error.");
		}
		return clone;
	}
	
	public static TimevacationUseTimeOfDaily defaultValue(){
		return new TimevacationUseTimeOfDaily(
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				new AttendanceTime(0),
				Optional.empty(),
				new AttendanceTime(0),
				new AttendanceTime(0));
	}

	/**
	 * 加算使用時間の計算
	 * @param holidayAddtionSet 休暇加算時間設定
	 * @param additionAtr 就業加算時間のみかどうか
	 * @return 加算使用時間
	 */
	public int calcTotalVacationAddTime(Optional<HolidayAddtionSet> holidayAddtionSet,AdditionAtr additionAtr) {
		int result = 0;
		if(additionAtr.isWorkingHoursOnly()&&holidayAddtionSet.isPresent()) {
			if(holidayAddtionSet.get().getAdditionVacationSet().getAnnualHoliday()==NotUseAtr.USE) {
				result = result + this.timeAnnualLeaveUseTime.valueAsMinutes();
			}
			if(holidayAddtionSet.get().getAdditionVacationSet().getSpecialHoliday()==NotUseAtr.USE) {
				result = result + this.timeSpecialHolidayUseTime.valueAsMinutes();
			}
			result = result + this.timeCompensatoryLeaveUseTime.valueAsMinutes()
							+ this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
							+ this.timeChildCareHolidayUseTime.valueAsMinutes()
							+ this.timeCareHolidayUseTime.valueAsMinutes();
		}else {
			result = this.timeAnnualLeaveUseTime.valueAsMinutes()
					+this.timeCompensatoryLeaveUseTime.valueAsMinutes()
					+this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
					+this.timeSpecialHolidayUseTime.valueAsMinutes()
					+this.timeChildCareHolidayUseTime.valueAsMinutes()
					+this.timeCareHolidayUseTime.valueAsMinutes();
		}	
		return result;
	}
	
	/**
	 * 合計使用時間の計算
	 * @return 合計使用時間
	 */
	public int totalVacationAddTime(){
		int result = 0;
		
		result = this.timeAnnualLeaveUseTime.valueAsMinutes()
				+ this.timeCompensatoryLeaveUseTime.valueAsMinutes()
				+ this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
				+ this.timeSpecialHolidayUseTime.valueAsMinutes()
				+ this.timeChildCareHolidayUseTime.valueAsMinutes()
				+ this.timeCareHolidayUseTime.valueAsMinutes();
		return result;
	}
	
	/**
	 * 引く
	 * @param target 減算対象
	 * @return 日別勤怠の時間休暇使用時間（減算後）
	 */
	public TimevacationUseTimeOfDaily minus(TimevacationUseTimeOfDaily target) {
		return new TimevacationUseTimeOfDaily(
			this.timeAnnualLeaveUseTime.minusMinutes(target.timeAnnualLeaveUseTime.valueAsMinutes()),
			this.timeCompensatoryLeaveUseTime.minusMinutes(target.getTimeCompensatoryLeaveUseTime().valueAsMinutes()),
			this.sixtyHourExcessHolidayUseTime.minusMinutes(target.getSixtyHourExcessHolidayUseTime().valueAsMinutes()),
			this.timeSpecialHolidayUseTime.minusMinutes(target.getTimeSpecialHolidayUseTime().valueAsMinutes()),
			Optional.empty(),
			this.timeChildCareHolidayUseTime.minusMinutes(target.getTimeChildCareHolidayUseTime().valueAsMinutes()),
			this.timeCareHolidayUseTime.minusMinutes(target.getTimeCareHolidayUseTime().valueAsMinutes()));
	}
	
	/**
	 * 加算
	 * @param target 加算対象
	 * @return 日別勤怠の時間休暇使用時間（加算後）
	 */
	public TimevacationUseTimeOfDaily add(TimevacationUseTimeOfDaily target) {
		return new TimevacationUseTimeOfDaily(
			this.timeAnnualLeaveUseTime.addMinutes(target.timeAnnualLeaveUseTime.valueAsMinutes()),
			this.timeCompensatoryLeaveUseTime.addMinutes(target.getTimeCompensatoryLeaveUseTime().valueAsMinutes()),
			this.sixtyHourExcessHolidayUseTime.addMinutes(target.getSixtyHourExcessHolidayUseTime().valueAsMinutes()),
			this.timeSpecialHolidayUseTime.addMinutes(target.getTimeSpecialHolidayUseTime().valueAsMinutes()),
			Optional.empty(),
			this.timeChildCareHolidayUseTime.addMinutes(target.getTimeChildCareHolidayUseTime().valueAsMinutes()),
			this.timeCareHolidayUseTime.addMinutes(target.getTimeCareHolidayUseTime().valueAsMinutes()));
	}

	/**
	 * 就業時間に加算する値だけを取得する
	 * @param holidayAddSet 休暇加算時間設定
	 * @return 加算使用時間
	 */
	public TimevacationUseTimeOfDaily getValueForAddWorkTime(HolidayAddtionSet holidayAddSet){
		// 時間年休使用時間
		AttendanceTime timeAnnual = AttendanceTime.ZERO;
		if (holidayAddSet.getAdditionVacationSet().getAnnualHoliday() == NotUseAtr.USE){
			timeAnnual = this.timeAnnualLeaveUseTime;
		}
		// 時間代休使用時間
		AttendanceTime timeComp = AttendanceTime.ZERO;
		// 超過有休使用時間
		AttendanceTime excessLeave = AttendanceTime.ZERO;
		if (holidayAddSet.getAdditionVacationSet().getAnnualHoliday() == NotUseAtr.USE){
			excessLeave = this.sixtyHourExcessHolidayUseTime;
		}
		// 特別休暇使用時間
		AttendanceTime specialHoliday = AttendanceTime.ZERO;
		if (holidayAddSet.getAdditionVacationSet().getSpecialHoliday() == NotUseAtr.USE){
			specialHoliday = this.timeSpecialHolidayUseTime;
		}
		// 子の看護休暇使用時間
		AttendanceTime childCare = AttendanceTime.ZERO;
		// 介護休暇使用時間
		AttendanceTime care = AttendanceTime.ZERO;
		
		return new TimevacationUseTimeOfDaily(
				timeAnnual, timeComp, excessLeave, specialHoliday, Optional.empty(), childCare, care);
	}
	
	/**
	 * 作る
	 * @param priorityOrder 時間休暇相殺優先順位
	 * @param remainingTime	 時間休暇使用残時間
	 * @return 日別勤怠の時間休暇使用時間
	 */
	public TimevacationUseTimeOfDaily create(
			CompanyHolidayPriorityOrder priorityOrder,
			AttendanceTime remainingTime){
		
		int annualHoliday = 0;
		int subHoliday = 0;
		int sixtyhourHoliday = 0;
		int specialHoliday = 0;
		int childCare = 0;
		int care = 0;
		
		// 時間休暇の優先順を取得する
		for (HolidayPriorityOrder holiday : priorityOrder.getHolidayPriorityOrders()){
			// 時間休暇使用残時間が残っているか確認する
			if (remainingTime.valueAsMinutes() <= 0) break;
			
			switch (holiday) {
			case ANNUAL_HOLIDAY:
				// 年休を相殺する時間を計算する
				annualHoliday = Math.min(this.timeAnnualLeaveUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(annualHoliday);
				break;
			case SUB_HOLIDAY:
				// 代休を相殺する時間を計算する
				subHoliday = Math.min(this.timeCompensatoryLeaveUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(subHoliday);
				break;
			case SIXTYHOUR_HOLIDAY:
				// 超過有休を相殺する時間を計算する
				sixtyhourHoliday = Math.min(this.sixtyHourExcessHolidayUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(sixtyhourHoliday);
				break;
			case SPECIAL_HOLIDAY:
				// 特別休暇を相殺する時間を計算する
				specialHoliday = Math.min(this.timeSpecialHolidayUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(specialHoliday);
				break;
			case CHILD_CARE:
				// 子の看護を相殺する時間を計算する
				childCare = Math.min(this.timeChildCareHolidayUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(childCare);
				break;
			case CARE:
				// 介護を相殺する時間を計算する
				care = Math.min(this.timeCareHolidayUseTime.valueAsMinutes(), remainingTime.valueAsMinutes());
				remainingTime = remainingTime.minusMinutes(care);
				break;
			}
		}
		// 日別勤怠の時間休暇使用時間を作成する
		return new TimevacationUseTimeOfDaily(
				new AttendanceTime(annualHoliday),
				new AttendanceTime(subHoliday),
				new AttendanceTime(sixtyhourHoliday),
				new AttendanceTime(specialHoliday),
				Optional.empty(),
				new AttendanceTime(childCare),
				new AttendanceTime(care));
	}
}
