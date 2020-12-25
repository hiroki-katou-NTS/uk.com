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
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenMovementTimeEachTimeSheet;

/** 応援別勤務の移動時間 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OuenMovementTimeEachTimeSheetDto implements ItemConst {

	/** 総移動時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = MOVE + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int totalTime;
	
	/** 所定内移動時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_B, jpPropertyName = WITHIN_STATUTORY + MOVE + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int breakTime;
	
	/** 休憩時間：勤怠時間 */
	@AttendanceItemLayout(layout = LAYOUT_C, jpPropertyName = BREAK + TIME)
	@AttendanceItemValue(type = ValueType.TIME)
	private int withinTime;
	
	/** 割増時間：割増時間 */
	@AttendanceItemLayout(layout = LAYOUT_D, jpPropertyName = PREMIUM + TIME, 
			listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<PremiumTimeDto> premiumTimes;
	
	public OuenMovementTimeEachTimeSheet toDomain() {
		return OuenMovementTimeEachTimeSheet.create(
				new AttendanceTime(this.totalTime),
				new AttendanceTime(this.breakTime),
				new AttendanceTime(this.withinTime),
				ConvertHelper.mapTo(premiumTimes, c -> c.toDomain()));
	}
	
	public static OuenMovementTimeEachTimeSheetDto valueOf(OuenMovementTimeEachTimeSheet domain) {
		return new OuenMovementTimeEachTimeSheetDto(
				domain.getTotalMoveTime().valueAsMinutes(),
				domain.getBreakTime().valueAsMinutes(),
				domain.getWithinMoveTime().valueAsMinutes(),
				ConvertHelper.mapTo(domain.getPremiumTime(), c -> PremiumTimeDto.valueOf(c)));
	}
}
