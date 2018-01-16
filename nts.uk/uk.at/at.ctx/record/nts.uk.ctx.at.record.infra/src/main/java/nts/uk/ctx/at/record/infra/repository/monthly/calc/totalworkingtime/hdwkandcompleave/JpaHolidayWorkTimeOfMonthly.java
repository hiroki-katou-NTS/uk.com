package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.hdwkandcompleave;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.HolidayWorkTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonHdwkTime;

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
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, holidayWorkTimeOfMonthly, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			HolidayWorkTimeOfMonthly holidayWorkTimeOfMonthly) {

		this.toEntity(attendanceTimeOfMonthlyKey, holidayWorkTimeOfMonthly, true);
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の休出時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：月別実績の休出時間
	 */
	private KrcdtMonHdwkTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			HolidayWorkTimeOfMonthly domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		KrcdtMonHdwkTime entity;
		if (execUpdate){
			entity = this.queryProxy().find(key, KrcdtMonHdwkTime.class).get();
		}
		else {
			entity = new KrcdtMonHdwkTime();
			entity.PK = key;
		}
		entity.totalHolidayWorkTime = domain.getTotalHolidayWorkTime().getTime().v();
		entity.calcTotalHolidayWorkTime = domain.getTotalHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = domain.getBeforeHolidayWorkTime().v();
		entity.totalTransferTime = domain.getTotalTransferTime().getTime().v();
		entity.calcTotalTransferTime = domain.getTotalTransferTime().getCalcTime().v();
		if (execUpdate) this.commandProxy().update(entity);
		return entity;
	}
}
