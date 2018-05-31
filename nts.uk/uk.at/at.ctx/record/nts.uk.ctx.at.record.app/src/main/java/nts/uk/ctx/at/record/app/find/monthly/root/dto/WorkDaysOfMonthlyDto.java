package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonDaysOfMonthlyDto;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays.PayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.SpecificDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AttendanceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayWorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TemporaryWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TwoTimesWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkDaysDetailOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkTimesOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務日数 */
public class WorkDaysOfMonthlyDto {

	/** 休業: 月別実績の休業 */
	@AttendanceItemLayout(jpPropertyName = "休業", layout = "A")
	private LeaveOfMonthlyDto leave;

	/** 休出日数: 月別実績の休出日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "休出日数", layout = "B")
	private Double holidayWorkDays;

	/** 休日日数: 月別実績の休日日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "休日日数", layout = "C")
	private Double holidayDays;

	/** 給与用日数: 月別実績の給与用日数 */
	@AttendanceItemLayout(jpPropertyName = "給与用日数", layout = "D")
	private PayDaysOfMonthlyDto payDays;

	/** 勤務回数: 月別実績の勤務回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "勤務回数", layout = "E")
	private Integer workTimes;

	/** 勤務日数: 月別実績の勤務日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "勤務日数", layout = "F")
	private Double workDays;

	/** 欠勤日数: 月別実績の欠勤日数 */
	@AttendanceItemLayout(jpPropertyName = "欠勤日数", layout = "G")
	private CommonDaysOfMonthlyDto absenceDays;

	/** 出勤日数: 月別実績の出勤日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "出勤日数", layout = "H")
	private Double attendanceDays;

	/** 所定日数: 月別実績の所定日数 */
	@AttendanceItemLayout(jpPropertyName = "所定日数", layout = "I")
	private PredeterminedDaysOfMonthlyDto predetermineDays;

	/** 特定日数: 月別実績の特定日数 */
	@AttendanceItemLayout(jpPropertyName = "特定日数", layout = "J", listMaxLength = 10, indexField = "specificDayItemNo")
	private List<AggregateSpecificDaysDto> specificDays;

	/** 二回勤務回数: 月別実績の二回勤務回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "二回勤務回数", layout = "K")
	private Integer twoTimesWorkTimes;

	/** 臨時勤務回数: 月別実績の臨時勤務回数 */
	@AttendanceItemValue(type = ValueType.INTEGER)
	@AttendanceItemLayout(jpPropertyName = "臨時勤務回数", layout = "L")
	private Integer temporaryWorkTimes;
	
	/** 振出日数: 月別実績の振出日数 */
	@AttendanceItemValue(type = ValueType.DOUBLE)
	@AttendanceItemLayout(jpPropertyName = "振出日数", layout = "M")
	private Double transferdays;

	/** 特別休暇日数: 月別実績の特別休暇日数 */
	@AttendanceItemLayout(jpPropertyName = "特別休暇日数", layout = "N")
	private CommonDaysOfMonthlyDto specialHolidays;
	
	public static WorkDaysOfMonthlyDto from(WorkDaysOfMonthly domain) {
		WorkDaysOfMonthlyDto dto = new WorkDaysOfMonthlyDto();
		if(domain != null) {
			dto.setLeave(LeaveOfMonthlyDto.from(domain.getLeave()));
			dto.setHolidayWorkDays(domain.getHolidayWorkDays() == null || domain.getHolidayWorkDays().getDays() == null
					? null : domain.getHolidayWorkDays().getDays().v());
			dto.setHolidayDays(domain.getHolidayDays() == null || domain.getHolidayDays().getDays() == null 
					? null : domain.getHolidayDays().getDays().v());
			dto.setPayDays(PayDaysOfMonthlyDto.from(domain.getPayDays()));
			dto.setWorkTimes(domain.getWorkTimes() == null || domain.getWorkTimes().getTimes() == null
					? null : domain.getWorkTimes().getTimes().v());
			dto.setWorkDays(domain.getWorkDays() == null || domain.getWorkDays().getDays() == null 
					? null : domain.getWorkDays().getDays().v());
			dto.setAbsenceDays(CommonDaysOfMonthlyDto.from(domain.getAbsenceDays()));
			dto.setAttendanceDays(domain.getAttendanceDays() == null || domain.getAttendanceDays().getDays() == null 
					? null : domain.getAttendanceDays().getDays().v());
			dto.setPredetermineDays(PredeterminedDaysOfMonthlyDto.from(domain.getPredetermineDays()));
			dto.setSpecificDays(ConvertHelper.mapTo(domain.getSpecificDays().getSpecificDays(), c -> AggregateSpecificDaysDto.from(c.getValue())));
			dto.setTwoTimesWorkTimes(domain.getTwoTimesWorkTimes() == null || domain.getTwoTimesWorkTimes().getTimes() == null 
					? null : domain.getTwoTimesWorkTimes().getTimes().v());
			dto.setTemporaryWorkTimes(domain.getTemporaryWorkTimes() == null || domain.getTemporaryWorkTimes().getTimes() == null 
					? null : domain.getTemporaryWorkTimes().getTimes().v());
		}
		return dto;
	}

	public WorkDaysOfMonthly toDomain() {
		return WorkDaysOfMonthly.of(
						AttendanceDaysOfMonthly.of(attendanceDays == null ? null : new AttendanceDaysMonth(attendanceDays)),
						absenceDays == null ? null : absenceDays.toAbsenceDays(), predetermineDays == null ? null : predetermineDays.toDomain(),
						workDays == null ? null : WorkDaysDetailOfMonthly.of(new AttendanceDaysMonth(workDays)),
						holidayDays == null ? null : HolidayDaysOfMonthly.of(new AttendanceDaysMonth(holidayDays)), 
						SpecificDaysOfMonthly.of(ConvertHelper.mapTo(specificDays, c -> c.toDomain())),
						holidayWorkDays == null ? null : HolidayWorkDaysOfMonthly.of(new AttendanceDaysMonth(holidayWorkDays)),
						payDays == null ? null : PayDaysOfMonthly.of(
								payDays.getPayAttendanceDays() == null ? null : new AttendanceDaysMonth(payDays.getPayAttendanceDays()), 
								payDays.getPayAbsenceDays() == null ? null : new AttendanceDaysMonth(payDays.getPayAbsenceDays())),
						workTimes == null ? null : WorkTimesOfMonthly.of(new AttendanceTimesMonth(workTimes)),
						twoTimesWorkTimes == null ? null : TwoTimesWorkTimesOfMonthly.of(new AttendanceTimesMonth(twoTimesWorkTimes)), 
						temporaryWorkTimes == null ? null : TemporaryWorkTimesOfMonthly.of(new AttendanceTimesMonth(temporaryWorkTimes)),
						leave == null ? null : leave.toDomain());
	}
}
