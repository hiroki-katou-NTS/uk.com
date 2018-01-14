package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：月別実績の休出時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaHolidayWorkTimeOfMonthly extends JpaRepository implements HolidayWorkTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, holidayWorkTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonHdwkTime entity = this.queryProxy().find(key, KrcdtMonHdwkTime.class).get();
		entity.totalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getTime().v();
		entity.calcTotalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = holidayWorkTimeOfMonthly.getBeforeHolidayWorkTime().v();
		entity.totalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getTime().v();
		entity.calcTotalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getCalcTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayWorkTimeOfMonthly ドメイン：月別実績の休出時間
	 * @return エンティティ：月別実績の休出時間
	 */
	private static KrcdtMonHdwkTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonHdwkTime entity = new KrcdtMonHdwkTime();
		entity.PK = key;
		entity.totalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getTime().v();
		entity.calcTotalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = holidayWorkTimeOfMonthly.getBeforeHolidayWorkTime().v();
		entity.totalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getTime().v();
		entity.calcTotalTransferTime = holidayWorkTimeOfMonthly.getTotalTransferTime().getCalcTime().v();
		return entity;
	}
}
