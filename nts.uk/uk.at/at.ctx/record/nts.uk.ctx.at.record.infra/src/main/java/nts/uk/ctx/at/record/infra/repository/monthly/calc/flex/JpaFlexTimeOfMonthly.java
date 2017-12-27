package nts.uk.ctx.at.record.infra.repository.monthly.calc.flex;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexCarryforwardTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfExcessOutsideTime;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

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

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// フレックス時間
		FlexTime flexTime = flexTimeOfMonthly.getFlexTime();
		// フレックス繰越時間
		FlexCarryforwardTime flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		FlexTimeOfExcessOutsideTime flexTimeOfExcessOutsideTime = flexTimeOfMonthly.getFlexTimeOfExcessOutsideTime();
		
		KrcdtMonFlexTime entity = this.queryProxy().find(key, KrcdtMonFlexTime.class).get();
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.calcFlexTime = flexTime.getFlexTime().getCalculationTime().v();
		entity.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.legalFlexTime = flexTime.getLegalFlexTime().v();
		entity.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		entity.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		entity.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		entity.beforeFlexTime = flexTimeOfMonthly.getBeforeFlexTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		this.commandProxy().update(entity);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param flexTimeOfMonthly ドメイン：月別実績のフレックス時間
	 * @return エンティティ：月別実績のフレックス時間
	 */
	private static KrcdtMonFlexTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			FlexTimeOfMonthly flexTimeOfMonthly){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAttendanceTimePK key = new KrcdtMonAttendanceTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// フレックス時間
		FlexTime flexTime = flexTimeOfMonthly.getFlexTime();
		// フレックス繰越時間
		FlexCarryforwardTime flexCarryForwardTime = flexTimeOfMonthly.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		FlexTimeOfExcessOutsideTime flexTimeOfExcessOutsideTime = flexTimeOfMonthly.getFlexTimeOfExcessOutsideTime();
		
		KrcdtMonFlexTime entity = new KrcdtMonFlexTime();
		entity.PK = key;
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.calcFlexTime = flexTime.getFlexTime().getCalculationTime().v();
		entity.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.legalFlexTime = flexTime.getLegalFlexTime().v();
		entity.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		entity.flexExcessTime = flexTimeOfMonthly.getFlexExcessTime().v();
		entity.flexShortageTime = flexTimeOfMonthly.getFlexShortageTime().v();
		entity.beforeFlexTime = flexTimeOfMonthly.getBeforeFlexTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		return entity;
	}
}
