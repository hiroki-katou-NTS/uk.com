package nts.uk.ctx.at.record.app.find.dailyperform.workrecord.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.PremiumTimeDto;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenAttendanceTimeEachTimeSheet;

/** 時間帯別勤怠の時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuenAttendanceTimeEachTimeSheetDto implements ItemConst {
	
	/** 総労働時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = TOTAL_LABOR + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int totalTime;
	
	/** 休憩時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = BREAK + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int breakTime;
	
	/** 所定内時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = WITHIN_STATUTORY + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int withinTime;
	
	/** 割増時間：割増時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = PREMIUM + TIME, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<PremiumTimeDto> premiumTimes;
	
	/** 医療時間：医療時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = MEDICAL + TIME, 
			listMaxLength = 2, indexField = DEFAULT_ENUM_FIELD_NAME)
	private List<MedicalCareTimeEachTimeSheetDto> medicalCareTimes;
	
	public OuenAttendanceTimeEachTimeSheet toDomain() {
		return OuenAttendanceTimeEachTimeSheet.create(
				new AttendanceTime(this.totalTime),
				new AttendanceTime(this.breakTime),
				new AttendanceTime(this.withinTime),
				ConvertHelper.mapTo(medicalCareTimes, c -> c.toDomain()),
				ConvertHelper.mapTo(premiumTimes, c -> c.toDomain()));
	}
	
	public static OuenAttendanceTimeEachTimeSheetDto valueOf(OuenAttendanceTimeEachTimeSheet domain) {
		return new OuenAttendanceTimeEachTimeSheetDto(
				domain.getTotalTime().valueAsMinutes(),
				domain.getBreakTime().valueAsMinutes(),
				domain.getWithinTime().valueAsMinutes(),
				ConvertHelper.mapTo(domain.getPremiumTime(), c -> PremiumTimeDto.valueOf(c)),
				ConvertHelper.mapTo(domain.getMedicalTime(), c -> MedicalCareTimeEachTimeSheetDto.valueOf(c)));
	}
}
