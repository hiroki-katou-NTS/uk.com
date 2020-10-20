package nts.uk.ctx.at.record.app.find.monthly.root.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.WorkTimeOfMonthlyVT;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.HolidayUsageOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.actual.LaborTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.attendanceleave.AttendanceLeaveGateTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.bonuspaytime.BonusPayTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.breaktime.BreakTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.divergencetime.DivergenceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.goout.GoOutOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.interval.IntervalTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.lateleaveearly.LateLeaveEarlyOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.midnighttime.MidnightTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.premiumtime.PremiumTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.timevarience.BudgetTimeVarienceOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.toppage.TopPageDisplayOfMonthly;

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

	/** インターバル時間 */
	@AttendanceItemLayout(jpPropertyName = INTERVAL, layout = LAYOUT_L)
	@AttendanceItemValue(type = ValueType.TIME)
	private int intervalTime;
	
	/** インターバル免除時間 */
	@AttendanceItemLayout(jpPropertyName = INTERVAL + DEDUCTION, layout = LAYOUT_M)
	@AttendanceItemValue(type = ValueType.TIME)
	private int intervalExemptionTime;

	/** 月別実績のトップページ表示用時間 */
	@AttendanceItemLayout(jpPropertyName = TOPPAGE, layout = LAYOUT_H)
	private TopPageDisplayTimeOfMonthlyDto topPage;

	/** 休暇使用時間 */
	@AttendanceItemLayout(jpPropertyName = HOLIDAY + USAGE, layout = LAYOUT_I)
	private HolidayUseMonthlyDto holidayUse;

	/** 労働時間 */
	@AttendanceItemLayout(jpPropertyName = LABOR, layout = LAYOUT_J)
	private LaborTimeMonthlyDto laborTime;

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
			dto.setReservation(ReservationOfMonthlyDto.from(domain.getReservation()));
			dto.setDivergenceTimes(domain.getDivergenceTime() == null ? new ArrayList<>() : 
				ConvertHelper.mapTo(domain.getDivergenceTime().getDivergenceTimeList(), 
					c -> DivergenceTimeOfMonthlyDto.from(c.getValue())));
			dto.setIntervalExemptionTime(domain.getInterval().getExemptionTime().valueAsMinutes());
			dto.setIntervalTime(domain.getInterval().getTime().valueAsMinutes());
			dto.setTopPage(TopPageDisplayTimeOfMonthlyDto.from(domain.getTopPage()));
			dto.setLaborTime(LaborTimeMonthlyDto.from(domain.getLaborTime()));
			dto.setHolidayUse(HolidayUseMonthlyDto.from(domain.getHolidayUseTime()));
		}
		return dto;
	}

	public WorkTimeOfMonthlyVT toDomain() {
		return WorkTimeOfMonthlyVT.of(BonusPayTimeOfMonthly.of(ConvertHelper.mapTo(bonus, c -> c.toDomain())),
				goout == null ? new GoOutOfMonthly() : goout.toDomain(), premiumTime == null ? new PremiumTimeOfMonthly() : premiumTime.toDomain(),
				breakTime == null ? new BreakTimeOfMonthly() : breakTime.toDomain(), 
				reservation.domain(),
				midNightTime == null ? new MidnightTimeOfMonthly() : midNightTime.toDomain(),
				lateLeaveEarly == null ? new LateLeaveEarlyOfMonthly() : lateLeaveEarly.toDomain(),
				attendanceLeave == null ? new AttendanceLeaveGateTimeOfMonthly() : attendanceLeave.toDomain(),
				BudgetTimeVarienceOfMonthly.of(toAttendanceTimeMonthWithMinus(budgetTimeVarience)),
				DivergenceTimeOfMonthly.of(ConvertHelper.mapTo(divergenceTimes, c -> c.toDomain())),
				ConvertHelper.mapTo(medical, c -> c.toDomain()),
				topPage == null ? TopPageDisplayOfMonthly.empty() : topPage.toDomain(),
				IntervalTimeOfMonthly.of(new AttendanceTimeMonth(intervalTime), new AttendanceTimeMonth(intervalExemptionTime)),
				holidayUse == null ? HolidayUsageOfMonthly.empty() : holidayUse.toDomain(),
				laborTime == null ? LaborTimeOfMonthly.empty() : laborTime.toDomain());
	}

	private AttendanceTimeMonthWithMinus toAttendanceTimeMonthWithMinus(Integer time) {
		return new AttendanceTimeMonthWithMinus(time);
	}
}
