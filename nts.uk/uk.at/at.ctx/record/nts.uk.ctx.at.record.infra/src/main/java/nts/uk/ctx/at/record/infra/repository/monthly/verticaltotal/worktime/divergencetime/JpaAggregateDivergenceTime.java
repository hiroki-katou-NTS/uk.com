package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.worktime.divergencetime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.divergencetime.AggregateDivergenceTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrDivgTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrDivgTimePK;

/**
 * リポジトリ実装：集計乖離時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateDivergenceTime extends JpaRepository implements AggregateDivergenceTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrDivgTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateDivergenceTime aggregateDivergenceTime) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateDivergenceTime, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateDivergenceTime aggregateDivergenceTime) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateDivergenceTime, true);
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
	 * @param domain ドメイン：集計乖離時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計乖離時間
	 */
	private KrcdtMonAggrDivgTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateDivergenceTime domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrDivgTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getDivergenceTimeNo());
		
		KrcdtMonAggrDivgTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrDivgTime.class, key);
		}
		else {
			entity = new KrcdtMonAggrDivgTime();
			entity.PK = key;
		}
		entity.divergenceAtr = domain.getDivergenceAtr().value;
		entity.divergenceTime = domain.getDivergenceTime().v();
		entity.deductionTime = domain.getDeductionTime().v();
		entity.divergenceTimeAfterDeduction = domain.getDivergenceTimeAfterDeduction().v();
		return entity;
	}
}
