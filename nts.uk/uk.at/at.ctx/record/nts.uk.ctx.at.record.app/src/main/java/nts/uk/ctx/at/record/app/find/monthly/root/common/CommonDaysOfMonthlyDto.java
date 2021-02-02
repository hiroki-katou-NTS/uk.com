package nts.uk.ctx.at.record.app.find.monthly.root.common;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.AggregateSpcVacationDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.workdays.SpcVacationDaysOfMonthly;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の欠勤日数 */
/** 月別実績の特別休暇日数 */
public class CommonDaysOfMonthlyDto implements ItemConst, AttendanceItemDataGate {

	/** 欠勤合計日数: 勤怠月間日数 */
	/** 特別休暇合計日数: 勤怠月間日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = TOTAL + DAYS, layout = LAYOUT_A)
	private double totalAbsenceDays;

	/** 欠勤日数: 集計欠勤日数 */
	/** 特別休暇日数: 集計特別休暇日数 */
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_B, indexField = DEFAULT_INDEX_FIELD_NAME, listMaxLength = 30)
	private List<CommonAggregateDaysDto> daysList;
	
	/** 欠勤合計時間: 勤怠月間時間 */
	/** 特別休暇合計時間: 勤怠月間時間 */
	@AttendanceItemValue(type = ValueType.TIME)
	@AttendanceItemLayout(jpPropertyName = TOTAL + TIME, layout = LAYOUT_C)
	private int totalAbsenceTime;

	public static CommonDaysOfMonthlyDto from(AbsenceDaysOfMonthly domain) {
		CommonDaysOfMonthlyDto dto = new CommonDaysOfMonthlyDto();
		if (domain != null) {
			dto.setDaysList(ConvertHelper.mapTo(domain.getAbsenceDaysList(),
					c -> new CommonAggregateDaysDto(c.getValue().getAbsenceFrameNo(),
							c.getValue().getDays() == null ? 0 : c.getValue().getDays().v(),
							c.getValue().getTime() == null ? 0 : c.getValue().getTime().v())));
			dto.setTotalAbsenceDays(domain.getTotalAbsenceDays() == null ? 0 : domain.getTotalAbsenceDays().v());
			dto.setTotalAbsenceTime(domain.getTotalAbsenceTime() == null ? 0 : domain.getTotalAbsenceTime().valueAsMinutes());
		}
		return dto;
	}

	public static CommonDaysOfMonthlyDto from(SpcVacationDaysOfMonthly domain) {
		CommonDaysOfMonthlyDto dto = new CommonDaysOfMonthlyDto();
		if (domain != null) {
			dto.setDaysList(ConvertHelper.mapTo(domain.getSpcVacationDaysList(),
					c -> new CommonAggregateDaysDto(c.getValue().getSpcVacationFrameNo(),
							c.getValue().getDays() == null ? 0 : c.getValue().getDays().v(),
							c.getValue().getTime() == null ? 0 : c.getValue().getTime().v())));
			dto.setTotalAbsenceDays(domain.getTotalSpcVacationDays() == null ? 0 : domain.getTotalSpcVacationDays().v());
			dto.setTotalAbsenceTime(domain.getTotalSpcVacationTime() == null ? 0 : domain.getTotalSpcVacationTime().valueAsMinutes());
		}
		return dto;
	}
	
	public AbsenceDaysOfMonthly toAbsenceDays() {
		return AbsenceDaysOfMonthly.of(
					new AttendanceDaysMonth(totalAbsenceDays),
					new AttendanceTimeMonth(totalAbsenceTime),
					ConvertHelper.mapTo(daysList, c -> AggregateAbsenceDays.of(c.getNo(),
									new AttendanceDaysMonth(c.getDays()),
									new AttendanceTimeMonth(c.getTime()))));
	}
	
	public SpcVacationDaysOfMonthly toSpcVacationDays() {
		return SpcVacationDaysOfMonthly.of(
					new AttendanceDaysMonth(totalAbsenceDays),
					new AttendanceTimeMonth(totalAbsenceTime),
					ConvertHelper.mapTo(daysList, c -> AggregateSpcVacationDays.of(c.getNo(),
									new AttendanceDaysMonth(c.getDays()),
									new AttendanceTimeMonth(c.getTime()))));
	}

	@Override
	public Optional<ItemValue> valueOf(String path) {
		switch (path) {
		case (TOTAL + DAYS):
			return Optional.of(ItemValue.builder().value(totalAbsenceDays).valueType(ValueType.DAYS));
		case (TOTAL + TIME):
			return Optional.of(ItemValue.builder().value(totalAbsenceTime).valueType(ValueType.TIME));
		default:
			break;
		}
		return AttendanceItemDataGate.super.valueOf(path);
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (DAYS.equals(path)) {
			return new CommonAggregateDaysDto();
		}
		return AttendanceItemDataGate.super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		if (DAYS.equals(path)) {
			return 30;
		}
		return AttendanceItemDataGate.super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		switch (path) {
		case (TOTAL + DAYS):
		case (TOTAL + TIME):
			return PropType.VALUE;
		case (DAYS):
			return PropType.IDX_LIST;
		default:
			break;
		}
		return AttendanceItemDataGate.super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (DAYS.equals(path)) {
			return (List<T>) daysList;
		}
		return AttendanceItemDataGate.super.gets(path);
	}

	@Override
	public void set(String path, ItemValue value) {
		switch (path) {
		case (TOTAL + DAYS):
			totalAbsenceDays = value.valueOrDefault(0d); break;
		case (TOTAL + TIME):
			totalAbsenceTime = value.valueOrDefault(0); break;
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (DAYS.equals(path)) {
			daysList = (List<CommonAggregateDaysDto>) value;
		}
	}

	
}
