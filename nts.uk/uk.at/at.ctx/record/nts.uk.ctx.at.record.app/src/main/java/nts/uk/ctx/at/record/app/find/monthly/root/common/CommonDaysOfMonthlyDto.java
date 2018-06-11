package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の欠勤日数 */
/** 月別実績の特別休暇日数 */
public class CommonDaysOfMonthlyDto implements ItemConst {

	/** 欠勤合計日数: 勤怠月間日数 */
	/** 特別休暇合計日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = TOTAL + DAYS, layout = LAYOUT_A)
	private Double totalAbsenceDays;

	/** 欠勤日数: 集計欠勤日数 */
	/** 特別休暇日数: 集計特別休暇日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B, indexField = DEFAULT_INDEX_FIELD_NAME, listMaxLength = 30)
	private List<CommonAggregateDaysDto> daysList;
	
	/** 欠勤合計時間: 勤怠月間時間 */
	/** 特別休暇合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = TOTAL + TIME, layout = LAYOUT_C)
	private Integer totalAbsenceTime;

	public static CommonDaysOfMonthlyDto from(AbsenceDaysOfMonthly domain) {
		CommonDaysOfMonthlyDto dto = new CommonDaysOfMonthlyDto();
		if (domain != null) {
			dto.setDaysList(ConvertHelper.mapTo(domain.getAbsenceDaysList(),
					c -> new CommonAggregateDaysDto(c.getValue().getAbsenceFrameNo(),
							c.getValue().getDays() == null ? null : c.getValue().getDays().v(),
							c.getValue().getTime() == null ? null : c.getValue().getTime().v())));
			dto.setTotalAbsenceDays(domain.getTotalAbsenceDays() == null ? null : domain.getTotalAbsenceDays().v());
			dto.setTotalAbsenceTime(domain.getTotalAbsenceTime() == null ? null : domain.getTotalAbsenceTime().valueAsMinutes());
		}
		return dto;
	}
	
	public AbsenceDaysOfMonthly toAbsenceDays() {
		return AbsenceDaysOfMonthly.of(
					totalAbsenceDays == null ? null : new AttendanceDaysMonth(totalAbsenceDays),
					totalAbsenceTime == null ? null : new AttendanceTimeMonth(totalAbsenceTime),
					ConvertHelper.mapTo(daysList, c -> AggregateAbsenceDays.of(c.getNo(),
									c.getDays() == null ? null : new AttendanceDaysMonth(c.getDays()),
									c.getTime() == null ? null : new AttendanceTimeMonth(c.getTime()))));
	}
}
