package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.WorkTimeOfMonthlyVT;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.attdleavegatetime.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.holidaytime.HolidayTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の勤務時間 */
public class WorkTimeOfMonthlyDto implements ItemConst {

	/** 医療時間: 月別実績の医療時間 */
	@AttendanceItemLayout(jpPropertyName = MEDICAL, layout = LAYOUT_A, listMaxLength = 2, listNoIndex = true, enumField = DEFAULT_ENUM_FIELD_NAME)
	private List<MedicalTimeOfMonthlyDto> medical;

	/** 加給時間: 月別実績の加給時間 */
	@AttendanceItemLayout(jpPropertyName = RAISING_SALARY, layout = LAYOUT_B, indexField = DEFAULT_INDEX_FIELD_NAME, listMaxLength = 10)
	private List<BonusPayTimeOfMonthlyDto> bonus;

	/** 外出: 月別実績の外出 */
	@AttendanceItemLayout(jpPropertyName = GO_OUT, layout = LAYOUT_C)
	private GoOutOfMonthlyDto goout;

	/** 割増時間: 月別実績の割増時間 */
	@AttendanceItemLayout(jpPropertyName = PREMIUM, layout = LAYOUT_D)
	private PremiumTimeOfMonthlyDto premiumTime;

	/** 休憩時間: 月別実績の休憩時間 */
	@AttendanceItemLayout(jpPropertyName = BREAK, layout = LAYOUT_E)
	private BreakTimeOfMonthlyDto breakTime;

	/** 深夜時間: 月別実績の深夜時間 */
	@AttendanceItemLayout(jpPropertyName = LATE_NIGHT, layout = LAYOUT_F)
	private MidnightTimeOfMonthlyDto midNightTime;

	/** 遅刻早退: 月別実績の遅刻早退 */
	@AttendanceItemLayout(jpPropertyName = LATE + LEAVE_EARLY, layout = LAYOUT_G)
	private LateLeaveEarlyOfMonthlyDto lateLeaveEarly;

	/** 入退門時間: 月別実績の入退門時間 */
	@AttendanceItemLayout(jpPropertyName = ATTENDANCE_LEAVE_GATE, layout = LAYOUT_H)
	private AttendanceLeaveGateTimeOfMonthlyDto attendanceLeave;

	/** 予実差異時間: 月別実績の予実差異時間 */
	@AttendanceItemLayout(jpPropertyName = PLAN_ACTUAL_DIFF, layout = LAYOUT_I)
	@AttendanceItemValue(type = ValueType.TIME)
	private int budgetTimeVarience;

	/** 予約: 月別実績の予約 */
	@AttendanceItemLayout(jpPropertyName = RESERVATION, layout = LAYOUT_J)
	private ReservationOfMonthlyDto reservation;

	/** 乖離時間: 月別実績の乖離時間 */
	@AttendanceItemLayout(jpPropertyName = DIVERGENCE, layout = LAYOUT_K, listMaxLength = 10, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<DivergenceTimeOfMonthlyDto> divergenceTimes;

	/** 休日時間: 月別実績の休日時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY, layout = LAYOUT_L)
	private HolidayTimeOfMonthlyDto holidayTime;

	public static WorkTimeOfMonthlyDto from(WorkTimeOfMonthlyVT domain) {
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
					? 0 : domain.getBudgetTimeVarience().getTime().valueAsMinutes());
//			dto.setReservation(reservation);
			dto.setDivergenceTimes(domain.getDivergenceTime() == null ? new ArrayList<>() : 
				ConvertHelper.mapTo(domain.getDivergenceTime().getDivergenceTimeList(), 
					c -> DivergenceTimeOfMonthlyDto.from(c.getValue())));
			dto.setHolidayTime(HolidayTimeOfMonthlyDto.from(domain.getHolidayTime()));
		}
		return dto;
	}

	public WorkTimeOfMonthlyVT toDomain() {
		return WorkTimeOfMonthlyVT.of(BonusPayTimeOfMonthly.of(ConvertHelper.mapTo(bonus, c -> c.toDomain())),
				goout == null ? new GoOutOfMonthly() : goout.toDomain(), premiumTime == null ? new PremiumTimeOfMonthly() : premiumTime.toDomain(),
				breakTime == null ? new BreakTimeOfMonthly() : breakTime.toDomain(), 
				holidayTime == null ? new HolidayTimeOfMonthly() : holidayTime.toDomain(),
				midNightTime == null ? new MidnightTimeOfMonthly() : midNightTime.toDomain(),
				lateLeaveEarly == null ? new LateLeaveEarlyOfMonthly() : lateLeaveEarly.toDomain(),
				attendanceLeave == null ? new AttendanceLeaveGateTimeOfMonthly() : attendanceLeave.toDomain(),
				BudgetTimeVarienceOfMonthly.of(toAttendanceTimeMonth(budgetTimeVarience)),
				DivergenceTimeOfMonthly.of(ConvertHelper.mapTo(divergenceTimes, c -> c.toDomain())),
				ConvertHelper.mapTo(medical, c -> c.toDomain()));
	}

	private AttendanceTimeMonth toAttendanceTimeMonth(Integer time) {
		return new AttendanceTimeMonth(time);
	}
}
