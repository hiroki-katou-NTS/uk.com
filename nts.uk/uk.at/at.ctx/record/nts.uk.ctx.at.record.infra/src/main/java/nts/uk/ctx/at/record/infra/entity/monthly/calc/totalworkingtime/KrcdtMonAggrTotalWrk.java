package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.AggregateTotalWorkingTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.PrescribedWorkingTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.WorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.OverTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 集計総労働時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_AGGR_TOTAL_WRK")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonAggrTotalWrk extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 就業時間 */
	@Column(name = "WORK_TIME")
	public int workTime;
	
	/** 実働就業時間 */
	@Column(name = "ACTWORK_TIME")
	public int actualWorkTime;
	
	/** 所定内割増時間 */
	@Column(name = "WITPRS_PREMIUM_TIME")
	public int withinPrescribedPremiumTime;
	
	/** 計画所定労働時間 */
	@Column(name = "SCHE_PRS_WORK_TIME")
	public int schedulePrescribedWorkingTime;
	
	/** 実績所定労働時間 */
	@Column(name = "RECD_PRS_WORK_TIME")
	public int recordPrescribedWorkingTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
		@JoinColumn(name = "SID", referencedColumnName = "SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "IS_LAST_DAY", insertable = false, updatable = false)
	})
	public KrcdtMonAttendanceTime krcdtMonAttendanceTime;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {		
		return this.PK;
	}
	
	/**
	 * ドメインに変換
	 * @param krcdtMonOverTime 月別実績の残業時間
	 * @param krcdtMonAggrOverTimes 集計残業時間
	 * @param krcdtMonHdwkTime 月別実績の休出時間
	 * @param krcdtMonAggrHdwkTimes 集計休出時間
	 * @param krcdtMonVactUseTime 月別実績の休暇使用時間
	 * @return 集計総労働時間
	 */
	public AggregateTotalWorkingTime toDomain(
			KrcdtMonOverTime krcdtMonOverTime,
			List<KrcdtMonAggrOverTime> krcdtMonAggrOverTimes,
			KrcdtMonHdwkTime krcdtMonHdwkTime,
			List<KrcdtMonAggrHdwkTime> krcdtMonAggrHdwkTimes,
			KrcdtMonVactUseTime krcdtMonVactUseTime){
		
		// 月別実績の就業時間
		val workTime = WorkTimeOfMonthly.of(
				new AttendanceTimeMonth(this.workTime),
				new AttendanceTimeMonth(this.withinPrescribedPremiumTime),
				new AttendanceTimeMonth(this.actualWorkTime));

		// 月別実績の残業時間
		OverTimeOfMonthly overTime = new OverTimeOfMonthly();
		if (krcdtMonOverTime != null){
			overTime = krcdtMonOverTime.toDomain(krcdtMonAggrOverTimes);
		}
		
		// 月別実績の休出時間
		HolidayWorkTimeOfMonthly holidayWorkTime = new HolidayWorkTimeOfMonthly();
		if (krcdtMonHdwkTime != null){
			holidayWorkTime = krcdtMonHdwkTime.toDomain(krcdtMonAggrHdwkTimes);
		}
		
		// 月別実績の休暇使用時間
		VacationUseTimeOfMonthly vacationUseTime = new VacationUseTimeOfMonthly();
		if (krcdtMonVactUseTime != null){
			vacationUseTime = krcdtMonVactUseTime.toDomain();
		}
		
		// 月別実績の所定労働時間
		val prescribedWorkingTime = PrescribedWorkingTimeOfMonthly.of(
				new AttendanceTimeMonth(this.schedulePrescribedWorkingTime),
				new AttendanceTimeMonth(this.recordPrescribedWorkingTime));
		
		// 集計総労働時間
		return AggregateTotalWorkingTime.of(
				workTime,
				overTime,
				holidayWorkTime,
				vacationUseTime,
				prescribedWorkingTime);
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 集計総労働時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, AggregateTotalWorkingTime domain){
		
		this.PK = new KrcdtMonAttendanceTimePK(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		this.fromDomainForUpdate(domain);
	}
	
	/**
	 * ドメインから変換　(for Update)
	 * @param domain 集計総労働時間
	 */
	public void fromDomainForUpdate(AggregateTotalWorkingTime domain){
		
		this.workTime = domain.getWorkTime().getWorkTime().v();
		this.actualWorkTime = domain.getWorkTime().getActualWorkTime().v();
		this.withinPrescribedPremiumTime = domain.getWorkTime().getWithinPrescribedPremiumTime().v();
		this.schedulePrescribedWorkingTime = domain.getPrescribedWorkingTime().getSchedulePrescribedWorkingTime().v();
		this.recordPrescribedWorkingTime = domain.getPrescribedWorkingTime().getRecordPrescribedWorkingTime().v();
	}
}
