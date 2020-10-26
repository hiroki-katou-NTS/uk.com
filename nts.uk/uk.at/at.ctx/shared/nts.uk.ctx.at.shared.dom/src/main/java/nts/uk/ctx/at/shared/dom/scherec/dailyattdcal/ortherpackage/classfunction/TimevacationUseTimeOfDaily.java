package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.HolidayAddtionSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.DeductionOffSetTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.enums.AdditionAtr;
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
	private AttendanceTime TimeAnnualLeaveUseTime;
	//時間代休使用時間
	private AttendanceTime TimeCompensatoryLeaveUseTime;
	//超過有休使用時間
	private AttendanceTime sixtyHourExcessHolidayUseTime;
	//特別休暇使用時間
	private AttendanceTime TimeSpecialHolidayUseTime;
	//特別休暇枠NO
	private Optional<SpecialHdFrameNo> SpecialHolidayFrameNo;
	//子の看護休暇使用時間
	private AttendanceTime TimeChildCareHolidayUseTime;
	//介護休暇使用時間
	private AttendanceTime TimeCareHolidayUseTime;
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
		TimeAnnualLeaveUseTime = timeAnnualLeaveUseTime;
		TimeCompensatoryLeaveUseTime = timeCompensatoryLeaveUseTime;
		this.sixtyHourExcessHolidayUseTime = sixtyHourExcessHolidayUseTime;
		TimeSpecialHolidayUseTime = timeSpecialHolidayUseTime;
		
		SpecialHolidayFrameNo = specialHolidayFrameNo.isPresent()?specialHolidayFrameNo:Optional.empty();
		
		TimeChildCareHolidayUseTime = timeChildCareHolidayUseTime;
		TimeCareHolidayUseTime = timeCareHolidayUseTime;
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
		this.TimeAnnualLeaveUseTime = new AttendanceTime(this.TimeAnnualLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getAnnualLeave().valueAsMinutes());
		this.TimeCompensatoryLeaveUseTime = new AttendanceTime(this.TimeCompensatoryLeaveUseTime.valueAsMinutes() - deductionOffSetTime.getCompensatoryLeave().valueAsMinutes());
		this.sixtyHourExcessHolidayUseTime = new AttendanceTime(this.sixtyHourExcessHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getSixtyHourHoliday().valueAsMinutes());
		this.TimeSpecialHolidayUseTime = new AttendanceTime(this.TimeSpecialHolidayUseTime.valueAsMinutes() - deductionOffSetTime.getSpecialHoliday().valueAsMinutes());
	}

	/**
	 * 合計使用時間の計算
	 * @param holidayAddtionSet
	 * @return
	 */
	public int calcTotalVacationAddTime(Optional<HolidayAddtionSet> holidayAddtionSet,AdditionAtr additionAtr) {
		int result = 0;
		if(additionAtr.isWorkingHoursOnly()&&holidayAddtionSet.isPresent()) {
			if(holidayAddtionSet.get().getAdditionVacationSet().getAnnualHoliday()==NotUseAtr.USE) {
				result = result + this.TimeAnnualLeaveUseTime.valueAsMinutes();
			}
			if(holidayAddtionSet.get().getAdditionVacationSet().getSpecialHoliday()==NotUseAtr.USE) {
				result = result + this.TimeSpecialHolidayUseTime.valueAsMinutes();
			}
			result = result + this.TimeCompensatoryLeaveUseTime.valueAsMinutes()
							+ this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
							+ this.TimeChildCareHolidayUseTime.valueAsMinutes()
							+ this.TimeCareHolidayUseTime.valueAsMinutes();
		}else {
			result = this.TimeAnnualLeaveUseTime.valueAsMinutes()
					+this.TimeCompensatoryLeaveUseTime.valueAsMinutes()
					+this.sixtyHourExcessHolidayUseTime.valueAsMinutes()
					+this.TimeSpecialHolidayUseTime.valueAsMinutes()
					+this.TimeChildCareHolidayUseTime.valueAsMinutes()
					+this.TimeCareHolidayUseTime.valueAsMinutes();
		}	
		return result;
	}
}
