package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtAggrHolidayWrkTm;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave.KrcdtAggrHolidayWrkTmPK;

/**
 * リポジトリ実装：集計休出時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateHolidayWorkTime extends JpaRepository implements AggregateHolidayWorkTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtAggrHolidayWrkTm a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.startYmd <= :startYmd "
			+ "AND a.PK.endYmd >= :endYmd ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime) {
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateHolidayWorkTime));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime) {
		
		KrcdtAggrHolidayWrkTmPK key = new KrcdtAggrHolidayWrkTmPK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end(),
				aggregateHolidayWorkTime.getHolidayWorkTimeFrameNo());
		KrcdtAggrHolidayWrkTm entity = this.queryProxy().find(key, KrcdtAggrHolidayWrkTm.class).get();
		entity.holidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v();
		entity.holidayWorkTimeCalc = aggregateHolidayWorkTime.getHolidayWorkTime().getCalculationTime().v();
		entity.beforeHolidayWorkTime = aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v();
		entity.transferHolidayWorkTime = aggregateHolidayWorkTime.getTransferHolidayWorkTime().getTime().v();
		entity.transferHolidayWorkTimeCalc = aggregateHolidayWorkTime.getTransferHolidayWorkTime().getCalculationTime().v();
		entity.withinStatutoryHolidayWorkTime = aggregateHolidayWorkTime.getWithinStatutoryHolidayWorkTime().v();
		entity.withinStatutoryTransferHolidayWorkTime = aggregateHolidayWorkTime.getWithinStatutoryTransferHolidayWorkTime().v();
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
	 * @param aggregateHolidayWorkTime ドメイン：集計休出時間
	 * @return エンティティ：集計休出時間
	 */
	private static KrcdtAggrHolidayWrkTm toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime){
		
		KrcdtAggrHolidayWrkTmPK key = new KrcdtAggrHolidayWrkTmPK(
				attendanceTimeOfMonthlyKey.getEmployeeID(),
				attendanceTimeOfMonthlyKey.getDatePeriod().start(),
				attendanceTimeOfMonthlyKey.getDatePeriod().end(),
				aggregateHolidayWorkTime.getHolidayWorkTimeFrameNo());
		KrcdtAggrHolidayWrkTm entity = new KrcdtAggrHolidayWrkTm();
		entity.PK = key;
		entity.holidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v();
		entity.holidayWorkTimeCalc = aggregateHolidayWorkTime.getHolidayWorkTime().getCalculationTime().v();
		entity.beforeHolidayWorkTime = aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v();
		entity.transferHolidayWorkTime = aggregateHolidayWorkTime.getTransferHolidayWorkTime().getTime().v();
		entity.transferHolidayWorkTimeCalc = aggregateHolidayWorkTime.getTransferHolidayWorkTime().getCalculationTime().v();
		entity.withinStatutoryHolidayWorkTime = aggregateHolidayWorkTime.getWithinStatutoryHolidayWorkTime().v();
		entity.withinStatutoryTransferHolidayWorkTime = aggregateHolidayWorkTime.getWithinStatutoryTransferHolidayWorkTime().v();
		return entity;
	}
}
