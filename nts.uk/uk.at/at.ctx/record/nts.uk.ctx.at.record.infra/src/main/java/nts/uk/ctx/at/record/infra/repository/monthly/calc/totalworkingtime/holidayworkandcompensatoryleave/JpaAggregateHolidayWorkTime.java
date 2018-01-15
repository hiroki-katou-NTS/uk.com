package nts.uk.ctx.at.record.infra.repository.monthly.calc.totalworkingtime.holidayworkandcompensatoryleave;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTime;
import nts.uk.ctx.at.record.dom.monthly.calc.totalworkingtime.hdwkandcompleave.AggregateHolidayWorkTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTime;
import nts.uk.ctx.at.record.infra.entity.monthly.calc.totalworkingtime.hdwkandcompleave.KrcdtMonAggrHdwkTimePK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;

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
		
		this.commandProxy().insert(toEntity(attendanceTimeOfMonthlyKey, aggregateHolidayWorkTime));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime) {

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAggrHdwkTimePK key = new KrcdtMonAggrHdwkTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				aggregateHolidayWorkTime.getHolidayWorkFrameNo().v());
		
		KrcdtMonAggrHdwkTime entity = this.queryProxy().find(key, KrcdtMonAggrHdwkTime.class).get();
		entity.holidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v();
		entity.calcHolidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v();
		entity.transferTime = aggregateHolidayWorkTime.getTransferTime().getTime().v();
		entity.calcTransferTime = aggregateHolidayWorkTime.getTransferTime().getCalcTime().v();
		entity.legalHolidayWorkTime = aggregateHolidayWorkTime.getLegalHolidayWorkTime().v();
		entity.legalTransferHolidayWorkTime = aggregateHolidayWorkTime.getLegalTransferHolidayWorkTime().v();
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
	 * @param aggregateHolidayWorkTime ドメイン：集計休出時間
	 * @return エンティティ：集計休出時間
	 */
	private static KrcdtMonAggrHdwkTime toEntity(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateHolidayWorkTime aggregateHolidayWorkTime){

		// 締め日付
		ClosureDate closureDate = attendanceTimeOfMonthlyKey.getClosureDate();
		
		// キー
		KrcdtMonAggrHdwkTimePK key = new KrcdtMonAggrHdwkTimePK(
				attendanceTimeOfMonthlyKey.getEmployeeId(),
				attendanceTimeOfMonthlyKey.getYearMonth().v(),
				attendanceTimeOfMonthlyKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				aggregateHolidayWorkTime.getHolidayWorkFrameNo().v());
		
		KrcdtMonAggrHdwkTime entity = new KrcdtMonAggrHdwkTime();
		entity.PK = key;
		entity.holidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getTime().v();
		entity.calcHolidayWorkTime = aggregateHolidayWorkTime.getHolidayWorkTime().getCalcTime().v();
		entity.beforeHolidayWorkTime = aggregateHolidayWorkTime.getBeforeHolidayWorkTime().v();
		entity.transferTime = aggregateHolidayWorkTime.getTransferTime().getTime().v();
		entity.calcTransferTime = aggregateHolidayWorkTime.getTransferTime().getCalcTime().v();
		entity.legalHolidayWorkTime = aggregateHolidayWorkTime.getLegalHolidayWorkTime().v();
		entity.legalTransferHolidayWorkTime = aggregateHolidayWorkTime.getLegalTransferHolidayWorkTime().v();
		return entity;
	}
}
