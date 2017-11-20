package nts.uk.ctx.at.record.infra.repository.monthly.calc.actualworkingtime;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime.RegularAndIrregularTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.actualworkingtime.KrcdtRegIrregTimeMon;

/**
 * リポジトリ実装：月別実績の通常変形時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaRegularAndIrregularTimeOfMonthly extends JpaRepository implements RegularAndIrregularTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, regularAndIrregularTimeOfMonthly));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly) {
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtRegIrregTimeMon entity = this.queryProxy().find(key, KrcdtRegIrregTimeMon.class).get();
		entity.monthlyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getMonthlyTotalPremiumTime().v();
		entity.weeklyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getWeeklyTotalPremiumTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param regularAndIrregularTimeOfMonthly ドメイン：月別実績の通常変形時間
	 * @return エンティティ：月別実績の通常変形時間
	 */
	private static KrcdtRegIrregTimeMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			RegularAndIrregularTimeOfMonthly regularAndIrregularTimeOfMonthly){
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtRegIrregTimeMon entity = new KrcdtRegIrregTimeMon();
		entity.PK = key;
		entity.monthlyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getMonthlyTotalPremiumTime().v();
		entity.weeklyTotalPremiumTime = regularAndIrregularTimeOfMonthly.getWeeklyTotalPremiumTime().v();
		return entity;
	}
}
