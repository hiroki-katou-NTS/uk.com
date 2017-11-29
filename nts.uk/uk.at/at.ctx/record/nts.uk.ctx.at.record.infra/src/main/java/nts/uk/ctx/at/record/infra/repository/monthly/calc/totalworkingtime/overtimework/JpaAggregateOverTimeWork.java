package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtimework;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtime.AggregateOverTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtime.KrcdtMonAggrOverTimePK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

/**
 * リポジトリ実装：集計残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateOverTimeWork extends JpaRepository implements AggregateOverTimeRepository {

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
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateOverTimeWork));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTime aggregateOverTime) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAggrOverTimePK key = new KrcdtMonAggrOverTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				aggregateOverTime.getOverTimeFrameNo().v());
		
		KrcdtMonAggrOverTime entity = this.queryProxy().find(key, KrcdtMonAggrOverTime.class).get();
		entity.overTime = aggregateOverTime.getOverTime().getTime().v();
		entity.calcOverTime = aggregateOverTime.getOverTime().getCalculationTime().v();
		entity.beforeOverTime = aggregateOverTime.getBeforeOverTime().v();
		entity.transferOverTime = aggregateOverTime.getTransferOverTime().getTime().v();
		entity.calcTransferOverTime = aggregateOverTime.getTransferOverTime().getCalculationTime().v();
		entity.legalOverTime = aggregateOverTime.getLegalOverTime().v();
		entity.legalTransferOverTime = aggregateOverTime.getLegalTransferOverTime().v();
		this.commandProxy().update(entity);
	}

	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey) {
		
		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
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
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateOverTime ドメイン：集計残業時間
	 * @return エンティティ：集計残業時間
	 */
	private static KrcdtMonAggrOverTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTime aggregateOverTime){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAggrOverTimePK key = new KrcdtMonAggrOverTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				aggregateOverTime.getOverTimeFrameNo().v());
		
		KrcdtMonAggrOverTime entity = new KrcdtMonAggrOverTime();
		entity.PK = key;
		entity.overTime = aggregateOverTime.getOverTime().getTime().v();
		entity.calcOverTime = aggregateOverTime.getOverTime().getCalculationTime().v();
		entity.beforeOverTime = aggregateOverTime.getBeforeOverTime().v();
		entity.transferOverTime = aggregateOverTime.getTransferOverTime().getTime().v();
		entity.calcTransferOverTime = aggregateOverTime.getTransferOverTime().getCalculationTime().v();
		entity.legalOverTime = aggregateOverTime.getLegalOverTime().v();
		entity.legalTransferOverTime = aggregateOverTime.getLegalTransferOverTime().v();
		return entity;
	}
}
