package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.app.find.monthly.root.common.CommonDaysOfMonthlyDto;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.times.AttendanceTimesMonth;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.WorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave.LeaveOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays.PayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.SpecificDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AbsenceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AttendanceDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.HolidayWorkDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.PredeterminedDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.RecruitmentDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.SpcVacationDaysOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TemporaryWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.TwoTimesWorkTimesOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkDaysDetailOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.WorkTimesOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務日数 */
public class WorkDaysOfMonthlyDto implements ItemConst {

	/** 休業: 月別実績の休業 */
	@AttendanceItemLayout(jpPropertyName = SUSPENS_WORK, layout = LAYOUT_A)
	private LeaveOfMonthlyDto leave;

	/** 休出日数: 月別実績の休出日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY_WORK, layout = LAYOUT_B)
	private double holidayWorkDays;

	/** 休日日数: 月別実績の休日日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = HOLIDAY, layout = LAYOUT_C)
	private double holidayDays;

	/** 給与用日数: 月別実績の給与用日数 */
	@AttendanceItemLayout(jpPropertyName = FOR_SALARY, layout = LAYOUT_D)
	private PayDaysOfMonthlyDto payDays;

	/** 勤務回数: 月別実績の勤務回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = COUNT, layout = LAYOUT_E)
	private int workTimes;

	/** 勤務日数: 月別実績の勤務日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = DAYS, layout = LAYOUT_F)
	private double workDays;

	/** 欠勤日数: 月別実績の欠勤日数 */
	@AttendanceItemLayout(jpPropertyName = ABSENCE, layout = LAYOUT_G)
	private CommonDaysOfMonthlyDto absenceDays;

	/** 出勤日数: 月別実績の出勤日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = ATTENDANCE, layout = LAYOUT_H)
	private double attendanceDays;

	/** 所定日数: 月別実績の所定日数 */
	@AttendanceItemLayout(jpPropertyName = WITHIN_STATUTORY, layout = LAYOUT_I)
	private PredeterminedDaysOfMonthlyDto predetermineDays;

	/** 特定日数: 月別実績の特定日数 */
	@AttendanceItemLayout(jpPropertyName = SPECIFIC, layout = LAYOUT_J, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<AggregateSpecificDaysDto> specificDays;

	/** 二回勤務回数: 月別実績の二回勤務回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = TWO_TIMES + COUNT, layout = LAYOUT_K)
	private int twoTimesWorkTimes;

	/** 臨時勤務回数: 月別実績の臨時勤務回数 */
	@AttendanceItemValue(type = ValueType.COUNT)
	@AttendanceItemLayout(jpPropertyName = TEMPORARY, layout = LAYOUT_L)
	private int temporaryWorkTimes;
	
	/** 振出日数: 月別実績の振出日数 */
	@AttendanceItemValue(type = ValueType.DAYS)
	@AttendanceItemLayout(jpPropertyName = TRANSFER, layout = LAYOUT_M)
	private double transferdays;

	/** 特別休暇日数: 月別実績の特別休暇日数 */
	@AttendanceItemLayout(jpPropertyName = SPECIAL + HOLIDAY, layout = LAYOUT_N)
	private CommonDaysOfMonthlyDto specialHolidays;
	
	public static WorkDaysOfMonthlyDto from(WorkDaysOfMonthly domain) {
		WorkDaysOfMonthlyDto dto = new WorkDaysOfMonthlyDto();
		if(domain != null) {
			dto.setLeave(LeaveOfMonthlyDto.from(domain.getLeave()));
			dto.setHolidayWorkDays(domain.getHolidayWorkDays() == null || domain.getHolidayWorkDays().getDays() == null
					? 0 : domain.getHolidayWorkDays().getDays().v());
			dto.setHolidayDays(domain.getHolidayDays() == null || domain.getHolidayDays().getDays() == null 
					? 0 : domain.getHolidayDays().getDays().v());
			dto.setPayDays(PayDaysOfMonthlyDto.from(domain.getPayDays()));
			dto.setWorkTimes(domain.getWorkTimes() == null || domain.getWorkTimes().getTimes() == null
					? 0 : domain.getWorkTimes().getTimes().v());
			dto.setWorkDays(domain.getWorkDays() == null || domain.getWorkDays().getDays() == null 
					? 0 : domain.getWorkDays().getDays().v());
			dto.setAbsenceDays(CommonDaysOfMonthlyDto.from(domain.getAbsenceDays()));
			dto.setAttendanceDays(domain.getAttendanceDays() == null || domain.getAttendanceDays().getDays() == null 
					? 0 : domain.getAttendanceDays().getDays().v());
			dto.setPredetermineDays(PredeterminedDaysOfMonthlyDto.from(domain.getPredetermineDays()));
			dto.setSpecificDays(ConvertHelper.mapTo(domain.getSpecificDays().getSpecificDays(), c -> AggregateSpecificDaysDto.from(c.getValue())));
			dto.setTwoTimesWorkTimes(domain.getTwoTimesWorkTimes() == null || domain.getTwoTimesWorkTimes().getTimes() == null 
					? 0 : domain.getTwoTimesWorkTimes().getTimes().v());
			dto.setTemporaryWorkTimes(domain.getTemporaryWorkTimes() == null || domain.getTemporaryWorkTimes().getTimes() == null 
					? 0 : domain.getTemporaryWorkTimes().getTimes().v());
			dto.setTransferdays(domain.getRecruitmentDays().getDays().v());
			dto.setSpecialHolidays(CommonDaysOfMonthlyDto.from(domain.getSpecialVacationDays()));
		}
		return dto;
	}

	public WorkDaysOfMonthly toDomain() {
		return WorkDaysOfMonthly.of(
						AttendanceDaysOfMonthly.of(new AttendanceDaysMonth(attendanceDays)),
						absenceDays == null ? new AbsenceDaysOfMonthly() : absenceDays.toAbsenceDays(), 
						predetermineDays == null ? new PredeterminedDaysOfMonthly() : predetermineDays.toDomain(),
						WorkDaysDetailOfMonthly.of(new AttendanceDaysMonth(workDays)),
						HolidayDaysOfMonthly.of(new AttendanceDaysMonth(holidayDays)), 
						SpecificDaysOfMonthly.of(ConvertHelper.mapTo(specificDays, c -> c.toDomain())),
						HolidayWorkDaysOfMonthly.of(new AttendanceDaysMonth(holidayWorkDays)),
						payDays == null ? new PayDaysOfMonthly() : PayDaysOfMonthly.of(
								new AttendanceDaysMonth(payDays.getPayAttendanceDays()), 
								new AttendanceDaysMonth(payDays.getPayAbsenceDays())),
						WorkTimesOfMonthly.of(new AttendanceTimesMonth(workTimes)),
						TwoTimesWorkTimesOfMonthly.of(new AttendanceTimesMonth(twoTimesWorkTimes)), 
						TemporaryWorkTimesOfMonthly.of(new AttendanceTimesMonth(temporaryWorkTimes)),
						leave == null ? new LeaveOfMonthly() : leave.toDomain(),
						RecruitmentDaysOfMonthly.of(new AttendanceDaysMonth(transferdays)),
						specialHolidays == null ? new SpcVacationDaysOfMonthly() : specialHolidays.toSpcVacationDays());
	}
}
