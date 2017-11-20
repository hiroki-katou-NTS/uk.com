package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayusetime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.SpecialHolidayUseTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayusetime.SpecialHolidayUseTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayusetime.KrcdtSpHolidayUtMon;

/**
 * リポジトリ実装：月別実績の特別休暇使用時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaSpecialHolidayUseTimeOfMonthly extends JpaRepository implements SpecialHolidayUseTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			SpecialHolidayUseTimeOfMonthly specialHolidayUseTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, specialHolidayUseTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			SpecialHolidayUseTimeOfMonthly specialHolidayUseTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtSpHolidayUtMon entity = this.queryProxy().find(key, KrcdtSpHolidayUtMon.class).get();
		entity.useTime = specialHolidayUseTimeOfMonthly.getUseTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param specialHolidayUseTimeOfMonthly ドメイン：月別実績の特別休暇使用時間
	 * @return エンティティ：月別実績の特別休暇使用時間
	 */
	private static KrcdtSpHolidayUtMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			SpecialHolidayUseTimeOfMonthly specialHolidayUseTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtSpHolidayUtMon entity = new KrcdtSpHolidayUtMon();
		entity.PK = key;
		entity.useTime = specialHolidayUseTimeOfMonthly.getUseTime().v();
		return entity;
	}
}
