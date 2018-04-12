package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時間 */
public class WorkTimeOfMonthlyDto {

	/** 医療時間: 月別実績の医療時間 */
	@AttendanceItemLayout(jpPropertyName = "医療時間", layout = "A", listMaxLength = 2, listNoIndex = true, enumField = "dayNightAtr")
	private List<MedicalTimeOfMonthlyDto> medical;

	/** 加給時間: 月別実績の加給時間 */
	@AttendanceItemLayout(jpPropertyName = "加給時間", layout = "B", indexField = "bonusPayFrameNo", listMaxLength = 10)
	private List<BonusPayTimeOfMonthlyDto> bonus;

	/** 外出: 月別実績の外出 */
	@AttendanceItemLayout(jpPropertyName = "外出", layout = "C")
	private GoOutOfMonthlyDto goout;

	/** 割増時間: 月別実績の割増時間 */
	@AttendanceItemLayout(jpPropertyName = "割増時間", layout = "D")
	private PremiumTimeOfMonthlyDto premiumTime;

	/** 休憩時間: 月別実績の休憩時間 */
	@AttendanceItemLayout(jpPropertyName = "休憩時間", layout = "E")
	private BreakTimeOfMonthlyDto breakTime;

	/** 深夜時間: 月別実績の深夜時間 */
	@AttendanceItemLayout(jpPropertyName = "深夜時間", layout = "F")
	private MidnightTimeOfMonthlyDto midNightTime;

	/** 遅刻早退: 月別実績の遅刻早退 */
	@AttendanceItemLayout(jpPropertyName = "遅刻早退", layout = "G")
	private LateLeaveEarlyOfMonthlyDto lateLeaveEarly;

	/** 入退門時間: 月別実績の入退門時間 */
	@AttendanceItemLayout(jpPropertyName = "入退門時間", layout = "H")
	private AttendanceLeaveGateTimeOfMonthlyDto attendanceLeave;

	/** 予実差異時間: 月別実績の予実差異時間 */
	@AttendanceItemLayout(jpPropertyName = "予実差異時間", layout = "I")
	@AttendanceItemValue(type = ValueType.INTEGER)
	private Integer budgetTimeVarience;

	/** 予約: 月別実績の予約 */
	@AttendanceItemLayout(jpPropertyName = "予約", layout = "J")
	private ReservationOfMonthlyDto reservation;

	/** 乖離時間: 月別実績の乖離時間 */
	@AttendanceItemLayout(jpPropertyName = "乖離時間", layout = "K", listMaxLength = 10, indexField = "divergenceTimeNo")
	private List<DivergenceTimeOfMonthlyDto> divergenceTimes;

	/** 休日時間: 月別実績の休日時間 */
	@AttendanceItemLayout(jpPropertyName = "休日時間", layout = "L")
	private HolidayTimeOfMonthlyDto holidayTime;

	public static WorkTimeOfMonthlyDto from(WorkTimeOfMonthly domain) {
		WorkTimeOfMonthlyDto dto = new WorkTimeOfMonthlyDto();
		if (domain != null) {
			dto.setMedical(ConvertHelper.mapTo(domain.getMedicalTime(), 
												c -> MedicalTimeOfMonthlyDto.from(c.getValue())));
			dto.setBonus(domain.getBonusPayTime() == null ? new ArrayList<>(): 
					ConvertHelper.mapTo(domain.getBonusPayTime().getBonusPayTime(),
										c -> BonusPayTimeOfMonthlyDto.from(c.getValue())));
			dto.setGoout(GoOutOfMonthlyDto.from(domain.getGoOut()));
			dto.setPremiumTime(PremiumTimeOfMonthlyDto.from(domain.getPremiumTime()));
			dto.setBreakTime(BreakTimeOfMonthlyDto.from(domain.getBreakTime()));
			dto.setMidNightTime(MidnightTimeOfMonthlyDto.from(domain.getMidnightTime()));
			dto.setLateLeaveEarly(LateLeaveEarlyOfMonthlyDto.from(domain.getLateLeaveEarly()));
			dto.setAttendanceLeave(AttendanceLeaveGateTimeOfMonthlyDto.from(domain.getAttendanceLeaveGateTime()));
			dto.setBudgetTimeVarience(domain.getBudgetTimeVarience() == null || domain.getBudgetTimeVarience().getTime() == null 
					? null : domain.getBudgetTimeVarience().getTime().valueAsMinutes());
//			dto.setReservation(reservation);
			dto.setDivergenceTimes(domain.getDivergenceTime() == null ? new ArrayList<>() : 
				ConvertHelper.mapTo(domain.getDivergenceTime().getDivergenceTimeList(), 
					c -> DivergenceTimeOfMonthlyDto.from(c.getValue())));
			dto.setHolidayTime(HolidayTimeOfMonthlyDto.from(domain.getHolidayTime()));
		}
		return dto;
	}

	public WorkTimeOfMonthly toDomain() {
		return WorkTimeOfMonthly.of(BonusPayTimeOfMonthly.of(ConvertHelper.mapTo(bonus, c -> c.toDomain())),
				goout == null ? null : goout.toDomain(), premiumTime == null ? null : premiumTime.toDomain(),
				breakTime == null ? null : breakTime.toDomain(), 
				holidayTime == null ? null : holidayTime.toDomain(),
				midNightTime == null ? null : midNightTime.toDomain(),
				lateLeaveEarly == null ? null : lateLeaveEarly.toDomain(),
				attendanceLeave == null ? null : attendanceLeave.toDomain(),
				BudgetTimeVarienceOfMonthly.of(toAttendanceTimeMonth(budgetTimeVarience)),
				DivergenceTimeOfMonthly.of(ConvertHelper.mapTo(divergenceTimes, c -> c.toDomain())),
				ConvertHelper.mapTo(medical, c -> c.toDomain()));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return time == null ? null : new AttendanceTimeMonth(time);
	}
}
