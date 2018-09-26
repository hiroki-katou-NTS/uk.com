package nts.uk.ctx.at.record.infra.entity.monthly;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.AggregateTotalTimeSpentAtWork;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.totalcount.TotalCountByPeriod;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAggrTotalSpt;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.KrcdtMonAgreementTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtMonRegIrregTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.KrcdtMonAggrTotalWrk;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcessOutside;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcoutTime;
import nts.uk.ctx.at.record.infra.entity.monthly.totalcount.KrcdtMonTotalTimes;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.KrcdtMonVerticalTotal;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workclock.KrcdtMonWorkClock;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrAbsnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpecDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpvcDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonLeave;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrBnspyTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrDivgTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrGoout;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrPremTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonMedicalTime;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * エンティティ：月別実績の勤怠時間
 * @author shuichi_ishida
 */
@Entity
@Table(name = "KRCDT_MON_ATTENDANCE_TIME")
@NoArgsConstructor
public class KrcdtMonAttendanceTime extends UkJpaEntity implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 開始年月日 */
	@Column(name = "START_YMD")
	public GeneralDate startYmd;

	/** 終了年月日 */
	@Column(name = "END_YMD")
	public GeneralDate endYmd;

	/** 集計日数 */
	@Column(name = "AGGREGATE_DAYS")
	public Double aggregateDays;
	
	/** 法定労働時間 */
	@Column(name = "STAT_WORKING_TIME")
	public int statutoryWorkingTime;
	
	/** 総労働時間 */
	@Column(name = "TOTAL_WORKING_TIME")
	public int totalWorkingTime;

	/** 実働時間：月別実績の通常変形時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonRegIrregTime krcdtMonRegIrregTime;
	
	/** 月別実績のフレックス時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonFlexTime krcdtMonFlexTime;
	
	/** 総労働時間：月別実績の休暇使用時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonVactUseTime krcdtMonVactUseTime;
	
	/** 総労働時間：集計総労働時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonAggrTotalWrk krcdtMonAggrTotalWrk;
	
	/** 総労働時間：残業時間：月別実績の残業時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonOverTime krcdtMonOverTime;
	
	/** 総労働時間：残業時間：集計残業時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimes;
	
	/** 総労働時間：休出・代休：月別実績の休出時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonHdwkTime krcdtMonHdwkTime;
	
	/** 総労働時間：休出・代休：集計休出時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimes;
	
	/** 集計総拘束時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonAggrTotalSpt krcdtMonAggrTotalSpt;
	
	/** 月別実績の時間外超過 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonExcessOutside krcdtMonExcessOutside;
	
	/** 時間外超過：時間外超過 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonExcoutTime> krcdtMonExcoutTime;
	
	/** 36協定時間 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonAgreementTime krcdtMonAgreementTime;
	
	/** 縦計 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonVerticalTotal krcdtMonVerticalTotal;
	
	/** 縦計：勤務日数：集計欠勤日数 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrAbsnDays> krcdtMonAggrAbsnDays;
	
	/** 縦計：勤務日数：集計特定日数 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrSpecDays> krcdtMonAggrSpecDays;
	
	/** 縦計：勤務日数：集計特別休暇日数 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrSpvcDays> krcdtMonAggrSpvcDays;
	
	/** 縦計：勤務日数：月別実績の休業 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonLeave krcdtMonLeave;
	
	/** 縦計：勤務時間：集計加給時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrBnspyTime> krcdtMonAggrBnspyTime;
	
	/** 縦計：勤務時間：集計乖離時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrDivgTime> krcdtMonAggrDivgTime;
	
	/** 縦計：勤務時間：集計外出 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrGoout> krcdtMonAggrGoout;
	
	/** 縦計：勤務時間：集計割増時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonAggrPremTime> krcdtMonAggrPremTime;
	
	/** 縦計：勤務時間：月別実績の医療時間 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonMedicalTime> krcdtMonMedicalTime;
	
	/** 縦計：勤務時刻 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public KrcdtMonWorkClock krcdtMonWorkClock;
	
	/** 回数集計 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy="krcdtMonAttendanceTime", orphanRemoval = true)
	public List<KrcdtMonTotalTimes> krcdtMonTotalTimes;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @return 月別実績の勤怠時間
	 */
	public AttendanceTimeOfMonthly toDomain(){

		// 月別実績の通常変形時間
		RegularAndIrregularTimeOfMonthly regAndIrgTime = new RegularAndIrregularTimeOfMonthly();
		if (this.krcdtMonRegIrregTime != null){
			regAndIrgTime = this.krcdtMonRegIrregTime.toDomain();
		}
		
		// 月別実績のフレックス時間
		FlexTimeOfMonthly flexTime = new FlexTimeOfMonthly();
		if (this.krcdtMonFlexTime != null){
			flexTime = this.krcdtMonFlexTime.toDomain();
		}
		
		// 集計総労働時間
		AggregateTotalWorkingTime aggregateTotalWorkingTime = new AggregateTotalWorkingTime();
		if (this.krcdtMonAggrTotalWrk != null){
			aggregateTotalWorkingTime = this.krcdtMonAggrTotalWrk.toDomain(
					this.krcdtMonOverTime,
					this.krcdtMonAggrOverTimes,
					this.krcdtMonHdwkTime,
					this.krcdtMonAggrHdwkTimes,
					this.krcdtMonVactUseTime);
		}
		
		// 集計総拘束時間
		AggregateTotalTimeSpentAtWork aggregateTotalTimeSpent = new AggregateTotalTimeSpentAtWork();
		if (this.krcdtMonAggrTotalSpt != null){
			aggregateTotalTimeSpent = this.krcdtMonAggrTotalSpt.toDomain();
		}
		
		// 月別実績の36協定時間
		AgreementTimeOfMonthly agreementTime = new AgreementTimeOfMonthly();
		if (this.krcdtMonAgreementTime != null){
			agreementTime = this.krcdtMonAgreementTime.toDomain();
		}
		
		// 月別実績の月の計算
		val monthlyCalculation = MonthlyCalculation.of(
				regAndIrgTime,
				flexTime,
				new AttendanceTimeMonth(this.statutoryWorkingTime),
				aggregateTotalWorkingTime,
				new AttendanceTimeMonth(this.totalWorkingTime),
				aggregateTotalTimeSpent,
				agreementTime);
		
		// 月別実績の時間外超過
		ExcessOutsideWorkOfMonthly excessOutsideWork = new ExcessOutsideWorkOfMonthly();
		if (this.krcdtMonExcessOutside != null){
			excessOutsideWork = this.krcdtMonExcessOutside.toDomain(this.krcdtMonExcoutTime);
		}
		
		// 月別実績の縦計
		VerticalTotalOfMonthly verticalTotal = new VerticalTotalOfMonthly();
		if (this.krcdtMonVerticalTotal != null){
			verticalTotal = this.krcdtMonVerticalTotal.toDomain(
					this.krcdtMonLeave,
					this.krcdtMonAggrAbsnDays,
					this.krcdtMonAggrSpecDays,
					this.krcdtMonAggrSpvcDays,
					this.krcdtMonAggrBnspyTime,
					this.krcdtMonAggrGoout,
					this.krcdtMonAggrPremTime,
					this.krcdtMonAggrDivgTime,
					this.krcdtMonMedicalTime,
					this.krcdtMonWorkClock);
		}
		
		// 期間別の回数集計
		TotalCountByPeriod totalCount = new TotalCountByPeriod();
		if (this.krcdtMonTotalTimes != null){
			totalCount = TotalCountByPeriod.of(
					this.krcdtMonTotalTimes.stream().map(c -> c.toDomain()).collect(Collectors.toList()));
		}
		
		return AttendanceTimeOfMonthly.of(
				this.PK.employeeId,
				new YearMonth(this.PK.yearMonth),
				ClosureId.valueOf(this.PK.closureId),
				new ClosureDate(this.PK.closureDay, (this.PK.isLastDay != 0)),
				new DatePeriod(this.startYmd, this.endYmd),
				monthlyCalculation,
				excessOutsideWork,
				verticalTotal,
				totalCount,
				new AttendanceDaysMonth(this.aggregateDays));
		
//		//テーブル結合用
//		return null;
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param domain 月別実績の勤怠時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthly domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 月別実績の勤怠時間
	 */
	public void fromDomainForUpdate(AttendanceTimeOfMonthly domain){

		val monthlyCalculation = domain.getMonthlyCalculation();
		
		this.startYmd = domain.getDatePeriod().start();
		this.endYmd = domain.getDatePeriod().end();
		this.aggregateDays = domain.getAggregateDays().v();
		this.statutoryWorkingTime = monthlyCalculation.getStatutoryWorkingTime().v();
		this.totalWorkingTime = monthlyCalculation.getTotalWorkingTime().v();
	}
}
