package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.PremiumTimeDto;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet.FullTimeNightShiftAttr;

/** 時間帯別勤怠の医療時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicalCareTimeEachTimeSheetDto implements ItemConst {

	/** 区分：常勤夜勤区分 */
	private int attr;
	
	/** 勤務時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WORKING_TIME + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int workTime;
	
	/** 控除時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = DEDUCTION + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int deductionTime;
	
	/** 休憩時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = BREAK + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int breakTime;
	
	public MedicalCareTimeEachTimeSheet toDomain() {
		return MedicalCareTimeEachTimeSheet.create(
				FullTimeNightShiftAttr.valueOf(String.valueOf(this.attr)),
				new AttendanceTime(this.workTime),
				new AttendanceTime(this.breakTime),
				new AttendanceTime(this.deductionTime));
	}
	
	public static MedicalCareTimeEachTimeSheetDto valueOf(MedicalCareTimeEachTimeSheet domain) {
		return new MedicalCareTimeEachTimeSheetDto(
				domain.getAttr().value,
				domain.getWorkTime().valueAsMinutes(),
				domain.getDeductionTime().valueAsMinutes(),
				domain.getBreakTime().valueAsMinutes());
	}
}
