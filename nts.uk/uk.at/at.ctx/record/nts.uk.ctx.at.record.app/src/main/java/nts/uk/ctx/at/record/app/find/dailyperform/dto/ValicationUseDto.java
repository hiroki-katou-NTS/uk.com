package nts.uk.ctx.at.record.app.find.dailyperform.dto;

import java.util.Optional;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.ortherpackage.classfunction.TimevacationUseTimeOfDaily;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

/**
 * 日別実績の時間休暇使用時間
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValicationUseDto implements ItemConst {

	/** 時間年休使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = ANNUNAL_LEAVE)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer timeAnnualLeaveUseTime;

	/** 超過有休使用時間 === 60H超休使用時間 */
	@AttendanceItemLayout(layout =LAYOUT_B, jpPropertyName = EXCESS)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer excessHolidayUseTime;

	/** 特別休暇使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = SPECIAL)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer timeSpecialHolidayUseTime;

	/** 時間代休使用時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = COMPENSATORY)
	@AttendanceItemValue(type = ValueType.TIME)
	private Integer timeCompensatoryLeaveUseTime;
	
	/*特別休暇枠NO*/
	public Integer specialHdFrameNo;
	
	/**子の看護休暇使用時間*/
	@AttendanceItemLayout(layout = LAYOUT_E, jpPropertyName = CHILD_CARE)
	@AttendanceItemValue(type = ValueType.TIME)
	public Integer childCareUseTime;
	
	/**介護休暇使用時間*/
	@AttendanceItemLayout(layout = LAYOUT_F, jpPropertyName = CARE)
	@AttendanceItemValue(type = ValueType.TIME)
	public Integer careUseTime;
	
	
	
	public TimevacationUseTimeOfDaily toDomain(){
		return new TimevacationUseTimeOfDaily(
						timeAnnualLeaveUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(timeAnnualLeaveUseTime), 
						timeCompensatoryLeaveUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(timeCompensatoryLeaveUseTime), 
						excessHolidayUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(excessHolidayUseTime), 
						timeSpecialHolidayUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(timeSpecialHolidayUseTime),
						Optional.ofNullable(this.specialHdFrameNo == null ? null : new SpecialHdFrameNo(this.specialHdFrameNo)),
						childCareUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(childCareUseTime),
						careUseTime == null ? AttendanceTime.ZERO : new AttendanceTime(careUseTime));
	}
	
	public static TimevacationUseTimeOfDaily createEmpty(){
		return new TimevacationUseTimeOfDaily(
				AttendanceTime.ZERO,
				AttendanceTime.ZERO, 
				AttendanceTime.ZERO, 
				AttendanceTime.ZERO,
				Optional.empty(),
				AttendanceTime.ZERO,
				AttendanceTime.ZERO);
	}
	
	@Override
	public ValicationUseDto clone(){
		return new ValicationUseDto(
				timeAnnualLeaveUseTime,
				excessHolidayUseTime, 
				timeSpecialHolidayUseTime, 
				timeCompensatoryLeaveUseTime,
				specialHdFrameNo,
				childCareUseTime,
				careUseTime);
	}
}
