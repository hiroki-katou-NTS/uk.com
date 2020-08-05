package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.paytime.BonusPayTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.premiumtime.PremiumTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.ConstraintTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.OverTimeFrameTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.OverTimeFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.breaktimegoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkFrameTimeSheet;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.overtimework.FlexTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.overtimework.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailycalprocess.calculation.other.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 *勤務予定の勤怠時間
 * UKDesign.データベース.ER図.就業.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name="KSCDT_SCH_TIME")
@Getter
public class KscdtSchTime extends ContractUkJpaEntity {

	@EmbeddedId
	public KscdtSchTimePK pk;
	/** 会社ID **/
	@Column(name = "CID")
	public String cid;
	
	/** 勤務回数**/
	@Column(name = "COUNT")
	public int count;
	
	/** 総労働時間 **/									
	@Column(name = "TOTAL_TIME")
	public int totalTime;
	
	/** 実働時間 **/
	@Column(name = "TOTAL_TIME_ACT")
	public int totalTimeAct;
	
	/** 所定内 就業時間 **/
	@Column(name = "PRS_WORK_TIME")
	public int prsWorkTime;
	
	/** 所定内 実働時間 **/
	@Column(name = "PRS_WORK_TIME_ACT")
	public int prsWorkTimeAct;
	
	/** 所定内 割増時間 **/
	@Column(name = "PRS_PRIME_TIME")
	public int prsPrimeTime;
	
	/** 所定内 深夜時間 **/
	@Column(name = "PRS_MIDNITE_TIME")
	public int prsMidniteTime;
	
	/** 所定外 残業拘束時間 **/
	@Column(name = "EXT_BIND_TIME_OTW")
	public int extBindTimeOtw;
										
	/** 所定外 休出拘束時間 **/
	@Column(name = "EXT_BIND_TIME_HDW")
	public int extBindTimeHw;
	
	/** 所定外 変形労働 法定内残業時間 **/
	@Column(name = "EXT_VARWK_OTW_TIME_LEGAL")
	public int extVarwkOtwTimeLegal;
	
	/** 所定外 フレックス時間 **/
	@Column(name = "EXT_FLEX_TIME")
	public int extFlexTime;
	
	/** 所定外 フレックス時間 事前申請時間 **/
	@Column(name = "EXT_FLEX_TIME_PREAPP")
	public int extFlexTimePreApp;	
	
	/** 所定外深夜 残業時間 **/
	@Column(name = "EXT_MIDNITE_OTW_TIME")
	public int extMidNiteOtwTime;	

	/** 所定外深夜 休出時間 法定内休日 **/
	@Column(name = "EXT_MIDNITE_HDW_TIME_LGHD")
	public int extMidNiteHdwTimeLghd;
	
	/** 所定外深夜 休出時間 法定外休日**/
	@Column(name = "EXT_MIDNITE_HDW_TIME_ILGHD")
	public int extMidNiteHdwTimeIlghd;
	
	/** 所定外深夜 休出時間 祝日**/
	@Column(name = "EXT_MIDNITE_HDW_TIME_PUBHD")
	public int extMidNiteHdwTimePubhd;
	
	/** 所定外深夜 合計時間**/
	@Column(name = "EXT_MIDNITE_TOTAL")
	public int extMidNiteTotal;
	
	/** 所定外深夜 合計時間 事前申請時間 **/
	@Column(name = "EXT_MIDNITE_TOTAL_PREAPP")
	public int extMidNiteTotalPreApp;
	
	/** インターバル出勤時刻 **/
	@Column(name = "INTERVAL_ATD_CLOCK")
	public int intervalAtdClock;	
	
	/** インターバル時間 **/
	@Column(name = "INTERVAL_TIME")
	public int intervalTime;	
	
	/** 休憩合計時間 **/
	@Column(name = "BRK_TOTAL_TIME")
	public int brkTotalTime;	
	
	/** 年休使用時間 **/
	@Column(name = "HDPAID_TIME")
	public int hdPaidTime;
	
	/** 年休時間消化休暇使用時間 **/
	@Column(name = "HDPAID_HOURLY_TIME")
	public int hdPaidHourlyTime;
	
	/** 代休使用時間 **/
	@Column(name = "HDCOM_TIME")
	public int hdComTime;
	
	/** 代休時間消化休暇使用時間 **/
	@Column(name = "HDCOM_HOURLY_TIME")
	public int hdComHourlyTime;
	
	/** 超過有休使用時間**/
	@Column(name = "HD60H_TIME")
	public int hd60hTime;
	
	/**超過有休時間消化休暇使用時間**/
	@Column(name = "HD60H_HOURLY_TIME")
	public int hd60hHourlyTime;
	
	/**特別休暇使用時間**/
	@Column(name = "HDSP_TIME")
	public int hdspTime;
	
	/**特別休暇時間消化休暇使用時間**/
	@Column(name = "HDSP_HOURLY_TIME")
	public int hdspHourlyTime;
								
	/**積立年休使用時間**/
	@Column(name = "HDSTK_TIME")
	public int hdstkTime;
	
	/** 時間消化休暇使用時間 **/
	@Column(name = "HD_HOURLY_TIME")
	public int hdHourlyTime;
	
	/** 時間消化休暇不足時間 **/
	@Column(name = "HD_HOURLY_SHORTAGE_TIME")
	public int hdHourlyShortageTime;
	
	/** 欠勤時間 **/
	@Column(name = "ABSENCE_TIME")
	public int absenceTime;
	
	/** 休暇加算時間 **/
	@Column(name = "VACATION_ADD_TIME")
	public int vacationAddTime;
	
	/** 時差勤務時間**/
	@Column(name = "STAGGERED_WH_TIME")
	public int staggeredWhTime;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "kscdtSchTime", orphanRemoval = true)
	public KscdtSchBasicInfo basicInfo;
	
	@OneToMany(targetEntity = KscdtSchOvertimeWork.class, mappedBy = "kscdtSchTime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_OVERTIME_WORK")
	public List<KscdtSchOvertimeWork> overtimeWorks;
	
	@OneToMany(targetEntity = KscdtSchHolidayWork.class, mappedBy = "kscdtSchTime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_HOLIDAY_WORK")
	public List<KscdtSchHolidayWork> holidayWorks;
	
	@OneToMany(targetEntity = KscdtSchBonusPay.class, mappedBy = "kscdtSchTime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_BONUSPAY")
	public List<KscdtSchBonusPay> bonusPays;
	
	@OneToMany(targetEntity = KscdtSchPremium.class, mappedBy = "kscdtSchTime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_PREMIUM")
	public List<KscdtSchPremium> premiums;
	
	@OneToMany(targetEntity = KscdtSchShortTime.class, mappedBy = "kscdtSchTime", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinTable(name = "KSCDT_SCH_SHORTTIME")
	public List<KscdtSchShortTime> shortTimes;
	
	/**
	 * 
	 * @param workingTime 勤務予定．勤怠時間．勤務時間
	 * @param sID
	 * @param yMD
	 * @param cID
	 * @return
	 */
	public static KscdtSchTime toEntity(ActualWorkingTimeOfDaily timeOfDailys, String sID, GeneralDate yMD, String cID) {
		KscdtSchTimePK pk = new KscdtSchTimePK(sID, yMD);
		
		
	
		// 勤務予定．勤怠時間．勤務時間．総労働時間
		TotalWorkingTime workingTime = timeOfDailys.getTotalWorkingTime();
		
		WithinStatutoryTimeOfDaily timeOfDaily = workingTime.getWithinStatutoryTimeOfDaily();
		
		// 勤務予定．勤怠時間．勤務時間．総労働時間．所定外時間
		ExcessOfStatutoryTimeOfDaily statutoryTimeOfDaily = workingTime.getExcessOfStatutoryTimeOfDaily();
		
		// 勤務予定．勤怠時間．勤務時間．総労働時間．所定外時間．残業時間
		Optional<OverTimeOfDaily> overTimeOfDaily = statutoryTimeOfDaily.getOverTimeWork();
		
		// 勤務予定．勤怠時間．勤務時間．総労働時間．所定外時間．所定外深夜時間
		ExcessOfStatutoryMidNightTime excessOfStatutoryMidNightTime = statutoryTimeOfDaily.getExcessOfStatutoryMidNightTime();
		
		// 勤務予定．勤怠時間．勤務時間．総労働時間.休暇時間
		HolidayOfDaily holidayOfDaily = workingTime.getHolidayOfDaily();
		
		// create KscdtSchOvertimeWork
		//  勤務予定．勤怠時間．勤務時間.総労働時間.所定外時間.残業枠時間
		List<KscdtSchOvertimeWork> kscdtSchOvertimeWork = overTimeOfDaily.get().getOverTimeWorkFrameTime()
				.stream().map(mapper-> KscdtSchOvertimeWork.toEntity(mapper, cID, sID, yMD)).collect(Collectors.toList());
		
		// create KscdtSchHolidayWork
		// 勤務予定．勤怠時間．勤務時間．総労働時間．所定外時間．休出時間
		HolidayWorkTimeOfDaily workTimeOfDaily = statutoryTimeOfDaily.getWorkHolidayTime().get();
		List<HolidayWorkFrameTimeSheet> holidayWorkFrameTimeSheet = workTimeOfDaily.getHolidayWorkFrameTimeSheet();
		List<HolidayWorkFrameTime> holidayWorkFrameTime = workTimeOfDaily.getHolidayWorkFrameTime();
		List<KscdtSchHolidayWork> kscdtSchHolidayWork = new ArrayList<>();
		for(HolidayWorkFrameTimeSheet x : holidayWorkFrameTimeSheet) {
			KscdtSchHolidayWork work = holidayWorkFrameTime.stream().map(y-> KscdtSchHolidayWork.toEntity(x, y, sID, yMD, cID)).findFirst().get();
			kscdtSchHolidayWork.add(work);
		}
		
		// create KscdtSchBonusPay
		// 勤務予定．勤怠時間．勤務時間．総労働時間．加給時間．割増時間
		List<BonusPayTime> dailyPerfor = workingTime.getRaiseSalaryTimeOfDailyPerfor().getRaisingSalaryTimes();
		List<KscdtSchBonusPay> kscdtSchBonusPay = dailyPerfor.stream().map(mapper-> KscdtSchBonusPay.toEntity(sID, yMD, mapper)).collect(Collectors.toList());
		
		// create KscdtSchPremium
		// 勤務予定．勤怠時間．勤務時間．割増時間．割増時間
		List<PremiumTime> premiumTimes = timeOfDailys.getPremiumTimeOfDailyPerformance().getPremiumTimes();
		List<KscdtSchPremium> kscdtSchPremium = premiumTimes.stream().map(mapper-> KscdtSchPremium.toEntity(mapper, sID, yMD)).collect(Collectors.toList());
		
		// 勤務予定．勤怠時間．勤務時間．総労働時間．短時間勤務時間
		ShortWorkTimeOfDaily result = workingTime.getShotrTimeOfDaily();
		List<KscdtSchShortTime> kscdtSchShortTime = new ArrayList<>();
		kscdtSchShortTime.add(KscdtSchShortTime.toEntity(result, sID, yMD));
		
		// QA
		Integer timeLghd = 0;
		Integer timeIlghd = 0;
		Integer timePubhd = 0;
		if(statutoryTimeOfDaily.getWorkHolidayTime().isPresent()) {
			if(statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayMidNightWork().isPresent()) {
				String check = statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime().get(0).getStatutoryAtr().name();
				
				if(check.equals(StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork.name())) {
					timeLghd = statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime().get(0).getTime().getTime().v();
				}
				
				if(check.equals(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork.name())) {
					timeIlghd = statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime().get(0).getTime().getTime().v();
				}
				
				if(check.equals(StaturoryAtrOfHolidayWork.PublicHolidayWork.name())) {
					timePubhd = statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayMidNightWork().get().getHolidayWorkMidNightTime().get(0).getTime().getTime().v();
				}
			}
		}

		KscdtSchTime kscdtSchTime = new KscdtSchTime(
				pk, 
				cID, // cid
				workingTime.getWorkTimes().v(), // count
				workingTime.getTotalTime().v(), // totalTime
				workingTime.getActualTime().v(), // totalTimeAct
				timeOfDaily.getWorkTime().v(), // prsWorkTime
				timeOfDaily.getActualWorkTime().v(), //prsWorkTimeAct
				timeOfDaily.getWithinPrescribedPremiumTime().v(), // prsPrimeTime
				timeOfDaily.getWithinStatutoryMidNightTime().getTime().getTime().v(), // prsMidniteTime
				overTimeOfDaily.get().getOverTimeWorkSpentAtWork().v(), // extBindTimeOtw
				statutoryTimeOfDaily.getWorkHolidayTime().get().getHolidayTimeSpentAtWork().v(), // extBindTimeHw
				overTimeOfDaily.get().getIrregularWithinPrescribedOverTimeWork().v(), // extVarwkOtwTimeLegal
			    overTimeOfDaily.get().getFlexTime().getFlexTime().getTime().v(), // extFlexTime
			    overTimeOfDaily.get().getFlexTime().getBeforeApplicationTime().v(), // extFlexTimePreApp
			    overTimeOfDaily.get().getExcessOverTimeWorkMidNightTime().get().getTime().getTime().v(), // EXT_MIDNITE_OTW_TIME // extMidNiteOtwTime
				timeLghd, // extMidNiteHdwTimeLghd
				timeIlghd, // extMidNiteHdwTimeIlghd
				timePubhd, //EXT_MIDNITE_HDW_TIME_PUBHD // extMidNiteHdwTimePubhd
				excessOfStatutoryMidNightTime.getTime().getTime().v(), //EXT_MIDNITE_TOTAL // extMidNiteTotal
				excessOfStatutoryMidNightTime.getBeforeApplicationTime().v(), // EXT_MIDNITE_TOTAL_PREAPP - 31 // extMidNiteTotalPreApp
				3, // đang QA 110822 // intervalAtdClock
				4, // đang QA 110822 // intervalTime
				workingTime.getBreakTimeOfDaily().getToRecordTotalTime().getTotalTime().getTime().v(), // 34 // brkTotalTime
				holidayOfDaily.getAnnual().getUseTime().v(), // 35 HDPAID_TIME // hdPaidTime
				holidayOfDaily.getAnnual().getDigestionUseTime().v(), // 36 HDPAID_HOURLY_TIME // hdPaidHourlyTime
				holidayOfDaily.getSubstitute().getUseTime().v(), // hdComTime
				holidayOfDaily.getSubstitute().getDigestionUseTime().v(), // 38 HDCOM_HOURLY_TIME // hdComHourlyTime
				holidayOfDaily.getOverSalary().getUseTime().v(), // hd60hTime
				holidayOfDaily.getOverSalary().getDigestionUseTime().v(), // 40 HD60H_HOURLY_TIME // hd60hHourlyTime
				holidayOfDaily.getSpecialHoliday().getUseTime().v(), // hdspTime
				holidayOfDaily.getSpecialHoliday().getDigestionUseTime().v(), // 42 HDSP_HOURLY_TIME // hdspHourlyTime
				holidayOfDaily.getYearlyReserved().getUseTime().v(), // hdstkTime
				holidayOfDaily.getTimeDigest().getUseTime().v(), // hdHourlyTime
				holidayOfDaily.getTimeDigest().getLeakageTime().v(), // hdHourlyShortageTime 
				holidayOfDaily.getAbsence().getUseTime().v(), // absenceTime
				workingTime.getVacationAddTime().v(), // vacationAddTime
				timeOfDailys.getTimeDifferenceWorkingHours().v(), // staggeredWhTime
				kscdtSchOvertimeWork, 
				kscdtSchHolidayWork, 
				kscdtSchBonusPay, 
				kscdtSchPremium, 
				kscdtSchShortTime);
		return kscdtSchTime;
		
	}
	
	public ActualWorkingTimeOfDaily toDomain() {
		// 拘束差異時間
		AttendanceTime constraintDiffTime = new AttendanceTime(null);
		// 拘束時間
		ConstraintTime constraintTime = new ConstraintTime(null, null);
		// 時差勤務時間
		AttendanceTime timeDiff = new AttendanceTime(staggeredWhTime);
		
		// 総労働時間
		KscdtSchOvertimeWork kscdtSchOvertimeWork = new KscdtSchOvertimeWork();
		KscdtSchHolidayWork kscdtSchHolidayWork = new KscdtSchHolidayWork();

		ExcessOfStatutoryMidNightTime nightTime = new ExcessOfStatutoryMidNightTime(new TimeDivergenceWithCalculation(new AttendanceTime(this.extMidNiteTotal), null, null), new AttendanceTime(this.extMidNiteTotalPreApp));
		
		ExcessOverTimeWorkMidNightTime midNightTimes = new ExcessOverTimeWorkMidNightTime(new TimeDivergenceWithCalculation(new AttendanceTime(extMidNiteTotalPreApp), null, null));
		OverTimeOfDaily overTimeOfDaily = new OverTimeOfDaily(
				new ArrayList<>(),
				new ArrayList<>(),
				Finally.of(midNightTimes),
				new AttendanceTime(extVarwkOtwTimeLegal),
				new FlexTime(TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus (extFlexTime)), new AttendanceTime(extFlexTimePreApp)), 
				new AttendanceTime(extBindTimeOtw));
		
		ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily = new ExcessOfStatutoryTimeOfDaily(nightTime,
				Optional.ofNullable(kscdtSchOvertimeWork.toDomain(overTimeOfDaily)),
				Optional.ofNullable(kscdtSchHolidayWork.toDomain(this.extBindTimeHw, this.extMidNiteHdwTimeLghd, this.extMidNiteHdwTimeIlghd, this.extMidNiteHdwTimePubhd)));
		// raiseSalaryTimeOfDailyPerfor
		KscdtSchBonusPay kscdtSchBonusPay = new KscdtSchBonusPay();
		RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor = new RaiseSalaryTimeOfDailyPerfor(
				kscdtSchBonusPay.toDomain(), null);
		
		// QA 110840
		KscdtSchShortTime kscdtSchShortTime = new KscdtSchShortTime();
		ShortWorkTimeOfDaily shotrTime = null;
		
		// WithinStatutoryMidNightTime
		WithinStatutoryMidNightTime midNightTime = new WithinStatutoryMidNightTime(new TimeDivergenceWithCalculation(new AttendanceTime(prsMidniteTime), null, null));
		WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(new AttendanceTime(this.prsWorkTime), new AttendanceTime(this.prsWorkTimeAct), new AttendanceTime(this.prsPrimeTime), midNightTime);
		
		
		SubstituteHolidayOfDaily substitute = new SubstituteHolidayOfDaily( new AttendanceTime(hdComTime),  new AttendanceTime(hdComHourlyTime));
		OverSalaryOfDaily overSalary = new OverSalaryOfDaily(new AttendanceTime(hd60hTime), new AttendanceTime(hd60hHourlyTime));
		SpecialHolidayOfDaily specialHoliday = new SpecialHolidayOfDaily(new AttendanceTime(hdspTime), new AttendanceTime(hdspHourlyTime));
		YearlyReservedOfDaily yearlyReserved = new YearlyReservedOfDaily(new AttendanceTime(hdstkTime)); 
		TimeDigestOfDaily timeDigest = new TimeDigestOfDaily(new AttendanceTime(hdHourlyTime), new AttendanceTime(hdHourlyShortageTime));
		AnnualOfDaily annual = new AnnualOfDaily(new AttendanceTime(hdPaidTime), new AttendanceTime(hdPaidHourlyTime));
		HolidayOfDaily holidayOfDaily = new HolidayOfDaily(
				new AbsenceOfDaily(new AttendanceTime(absenceTime)),
				timeDigest,
				yearlyReserved,
				substitute,
				overSalary,
				specialHoliday,
				annual);
		
		
		
		DeductionTotalTime deductionTotalTime = DeductionTotalTime.of(TimeWithCalculation.sameTime(new AttendanceTime(this.brkTotalTime)), null, null);
		BreakTimeOfDaily breakTimeOfDaily = new BreakTimeOfDaily(null, deductionTotalTime, null, null, null);
		
		TotalWorkingTime totalWorkingTime = new TotalWorkingTime(new AttendanceTime(this.totalTime), null, new AttendanceTime(this.totalTimeAct),
				withinStatutoryTimeOfDaily, excessOfStatutoryTimeOfDaily, new ArrayList<>(), new ArrayList<>(),
				breakTimeOfDaily, new ArrayList<>(), raiseSalaryTimeOfDailyPerfor, new WorkTimes(this.count), null,
				shotrTime, holidayOfDaily, new AttendanceTime(vacationAddTime));
		
		// 乖離時間
		DivergenceTimeOfDaily divTime = null;
		
		// 割増時間
		KscdtSchPremium kscdtSchPremium = new KscdtSchPremium();
		PremiumTimeOfDailyPerformance premiumTime = new PremiumTimeOfDailyPerformance(kscdtSchPremium.toDomain());

		ActualWorkingTimeOfDaily workingTimeOfDaily = new ActualWorkingTimeOfDaily(constraintDiffTime, constraintTime,
				timeDiff, totalWorkingTime, divTime, premiumTime);
		return workingTimeOfDaily;
	}
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscdtSchTime(KscdtSchTimePK pk, String cid, int count, int totalTime, int totalTimeAct, int prsWorkTime,
			int prsWorkTimeAct, int prsPrimeTime, int prsMidniteTime, int extBindTimeOtw, int extBindTimeHw,
			int extVarwkOtwTimeLegal, int extFlexTime, int extFlexTimePreApp, int extMidNiteOtwTime,
			int extMidNiteHdwTimeLghd, int extMidNiteHdwTimeIlghd, int extMidNiteHdwTimePubhd, int extMidNiteTotal,
			int extMidNiteTotalPreApp, int intervalAtdClock, int intervalTime, int brkTotalTime, int hdPaidTime,
			int hdPaidHourlyTime, int hdComTime, int hdComHourlyTime, int hd60hTime, int hd60hHourlyTime, int hdspTime,
			int hdspHourlyTime, int hdstkTime, int hdHourlyTime, int hdHourlyShortageTime, int absenceTime,
			int vacationAddTime, int staggeredWhTime,
			List<KscdtSchOvertimeWork> overtimeWorks, List<KscdtSchHolidayWork> holidayWorks,
			List<KscdtSchBonusPay> bonusPays, List<KscdtSchPremium> premiums, List<KscdtSchShortTime> shortTimes) {
		super();
		this.pk = pk;
		this.cid = cid;
		this.count = count;
		this.totalTime = totalTime;
		this.totalTimeAct = totalTimeAct;
		this.prsWorkTime = prsWorkTime;
		this.prsWorkTimeAct = prsWorkTimeAct;
		this.prsPrimeTime = prsPrimeTime;
		this.prsMidniteTime = prsMidniteTime;
		this.extBindTimeOtw = extBindTimeOtw;
		this.extBindTimeHw = extBindTimeHw;
		this.extVarwkOtwTimeLegal = extVarwkOtwTimeLegal;
		this.extFlexTime = extFlexTime;
		this.extFlexTimePreApp = extFlexTimePreApp;
		this.extMidNiteOtwTime = extMidNiteOtwTime;
		this.extMidNiteHdwTimeLghd = extMidNiteHdwTimeLghd;
		this.extMidNiteHdwTimeIlghd = extMidNiteHdwTimeIlghd;
		this.extMidNiteHdwTimePubhd = extMidNiteHdwTimePubhd;
		this.extMidNiteTotal = extMidNiteTotal;
		this.extMidNiteTotalPreApp = extMidNiteTotalPreApp;
		this.intervalAtdClock = intervalAtdClock;
		this.intervalTime = intervalTime;
		this.brkTotalTime = brkTotalTime;
		this.hdPaidTime = hdPaidTime;
		this.hdPaidHourlyTime = hdPaidHourlyTime;
		this.hdComTime = hdComTime;
		this.hdComHourlyTime = hdComHourlyTime;
		this.hd60hTime = hd60hTime;
		this.hd60hHourlyTime = hd60hHourlyTime;
		this.hdspTime = hdspTime;
		this.hdspHourlyTime = hdspHourlyTime;
		this.hdstkTime = hdstkTime;
		this.hdHourlyTime = hdHourlyTime;
		this.hdHourlyShortageTime = hdHourlyShortageTime;
		this.absenceTime = absenceTime;
		this.vacationAddTime = vacationAddTime;
		this.staggeredWhTime = staggeredWhTime;
		this.overtimeWorks = overtimeWorks;
		this.holidayWorks = holidayWorks;
		this.bonusPays = bonusPays;
		this.premiums = premiums;
		this.shortTimes = shortTimes;
	}
}
