package nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 月別実績の休暇使用時間
 * @author shuichu_ishida
 */
@Entity
@Table(name = "KRCDT_MON_VACT_USE_TIME")
@NoArgsConstructor
@AllArgsConstructor
public class KrcdtMonVactUseTime extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** プライマリキー */
	@EmbeddedId
	public KrcdtMonAttendanceTimePK PK;
	
	/** 年休使用時間 */
	@Column(name = "ANLLEV_USE_TIME")
	public int annualLeaveUseTime;
	
	/** 積立年休使用時間 */
	@Column(name = "RETYEA_USE_TIME")
	public int retentionYearlyUseTime;
	
	/** 特別休暇使用時間 */
	@Column(name = "SPHD_USE_TIME")
	public int specialHolidayUseTime;
	
	/** 代休使用時間 */
	@Column(name = "CMPLEV_USE_TIME")
	public int compensatoryLeaveUseTime;

	/** マッチング：月別実績の勤怠時間 */
	@OneToOne
	@JoinColumns({
    	@JoinColumn(name = "SID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.SID", insertable = false, updatable = false),
		@JoinColumn(name = "YM", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.YM", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_ID", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_ID", insertable = false, updatable = false),
		@JoinColumn(name = "CLOSURE_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.CLOSURE_DAY", insertable = false, updatable = false),
		@JoinColumn(name = "IS_LAST_DAY", referencedColumnName = "KRCDT_MON_ATTENDANCE_TIME.IS_LAST_DAY", insertable = false, updatable = false)
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
	 * @return 月別実績の休暇使用時間
	 */
	public VacationUseTimeOfMonthly toDomain(){
		
		return VacationUseTimeOfMonthly.of(
				AnnualLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.annualLeaveUseTime)),
				RetentionYearlyUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.retentionYearlyUseTime)),
				SpecialHolidayUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.specialHolidayUseTime)),
				CompensatoryLeaveUseTimeOfMonthly.of(
						new AttendanceTimeMonth(this.compensatoryLeaveUseTime)));
	}
	
	/**
	 * ドメインから変換　（for Insert）
	 * @param key キー値：月別実績の勤怠時間
	 * @param domain 月別実績の休暇使用時間
	 */
	public void fromDomainForPersist(AttendanceTimeOfMonthlyKey key, VacationUseTimeOfMonthly domain){
		
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
	 * @param domain 月別実績の休暇使用時間
	 */
	public void fromDomainForUpdate(VacationUseTimeOfMonthly domain){
		
		this.annualLeaveUseTime = domain.getAnnualLeave().getUseTime().v();
		this.retentionYearlyUseTime = domain.getRetentionYearly().getUseTime().v();
		this.specialHolidayUseTime = domain.getSpecialHoliday().getUseTime().v();
		this.compensatoryLeaveUseTime = domain.getCompensatoryLeave().getUseTime().v();
	}
}
