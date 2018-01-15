package nts.uk.ctx.at.record.infra.repository.monthly.calc.flex;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.flex.FlexTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.flex.KrcdtMonFlexTime;

/**
 * リポジトリ実装：月別実績のフレックス時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaFlexTimeOfMonthly extends JpaRepository implements FlexTimeOfMonthlyRepository {

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly) {
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, flexTimeOfMonthly, false));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, FlexTimeOfMonthly flexTimeOfMonthly) {
		this.toEntity(attendanceTimeOfMonthlyKey, flexTimeOfMonthly, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績のフレックス時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：月別実績のフレックス時間
	 */
	private KrcdtMonFlexTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			FlexTimeOfMonthly domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		// フレックス時間
		val flexTime = domain.getFlexTime();
		// フレックス繰越時間
		val flexCarryForwardTime = domain.getFlexCarryforwardTime();
		// 時間外超過のフレックス時間
		val flexTimeOfExcessOutsideTime = domain.getFlexTimeOfExcessOutsideTime();
		
		KrcdtMonFlexTime entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcdtMonFlexTime.class).get();
		}
		else {
			entity = new KrcdtMonFlexTime();
			entity.PK = key;
		}
		entity.flexTime = flexTime.getFlexTime().getTime().v();
		entity.calcFlexTime = flexTime.getFlexTime().getCalcTime().v();
		entity.beforeFlexTime = flexTime.getBeforeFlexTime().v();
		entity.legalFlexTime = flexTime.getLegalFlexTime().v();
		entity.illegalFlexTime = flexTime.getIllegalFlexTime().v();
		entity.flexExcessTime = domain.getFlexExcessTime().v();
		entity.flexShortageTime = domain.getFlexShortageTime().v();
		entity.flexCarryforwardWorkTime = flexCarryForwardTime.getFlexCarryforwardWorkTime().v();
		entity.flexCarryforwardTime = flexCarryForwardTime.getFlexCarryforwardTime().v();
		entity.flexCarryforwardShortageTime = flexCarryForwardTime.getFlexCarryforwardShortageTime().v();
		entity.excessFlexAtr = flexTimeOfExcessOutsideTime.getExcessFlexAtr().value;
		entity.principleTime = flexTimeOfExcessOutsideTime.getPrincipleTime().v();
		entity.forConvenienceTime = flexTimeOfExcessOutsideTime.getForConvenienceTime().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
