package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.worktime.premiumtime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.AggregatePremiumTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.premiumtime.AggregatePremiumTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrPremTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrPremTimePK;

/**
 * リポジトリ実装：集計割増時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregatePremiumTime extends JpaRepository implements AggregatePremiumTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrPremTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregatePremiumTime aggregatePremiumTime) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregatePremiumTime, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregatePremiumTime aggregatePremiumTime) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregatePremiumTime, true);
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
	 * @param domain ドメイン：集計割増時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計割増時間
	 */
	private KrcdtMonAggrPremTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregatePremiumTime domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrPremTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getPremiumTimeItemNo());
		
		KrcdtMonAggrPremTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrPremTime.class, key);
		}
		else {
			entity = new KrcdtMonAggrPremTime();
			entity.PK = key;
		}
		entity.premiumTime = domain.getTime().v();
		return entity;
	}
}
