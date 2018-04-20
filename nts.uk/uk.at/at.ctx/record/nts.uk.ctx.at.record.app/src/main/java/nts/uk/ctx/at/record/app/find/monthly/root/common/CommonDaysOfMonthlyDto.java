package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の欠勤日数 */
/** 月別実績の特別休暇日数 */
public class CommonDaysOfMonthlyDto {

	/** 欠勤合計日数: 勤怠月間日数 */
	/** 特別休暇合計日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "合計日数", layout = "A")
	private Double totalAbsenceDays;

	/** 欠勤日数: 集計欠勤日数 */
	/** 特別休暇日数: 集計特別休暇日数 */
	@AttendanceItemLayout(jpPropertyName = "日数", layout = "B", indexField = "frameNo", listMaxLength = 30)
	private List<CommonAggregateDaysDto> daysList;

	public static CommonDaysOfMonthlyDto from(AbsenceDaysOfMonthly domain) {
		CommonDaysOfMonthlyDto dto = new CommonDaysOfMonthlyDto();
		if (domain != null) {
			dto.setDaysList(ConvertHelper.mapTo(domain.getAbsenceDaysList(),
					c -> new CommonAggregateDaysDto(c.getValue().getAbsenceFrameNo(),
							c.getValue().getDays() == null ? null : c.getValue().getDays().v())));
			dto.setTotalAbsenceDays(domain.getTotalAbsenceDays() == null ? null : domain.getTotalAbsenceDays().v());
		}
		return dto;
	}
}
