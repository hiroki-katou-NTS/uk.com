package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.vacationusetime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.vacationusetime.VacationUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.vacationusetime.KrcdtMonVactUseTime;

/**
 * リポジトリ実装：月別実績の休暇使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaVacationUseTimeOfMonthly extends JpaRepository implements VacationUseTimeOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			VacationUseTimeOfMonthly holidayUseTimeOfMonthly) {

		this.toUpdate(attendanceTimeOfMonthlyKey, holidayUseTimeOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の休暇使用時間
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, VacationUseTimeOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の年休使用時間
		val annualLeaveUseTime = domain.getAnnualLeave();
		// 月別実績の積立年休使用時間
		val retentionYearlyUseTime = domain.getRetentionYearly();
		// 月別実績の特別休暇使用時間
		val specialHolidayUseTime = domain.getSpecialHoliday();
		// 月別実績の代休使用時間
		val compensatoryLeaveUseTime = domain.getCompensatoryLeave();
		
		KrcdtMonVactUseTime entity = this.getEntityManager().find(KrcdtMonVactUseTime.class, key);
		if (entity == null) return;
		entity.annualLeaveUseTime = annualLeaveUseTime.getUseTime().v();
		entity.retentionYearlyUseTime = retentionYearlyUseTime.getUseTime().v();
		entity.specialHolidayUseTime = specialHolidayUseTime.getUseTime().v();
		entity.compensatoryLeaveUseTime = compensatoryLeaveUseTime.getUseTime().v();
	}
}
