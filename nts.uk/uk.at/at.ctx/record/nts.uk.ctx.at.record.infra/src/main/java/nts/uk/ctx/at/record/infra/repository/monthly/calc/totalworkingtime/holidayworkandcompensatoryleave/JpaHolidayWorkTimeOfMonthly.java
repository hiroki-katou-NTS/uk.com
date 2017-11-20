package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtHolidayWorkTmMon;

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
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtHolidayWorkTmMon entity = this.queryProxy().find(key, KrcdtHolidayWorkTmMon.class).get();
		entity.totalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getTime().v();
		entity.totalHolidayWorkTimeCalc = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getCalculationTime().v();
		entity.beforeHolidayWorkTime = holidayWorkTimeOfMonthly.getBeforeHolidayWorkTime().v();
		entity.transferTotalTime = holidayWorkTimeOfMonthly.getTransferTotalTime().getTime().v();
		entity.transferTotalTimeCalc = holidayWorkTimeOfMonthly.getTransferTotalTime().getCalculationTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param holidayWorkTimeOfMonthly ドメイン：月別実績の休出時間
	 * @return エンティティ：月別実績の休出時間
	 */
	private static KrcdtHolidayWorkTmMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtHolidayWorkTmMon entity = new KrcdtHolidayWorkTmMon();
		entity.PK = key;
		entity.totalHolidayWorkTime = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getTime().v();
		entity.totalHolidayWorkTimeCalc = holidayWorkTimeOfMonthly.getTotalHolidayWorkTime().getCalculationTime().v();
		entity.beforeHolidayWorkTime = holidayWorkTimeOfMonthly.getBeforeHolidayWorkTime().v();
		entity.transferTotalTime = holidayWorkTimeOfMonthly.getTransferTotalTime().getTime().v();
		entity.transferTotalTimeCalc = holidayWorkTimeOfMonthly.getTransferTotalTime().getCalculationTime().v();
		return entity;
	}
}
