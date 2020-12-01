package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.AdditionAtr;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 日別実績の時間休暇使用時間
 * @author ken_takasu
 *
 */
@Getter
public class TimevacationUseTimeOfDaily {
	
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
	 * 各使用時間から相殺時間を控除する
	 * 
	 * @param deductionOffSetTime
	 */
	public void subtractionDeductionOffSetTime(DeductionOffSetTime deductionOffSetTime) {
		this.timeAnnualLeaveUseTime = new AttendanceTime(this.timeAnnualLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getAnnualLeave().valueAsMinutes());
		this.timeCompensatoryLeaveUseTime = new AttendanceTime(this.timeCompensatoryLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getCompensatoryLeave().valueAsMinutes());
		this.sixtyHourExcessHolidayUseTime = new AttendanceTime(this.sixtyHourExcessHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getSixtyHourHoliday().valueAsMinutes());
		this.timeSpecialHolidayUseTime = new AttendanceTime(this.timeSpecialHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getSpecialHoliday().valueAsMinutes());
	}

	/**
	 * 加算使用時間の計算
	 * @param holidayAddtionSet
	 * @return
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
	 * 
	 * 合計使用時間の計算
	 */
	public int  totalVacationAddTime(){
		int result = 0;
		
		result = this.timeAnnualLeaveUseTime.valueAsMinutes()
				+this.timeCompensatoryLeaveUseTime.valueAsMinutes()
				+this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
				+this.timeSpecialHolidayUseTime.valueAsMinutes()
				+this.timeChildCareHolidayUseTime.valueAsMinutes()
				+this.timeCareHolidayUseTime.valueAsMinutes();
		return result;
	}
}
