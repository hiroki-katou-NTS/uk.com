package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.workdays.specificdays;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.specificdays.AggregateSpecificDaysRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpecDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrSpecDaysPK;

/**
 * リポジトリ実装：集計特定日数
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateSpecificDays extends JpaRepository implements AggregateSpecificDaysRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrSpecDays a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateSpecificDays aggregateSpecificDays) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateSpecificDays, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateSpecificDays aggregateSpecificDays) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateSpecificDays, true);
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
	 * @param domain ドメイン：集計特定日数
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計特定日数
	 */
	private KrcdtMonAggrSpecDays toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateSpecificDays domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrSpecDaysPK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getSpecificDayItemNo().v());
		
		KrcdtMonAggrSpecDays entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrSpecDays.class, key);
		}
		else {
			entity = new KrcdtMonAggrSpecDays();
			entity.PK = key;
		}
		entity.specificDays = domain.getSpecificDays().v();
		entity.holidayWorkSpecificDays = domain.getHolidayWorkSpecificDays().v();
		return entity;
	}
}
