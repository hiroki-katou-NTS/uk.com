package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.workdays.workdays;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDays;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays.AggregateAbsenceDaysRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrAbsnDays;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.workdays.KrcdtMonAggrAbsnDaysPK;

/**
 * リポジトリ実装：集計欠勤日数
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateAbsenceDays extends JpaRepository implements AggregateAbsenceDaysRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrAbsnDays a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateAbsenceDays aggregateAbsenceDays) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateAbsenceDays, false));
	}
	
	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateAbsenceDays aggregateAbsenceDays) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateAbsenceDays, true);
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
	 * @param domain ドメイン：集計欠勤日数
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計欠勤日数
	 */
	private KrcdtMonAggrAbsnDays toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateAbsenceDays domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrAbsnDaysPK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getAbsenceFrameNo());
		
		KrcdtMonAggrAbsnDays entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrAbsnDays.class, key);
		}
		else {
			entity = new KrcdtMonAggrAbsnDays();
			entity.PK = key;
		}
		entity.absenceDays = domain.getDays().v();
		entity.absenceTime = domain.getTime().v();
		return entity;
	}
}
