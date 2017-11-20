package nts.uk.ctx.at.record.infra.repository.monthly.calc.flex;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideWork;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtFlexTimeMon;

/**
 * リポジトリ実装：月別実績のフレックス時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaFlexTimeOfMonthly extends JpaRepository implements FlexTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly) {
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, flexTimeOfMonthly));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly) {
		
		// フレックス時間
		FlexTime flexTime = flexTimeOfMonthly.getFlexTime();
		// フレックス繰越時間
		FlexCarryforwardTime flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		FlexTimeOfExcessOutsideWork flexTimeOfExcessOutsideWork = flexTimeOfMonthly.getFlexTimeOfExcessOutsideWork();
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtFlexTimeMon entity = this.queryProxy().find(key, KrcdtFlexTimeMon.class).get();
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.flexTimeCalc = flexTime.getFlexTime().getCalculationTime().v();
		entity.calcBeforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.calcWithinStatutoryFlexTime = flexTime.getWithinStatutoryFlexTime().v();
		entity.calcExcessOfStatutoryFlexTime = flexTime.getExcessOfStatutoryFlexTime().v();
		entity.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		entity.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		entity.beforeFlexTime = flexTimeOfMonthly.getBeforeFlexTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideWork.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideWork.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideWork.getForConvenienceTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param flexTimeOfMonthly ドメイン：月別実績のフレックス時間
	 * @return エンティティ：月別実績のフレックス時間
	 */
	private static KrcdtFlexTimeMon toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			FlexTimeOfMonthly flexTimeOfMonthly){
		
		// フレックス時間
		FlexTime flexTime = flexTimeOfMonthly.getFlexTime();
		// フレックス繰越時間
		FlexCarryforwardTime flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		FlexTimeOfExcessOutsideWork flexTimeOfExcessOutsideWork = flexTimeOfMonthly.getFlexTimeOfExcessOutsideWork();
		
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end());
		KrcdtFlexTimeMon entity = new KrcdtFlexTimeMon();
		entity.PK = key;
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.flexTimeCalc = flexTime.getFlexTime().getCalculationTime().v();
		entity.calcBeforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.calcWithinStatutoryFlexTime = flexTime.getWithinStatutoryFlexTime().v();
		entity.calcExcessOfStatutoryFlexTime = flexTime.getExcessOfStatutoryFlexTime().v();
		entity.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		entity.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		entity.beforeFlexTime = flexTimeOfMonthly.getBeforeFlexTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideWork.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideWork.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideWork.getForConvenienceTime().v();
		return entity;
	}
}
