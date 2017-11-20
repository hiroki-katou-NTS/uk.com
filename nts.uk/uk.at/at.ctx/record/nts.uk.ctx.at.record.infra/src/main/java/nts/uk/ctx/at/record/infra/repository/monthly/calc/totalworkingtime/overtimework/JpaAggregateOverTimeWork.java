package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.overtimework;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.AggregateOverTimeWork;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.overtimework.AggregateOverTimeWorkRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtAggrOverTimeWork;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.overtimework.KrcdtAggrOverTimeWorkPK;

/**
 * リポジトリ実装：集計残業時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateOverTimeWork extends JpaRepository implements AggregateOverTimeWorkRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtAggrOverTimeWork a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.startYmd <= :startYmd "
			+ "AND a.PK.endYmd >= :endYmd ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTimeWork aggregateOverTimeWork) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateOverTimeWork));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTimeWork aggregateOverTimeWork) {
		
		KrcdtAggrOverTimeWorkPK key = new KrcdtAggrOverTimeWorkPK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end(),
				aggregateOverTimeWork.getOverTimeWorkFrameNo());
		KrcdtAggrOverTimeWork entity = this.queryProxy().find(key, KrcdtAggrOverTimeWork.class).get();
		entity.overTimeWork = aggregateOverTimeWork.getOverTimeWork().getTime().v();
		entity.overTimeWorkCalc = aggregateOverTimeWork.getOverTimeWork().getCalculationTime().v();
		entity.beforeOverTimeWork = aggregateOverTimeWork.getBeforeOverTimeWork().v();
		entity.transferOverTimeWork = aggregateOverTimeWork.getTransferOverTimeWork().getTime().v();
		entity.transferOverTimeWorkCalc = aggregateOverTimeWork.getTransferOverTimeWork().getCalculationTime().v();
		entity.withinStatutoryOverTimeWork = aggregateOverTimeWork.getWithinStatutoryOverTimeWork().v();
		entity.withinStatutoryTransferOverTimeWork = aggregateOverTimeWork.getWithinStatutoryTransferOverTimeWork().v();
		this.commandProxy().update(entity);
	}

	/** 削除　（親キー） */
	@Override
	public void removeByParentPK(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey) {
		this.getEntityManager().createQuery(DELETE_BY_PARENT_PK)
		.setParameter("employeeID", attendanceTimeOfMonthlyKey.getEmployeeID())
		.setParameter("startYmd", attendanceTimeOfMonthlyKey.getDatePeriod().start())
		.setParameter("endYmd", attendanceTimeOfMonthlyKey.getDatePeriod().end())
		.executeUpdate();
	}
	
	/**
	 * ドメイン→エンティティ
	 * @param attendanceTimeOfMonthlyKey キー値：月別実績の勤怠時間
	 * @param aggregateOverTimeWork ドメイン：集計残業時間
	 * @return エンティティ：集計残業時間
	 */
	private static KrcdtAggrOverTimeWork toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateOverTimeWork aggregateOverTimeWork){
		
		KrcdtAggrOverTimeWorkPK key = new KrcdtAggrOverTimeWorkPK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end(),
				aggregateOverTimeWork.getOverTimeWorkFrameNo());
		KrcdtAggrOverTimeWork entity = new KrcdtAggrOverTimeWork();
		entity.PK = key;
		entity.overTimeWork = aggregateOverTimeWork.getOverTimeWork().getTime().v();
		entity.overTimeWorkCalc = aggregateOverTimeWork.getOverTimeWork().getCalculationTime().v();
		entity.beforeOverTimeWork = aggregateOverTimeWork.getBeforeOverTimeWork().v();
		entity.transferOverTimeWork = aggregateOverTimeWork.getTransferOverTimeWork().getTime().v();
		entity.transferOverTimeWorkCalc = aggregateOverTimeWork.getTransferOverTimeWork().getCalculationTime().v();
		entity.withinStatutoryOverTimeWork = aggregateOverTimeWork.getWithinStatutoryOverTimeWork().v();
		entity.withinStatutoryTransferOverTimeWork = aggregateOverTimeWork.getWithinStatutoryTransferOverTimeWork().v();
		return entity;
	}
}
