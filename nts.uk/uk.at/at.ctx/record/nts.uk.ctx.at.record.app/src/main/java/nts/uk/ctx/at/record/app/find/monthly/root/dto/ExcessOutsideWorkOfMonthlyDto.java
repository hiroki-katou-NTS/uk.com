package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の時間外超過 */
public class ExcessOutsideWorkOfMonthlyDto {

	/** 月割増合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "月割増合計時間", layout = "A")
	private int monthlyTotalPremiumTime;
	
	/** 時間: 時間外超過 */
	@AttendanceItemLayout(jpPropertyName = "時間", layout = "B", listMaxLength = 50, indexField = "fakeNo")
	private List<ExcessOutsideWorkDto> time;
	
	/** 週割増合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "週割増合計時間", layout = "C")
	private int weeklyTotalPremiumTime;
	
	/** 変形繰越時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "変形繰越時間", layout = "D")
	private int deformationCarryforwardTime;
	
	public ExcessOutsideWorkOfMonthly toDomain() {
		return ExcessOutsideWorkOfMonthly.of(
						new AttendanceTimeMonth(weeklyTotalPremiumTime), 
						new AttendanceTimeMonth(monthlyTotalPremiumTime), 
						new AttendanceTimeMonthWithMinus(deformationCarryforwardTime), 
						ConvertHelper.mapTo(time, c -> c.toDomain()));
	}
	
	public static ExcessOutsideWorkOfMonthlyDto from(ExcessOutsideWorkOfMonthly domain) {
		ExcessOutsideWorkOfMonthlyDto dto = new ExcessOutsideWorkOfMonthlyDto();
		if(domain != null) {
			dto.setDeformationCarryforwardTime(domain.getDeformationCarryforwardTime() == null 
					? 0 : domain.getDeformationCarryforwardTime().valueAsMinutes());
			dto.setMonthlyTotalPremiumTime(domain.getMonthlyTotalPremiumTime() == null 
					? 0 : domain.getMonthlyTotalPremiumTime().valueAsMinutes());
			dto.setWeeklyTotalPremiumTime(domain.getWeeklyTotalPremiumTime() == null 
					? 0 : domain.getWeeklyTotalPremiumTime().valueAsMinutes());
			dto.setTime(ExcessOutsideWorkDto.from(domain.getTime()));
		}
		return dto;
	}
}
