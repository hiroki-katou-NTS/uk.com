package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal.worktime.bonuspaytime;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTime;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.bonuspaytime.AggregateBonusPayTimeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrBnspyTime;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.worktime.KrcdtMonAggrBnspyTimePK;

/**
 * リポジトリ実装：集計加給時間
 * @author shuichu_ishida
 */
@Stateless
public class JpaAggregateBonusPayTime extends JpaRepository implements AggregateBonusPayTimeRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonAggrBnspyTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";
	
	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateBonusPayTime aggregateBonusPayTime) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, aggregateBonusPayTime, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			AggregateBonusPayTime aggregateBonusPayTime) {

		this.toEntity(attendanceTimeOfMonthlyKey, aggregateBonusPayTime, true);
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
	 * @param domain ドメイン：集計加給時間
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：集計加給時間
	 */
	private KrcdtMonAggrBnspyTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			AggregateBonusPayTime domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAggrBnspyTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getBonusPayFrameNo());
		
		KrcdtMonAggrBnspyTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonAggrBnspyTime.class, key);
		}
		else {
			entity = new KrcdtMonAggrBnspyTime();
			entity.PK = key;
		}
		entity.bonusPayTime = domain.getBonusPayTime().v();
		entity.specificBonusPayTime = domain.getSpecificBonusPayTime().v();
		entity.holidayWorkBonusPayTime = domain.getHolidayWorkBonusPayTime().v();
		entity.holidayWorkSpecificBonusPayTime = domain.getHolidayWorkSpecificBonusPayTime().v();
		return entity;
	}
}
