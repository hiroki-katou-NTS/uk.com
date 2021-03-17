package nts.uk.ctx.at.record.infra.entity.daily.ouen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.ouen.OuenWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenAttendanceTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenMovementTimeEachTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.MedicalCareTimeEachTimeSheet.FullTimeNightShiftAttr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.ExtraTimeItemNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.WorkingHoursUnitPrice;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@NoArgsConstructor
@Table(name = "KRCDT_DAY_TIME_SUP")
public class KrcdtDayOuenTime extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KrcdtDayOuenTimePK pk;

	/** 総労働時間 */
	@Column(name = "TOTAL_TIME")
	public int totalTime;

	/** 所定内時間 */
	@Column(name = "WITHIN_TIME")
	public int withinTime;

	/** 休憩時間 */
	@Column(name = "BREAK_TIME")
	public int breakTime;

	/** 単価 */
	@Column(name = "PRICE_UNIT")
	public int priceUnit;

	/** 金額 */
	@Column(name = "AMOUNT")
	public int amount;

	/** 総移動時間 */
	@Column(name = "MOVE_TOTAL_TIME")
	public int moveTotalTime;

	/** 所定内移動時間 */
	@Column(name = "MOVE_WITHIN_TIME")
	public int moveWithinTime;

	/** 移動休憩時間 */
	@Column(name = "MOVE_BREAK_TIME")
	public int moveBreakTime;

	/** 割増時間1 */
	@Column(name = "PREMIUM_TIME1")
	public int premiumTime1;

	/** 割増時間2 */
	@Column(name = "PREMIUM_TIME2")
	public int premiumTime2;

	/** 割増時間3 */
	@Column(name = "PREMIUM_TIME3")
	public int premiumTime3;

	/** 割増時間4 */
	@Column(name = "PREMIUM_TIME4")
	public int premiumTime4;

	/** 割増時間5 */
	@Column(name = "PREMIUM_TIME5")
	public int premiumTime5;

	/** 割増時間6 */
	@Column(name = "PREMIUM_TIME6")
	public int premiumTime6;

	/** 割増時間7 */
	@Column(name = "PREMIUM_TIME7")
	public int premiumTime7;

	/** 割増時間8 */
	@Column(name = "PREMIUM_TIME8")
	public int premiumTime8;

	/** 割増時間9 */
	@Column(name = "PREMIUM_TIME9")
	public int premiumTime9;

	/** 割増時間10 */
	@Column(name = "PREMIUM_TIME10")
	public int premiumTime10;
	
	/** 割増金額1 */
	@Column(name = "PREMIUM_AMOUNT1")
	public int premiumAmount1;

	/** 割増金額2 */
	@Column(name = "PREMIUM_AMOUNT2")
	public int premiumAmount2;

	/** 割増金額3 */
	@Column(name = "PREMIUM_AMOUNT3")
	public int premiumAmount3;

	/** 割増金額4 */
	@Column(name = "PREMIUM_AMOUNT4")
	public int premiumAmount4;

	/** 割増金額5 */
	@Column(name = "PREMIUM_AMOUNT5")
	public int premiumAmount5;

	/** 割増金額6 */
	@Column(name = "PREMIUM_AMOUNT6")
	public int premiumAmount6;

	/** 割増金額7 */
	@Column(name = "PREMIUM_AMOUNT7")
	public int premiumAmount7;

	/** 割増金額8 */
	@Column(name = "PREMIUM_AMOUNT8")
	public int premiumAmount8;

	/** 割増金額9 */
	@Column(name = "PREMIUM_AMOUNT9")
	public int premiumAmount9;

	/** 割増金額10 */
	@Column(name = "PREMIUM_AMOUNT10")
	public int premiumAmount10;

	/** 移動割増時間1 */
	@Column(name = "MOVE_PREMIUM_TIME1")
	public int movePremiumTime1;

	/** 移動割増時間2 */
	@Column(name = "MOVE_PREMIUM_TIME2")
	public int movePremiumTime2;

	/** 移動割増時間3 */
	@Column(name = "MOVE_PREMIUM_TIME3")
	public int movePremiumTime3;

	/** 移動割増時間4 */
	@Column(name = "MOVE_PREMIUM_TIME4")
	public int movePremiumTime4;

	/** 移動割増時間5 */
	@Column(name = "MOVE_PREMIUM_TIME5")
	public int movePremiumTime5;

	/** 移動の割増時間6 */
	@Column(name = "MOVE_PREMIUM_TIME6")
	public int movePremiumTime6;

	/** 移動割増時間7 */
	@Column(name = "MOVE_PREMIUM_TIME7")
	public int movePremiumTime7;

	/** 移動割増時間8 */
	@Column(name = "MOVE_PREMIUM_TIME8")
	public int movePremiumTime8;

	/** 移動割増時間9 */
	@Column(name = "MOVE_PREMIUM_TIME9")
	public int movePremiumTime9;

	/** 移動割増時間10 */
	@Column(name = "MOVE_PREMIUM_TIME10")
	public int movePremiumTime10;
	
	/** 常勤勤務時間 */
	@Column(name = "NORMAL_WORK_TIME")
	public int normalWorkTime;

	/** 常勤控除時間 */
	@Column(name = "NORMAL_DEDUCTION_TIME")
	public int normalDeductionTime;

	/** 常勤休憩時間 */
	@Column(name = "NORMAL_BREAK_TIME")
	public int normalBreakTime;

	/** 夜勤勤務時間 */
	@Column(name = "NIGHT_WORK_TIME")
	public int nightWorkTime;

	/** 夜勤控除時間 */
	@Column(name = "NIGHT_DEDUCTION_TIME")
	public int nightDeductionTime;

	/** 夜勤休憩時間 */
	@Column(name = "NIGHT_BREAK_TIME")
	public int nightBreakTime;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public OuenWorkTimeOfDailyAttendance domain() {
		
		List<MedicalCareTimeEachTimeSheet> medicalTimes = new ArrayList<>();
		medicalTimes.add(MedicalCareTimeEachTimeSheet.create(
										FullTimeNightShiftAttr.DAY_SHIFT,
										new AttendanceTime(normalWorkTime), 
										new AttendanceTime(normalBreakTime), 
										new AttendanceTime(normalDeductionTime)));
		medicalTimes.add(MedicalCareTimeEachTimeSheet.create(
										FullTimeNightShiftAttr.NIGHT_SHIFT,
										new AttendanceTime(nightWorkTime), 
										new AttendanceTime(nightBreakTime), 
										new AttendanceTime(nightDeductionTime)));
		
		return OuenWorkTimeOfDailyAttendance.create(new SupportFrameNo(pk.ouenNo), 
						OuenAttendanceTimeEachTimeSheet.create(
								new AttendanceTime(totalTime), 
								new AttendanceTime(breakTime), 
								new AttendanceTime(withinTime),
								medicalTimes, 
								premiumTime(premiumTime1, premiumTime2, premiumTime3, premiumTime4, premiumTime5, 
											premiumTime6, premiumTime7, premiumTime8, premiumTime9, premiumTime10,
											premiumAmount1, premiumAmount2, premiumAmount3, premiumAmount4, premiumAmount5,
											premiumAmount6, premiumAmount7, premiumAmount8, premiumAmount9, premiumAmount10)),
						OuenMovementTimeEachTimeSheet.create(
								new AttendanceTime(moveTotalTime), 
								new AttendanceTime(moveBreakTime), 
								new AttendanceTime(moveWithinTime), 
								premiumTime(movePremiumTime1, movePremiumTime2, movePremiumTime3, movePremiumTime4, movePremiumTime5, 
											movePremiumTime6, movePremiumTime7, movePremiumTime8, movePremiumTime9, movePremiumTime10,
											0, 0, 0, 0, 0,0, 0, 0, 0, 0)),
						new AttendanceAmountDaily(amount), 
						new WorkingHoursUnitPrice(priceUnit));
	}
	
	public static List<PremiumTime> premiumTime(int no1, int no2, int no3, 
			int no4, int no5, int no6, int no7, 
			int no8, int no9, int no10,
			int amount1, int amount2, int amount3, int amount4,
			int amount5, int amount6, int amount7, int amount8,
			int amount9, int amount10){
		
		List<PremiumTime> premiumTimes = new ArrayList<>();
		
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(1), new AttendanceTime(no1), new AttendanceAmountDaily(amount1)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(2), new AttendanceTime(no2), new AttendanceAmountDaily(amount2)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(3), new AttendanceTime(no3), new AttendanceAmountDaily(amount3)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(4), new AttendanceTime(no4), new AttendanceAmountDaily(amount4)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(5), new AttendanceTime(no5), new AttendanceAmountDaily(amount5)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(6), new AttendanceTime(no6), new AttendanceAmountDaily(amount6)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(7), new AttendanceTime(no7), new AttendanceAmountDaily(amount7)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(8), new AttendanceTime(no8), new AttendanceAmountDaily(amount8)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(9), new AttendanceTime(no9), new AttendanceAmountDaily(amount9)));
		premiumTimes.add(new PremiumTime(ExtraTimeItemNo.valueOf(10), new AttendanceTime(no10), new AttendanceAmountDaily(amount10)));
		
		return premiumTimes;
	}
	
	public static List<KrcdtDayOuenTime> convert(OuenWorkTimeOfDaily domain) {
		return domain.getOuenTime().stream()
				.map(d -> KrcdtDayOuenTime.convert(domain.getEmpId(), domain.getYmd(), d))
				.collect(Collectors.toList());
	}
	
	public static KrcdtDayOuenTime convert(String empId, GeneralDate ymd, OuenWorkTimeOfDailyAttendance domain) {
		KrcdtDayOuenTime entity = new KrcdtDayOuenTime();
		
		entity.pk = new KrcdtDayOuenTimePK(empId, 
				ymd, domain.getWorkNo().v());
		
		entity.amount = domain.getAmount().v();
		entity.priceUnit = domain.getPriceUnit().v();
		entity.totalTime = domain.getWorkTime().getTotalTime().valueAsMinutes();
		entity.breakTime = domain.getWorkTime().getBreakTime().valueAsMinutes();
		entity.withinTime = domain.getWorkTime().getWithinTime().valueAsMinutes();
		entity.premiumTime1 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 1);
		entity.premiumTime2 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 2);
		entity.premiumTime3 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 3);
		entity.premiumTime4 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 4);
		entity.premiumTime5 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 5);
		entity.premiumTime6 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 6);
		entity.premiumTime7 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 7);
		entity.premiumTime8 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 8);
		entity.premiumTime9 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 9);
		entity.premiumTime10 = getPremiumTime(domain.getWorkTime().getPremiumTime(), 10);
		entity.premiumAmount1 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 1);
		entity.premiumAmount2 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 2);
		entity.premiumAmount3 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 3);
		entity.premiumAmount4 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 4);
		entity.premiumAmount5 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 5);
		entity.premiumAmount6 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 6);
		entity.premiumAmount7 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 7);
		entity.premiumAmount8 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 8);
		entity.premiumAmount9 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 9);
		entity.premiumAmount10 = getPremiumAmount(domain.getWorkTime().getPremiumTime(), 10);
		entity.moveTotalTime = domain.getMoveTime().getTotalMoveTime().valueAsMinutes();
		entity.moveBreakTime = domain.getMoveTime().getBreakTime().valueAsMinutes();
		entity.moveWithinTime = domain.getMoveTime().getWithinMoveTime().valueAsMinutes();
		entity.movePremiumTime1 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 1);
		entity.movePremiumTime2 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 2);
		entity.movePremiumTime3 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 3);
		entity.movePremiumTime4 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 4);
		entity.movePremiumTime5 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 5);
		entity.movePremiumTime6 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 6);
		entity.movePremiumTime7 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 7);
		entity.movePremiumTime8 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 8);
		entity.movePremiumTime9 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 9);
		entity.movePremiumTime10 = getPremiumTime(domain.getMoveTime().getPremiumTime(), 10);
		
		getMedicalTime(domain.getWorkTime().getMedicalTime(), FullTimeNightShiftAttr.DAY_SHIFT).ifPresent(m -> {

			entity.normalWorkTime = m.getWorkTime().valueAsMinutes();
			entity.normalBreakTime = m.getBreakTime().valueAsMinutes();
			entity.normalDeductionTime = m.getDeductionTime().valueAsMinutes();
		});
		getMedicalTime(domain.getWorkTime().getMedicalTime(), FullTimeNightShiftAttr.NIGHT_SHIFT).ifPresent(m -> {

			entity.nightWorkTime = m.getWorkTime().valueAsMinutes();
			entity.nightBreakTime = m.getBreakTime().valueAsMinutes();
			entity.nightDeductionTime = m.getDeductionTime().valueAsMinutes();
		});
		
		return entity;
	}
	
	private static Optional<MedicalCareTimeEachTimeSheet> getMedicalTime(
			List<MedicalCareTimeEachTimeSheet> times, FullTimeNightShiftAttr attr) {
		
		return times.stream().filter(c -> c.getAttr() == attr)
				.findFirst();
	}
	
	private static int getPremiumTime(List<PremiumTime> times, int no) {
		
		return times.stream().filter(c -> c.getPremiumTimeNo().value == no)
				.findFirst().map(c -> c.getPremitumTime().v()).orElse(0);
	}
	
	private static int getPremiumAmount(List<PremiumTime> times, int no) {
		
		return times.stream().filter(c -> c.getPremiumTimeNo().value == no)
				.findFirst().map(c -> c.getPremiumAmount().v()).orElse(0);
	}
}
