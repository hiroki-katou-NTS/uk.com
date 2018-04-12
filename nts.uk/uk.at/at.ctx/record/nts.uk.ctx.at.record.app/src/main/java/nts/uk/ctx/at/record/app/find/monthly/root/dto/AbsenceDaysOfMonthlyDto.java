package nts.uk.ctx.at.record.app.find.monthly.root.dto;

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
public class AbsenceDaysOfMonthlyDto {

	/** 欠勤合計日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "欠勤合計日数", layout = "A")
	private Double totalAbsenceDays;

	/** TODO: check list max length */
	/** 欠勤日数: 集計欠勤日数 */
	// @AttendanceItemLayout(jpPropertyName = "欠勤日数", layout = "B", indexField =
	// "absenceFrameNo", listMaxLength = ??)
	private List<AggregateAbsenceDaysDto> absenceDaysList;

	public static AbsenceDaysOfMonthlyDto from(AbsenceDaysOfMonthly domain) {
		AbsenceDaysOfMonthlyDto dto = new AbsenceDaysOfMonthlyDto();
		if (domain != null) {
			dto.setAbsenceDaysList(ConvertHelper.mapTo(domain.getAbsenceDaysList(),
					c -> new AggregateAbsenceDaysDto(c.getValue().getAbsenceFrameNo(),
							c.getValue().getDays() == null ? null : c.getValue().getDays().v())));
			dto.setTotalAbsenceDays(domain.getTotalAbsenceDays() == null ? null : domain.getTotalAbsenceDays().v());
		}
		return dto;
	}
}
