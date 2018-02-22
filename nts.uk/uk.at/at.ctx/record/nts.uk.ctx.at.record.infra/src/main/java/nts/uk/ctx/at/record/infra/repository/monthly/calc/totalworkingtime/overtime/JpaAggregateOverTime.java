package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTimePK;

/**
 * リポジトリ実装：集計残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateOverTime extends JpaRepository implements AggregateOverTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrOverTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTime aggregateOverTimeWork) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateOverTimeWork, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTime aggregateOverTime) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateOverTime, true);
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
	 * @param domain ドメイン：集計残業時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計残業時間
	 */
	private KrcdtMonAggrOverTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateOverTime domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrOverTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getOverTimeFrameNo().v());
		
		KrcdtMonAggrOverTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrOverTime.class, key);
		}
		else {
			entity = new KrcdtMonAggrOverTime();
			entity.PK = key;
		}
		entity.overTime = domain.getOverTime().getTime().v();
		entity.calcOverTime = domain.getOverTime().getCalcTime().v();
		entity.beforeOverTime = domain.getBeforeOverTime().v();
		entity.transferOverTime = domain.getTransferOverTime().getTime().v();
		entity.calcTransferOverTime = domain.getTransferOverTime().getCalcTime().v();
		entity.legalOverTime = domain.getLegalOverTime().v();
		entity.legalTransferOverTime = domain.getLegalTransferOverTime().v();
		return entity;
	}
}
