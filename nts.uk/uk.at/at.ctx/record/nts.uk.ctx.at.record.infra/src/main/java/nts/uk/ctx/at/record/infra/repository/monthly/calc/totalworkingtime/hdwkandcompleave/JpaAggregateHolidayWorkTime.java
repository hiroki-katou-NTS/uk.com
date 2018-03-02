package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.hdwkandcompleave;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTimePK;

/**
 * リポジトリ実装：集計休出時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateHolidayWorkTime extends JpaRepository implements AggregateHolidayWorkTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrHdwkTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateHolidayWorkTime, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateHolidayWorkTime, true);
	}

	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey) {
		
		// 締め日付
		val closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
				.setParameter("employeeID", attendanceTimeOfMonthlyKey.getEmployeeId())
				.setParameter("yearMonth", attendanceTimeOfMonthlyKey.getYearMonth().v())
				.setParameter("closureId", attendanceTimeOfMonthlyKey.getClosureId().value)
				.setParameter("closureDay", closureDate.getClosureDay().v())
				.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：集計休出時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計休出時間
	 */
	private KrcdtMonAggrHdwkTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateHolidayWorkTime domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrHdwkTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getHolidayWorkFrameNo().v());
		
		KrcdtMonAggrHdwkTime entity;
		if (execUpdate) {
			entity = this.getEntityManager().find(KrcdtMonAggrHdwkTime.class, key);
		}
		else {
			entity = new KrcdtMonAggrHdwkTime();
			entity.PK = key;
		}
		entity.holidayWorkTime = domain.getHolidayWorkTime().getTime().v();
		entity.calcHolidayWorkTime = domain.getHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = domain.getBeforeHolidayWorkTime().v();
		entity.transferTime = domain.getTransferTime().getTime().v();
		entity.calcTransferTime = domain.getTransferTime().getCalcTime().v();
		entity.legalHolidayWorkTime = domain.getLegalHolidayWorkTime().v();
		entity.legalTransferHolidayWorkTime = domain.getLegalTransferHolidayWorkTime().v();
		return entity;
	}
}
