package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayusetime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.AnnualLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.CompensatoryLeaveUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.HolidayUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.RetentionYearlyUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtMonHldyUseTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：月別実績の休暇使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaHolidayUseTimeOfMonthly extends JpaRepository implements HolidayUseTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayUseTimeOfMonthly holidayUseTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, holidayUseTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayUseTimeOfMonthly holidayUseTimeOfMonthly) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の年休使用時間
		AnnualLeaveUseTimeOfMonthly annualLeaveUseTime = holidayUseTimeOfMonthly.getAnnualLeave();
		// 月別実績の積立年休使用時間
		RetentionYearlyUseTimeOfMonthly retentionYearlyUseTime = holidayUseTimeOfMonthly.getRetentionYearly();
		// 月別実績の特別休暇使用時間
		SpecialHolidayUseTimeOfMonthly specialHolidayUseTime = holidayUseTimeOfMonthly.getSpecialHoliday();
		// 月別実績の代休使用時間
		CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTime = holidayUseTimeOfMonthly.getCompensatoryLeave();
		
		KrcdtMonHldyUseTime entity = this.queryProxy().find(key, KrcdtMonHldyUseTime.class).get();
		entity.annualLeaveUseTime = annualLeaveUseTime.getUseTime().v();
		entity.retentionYearlyUseTime = retentionYearlyUseTime.getUseTime().v();
		entity.specialHolidayUseTime = specialHolidayUseTime.getUseTime().v();
		entity.compensatoryLeaveUseTime = compensatoryLeaveUseTime.getUseTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayUseTimeOfMonthly ドメイン：月別実績の休暇使用時間
	 * @return エンティティ：月別実績の年休使用時間
	 */
	private static KrcdtMonHldyUseTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayUseTimeOfMonthly holidayUseTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));

		// 月別実績の年休使用時間
		AnnualLeaveUseTimeOfMonthly annualLeaveUseTime = holidayUseTimeOfMonthly.getAnnualLeave();
		// 月別実績の積立年休使用時間
		RetentionYearlyUseTimeOfMonthly retentionYearlyUseTime = holidayUseTimeOfMonthly.getRetentionYearly();
		// 月別実績の特別休暇使用時間
		SpecialHolidayUseTimeOfMonthly specialHolidayUseTime = holidayUseTimeOfMonthly.getSpecialHoliday();
		// 月別実績の代休使用時間
		CompensatoryLeaveUseTimeOfMonthly compensatoryLeaveUseTime = holidayUseTimeOfMonthly.getCompensatoryLeave();
		
		KrcdtMonHldyUseTime entity = new KrcdtMonHldyUseTime();
		entity.PK = key;
		entity.annualLeaveUseTime = annualLeaveUseTime.getUseTime().v();
		entity.retentionYearlyUseTime = retentionYearlyUseTime.getUseTime().v();
		entity.specialHolidayUseTime = specialHolidayUseTime.getUseTime().v();
		entity.compensatoryLeaveUseTime = compensatoryLeaveUseTime.getUseTime().v();
		return entity;
	}
}
