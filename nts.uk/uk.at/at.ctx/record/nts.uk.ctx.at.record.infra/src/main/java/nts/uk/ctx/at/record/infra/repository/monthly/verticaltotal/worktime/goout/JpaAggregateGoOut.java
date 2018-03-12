package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.worktime.goout;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOut;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.goout.AggregateGoOutRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrGoout;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrGooutPK;

/**
 * リポジトリ実装：集計外出
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateGoOut extends JpaRepository implements AggregateGoOutRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrGoout a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, AggregateGoOut aggregateGoOut) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateGoOut, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, AggregateGoOut aggregateGoOut) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateGoOut, true);
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
	 * @param domain ドメイン：集計外出
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計外出
	 */
	private KrcdtMonAggrGoout toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateGoOut domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrGooutPK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getGoOutReason().value);
		
		KrcdtMonAggrGoout entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrGoout.class, key);
		}
		else {
			entity = new KrcdtMonAggrGoout();
			entity.PK = key;
		}
		entity.goOutTimes = domain.getTimes().v();
		entity.legalTime = domain.getLegalTime().getTime().v();
		entity.calcLegalTime = domain.getLegalTime().getCalcTime().v();
		entity.illegalTime = domain.getIllegalTime().getTime().v();
		entity.calcIllegalTime = domain.getIllegalTime().getCalcTime().v();
		entity.totalTime = domain.getTotalTime().getTime().v();
		entity.calcTotalTime = domain.getTotalTime().getCalcTime().v();
		return entity;
	}
}
