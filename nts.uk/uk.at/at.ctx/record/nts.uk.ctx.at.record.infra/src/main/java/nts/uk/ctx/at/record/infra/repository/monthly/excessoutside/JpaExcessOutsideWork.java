package nts.uk.ctx.at.record.infra.repository.monthly.excessoutside;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWork;
import nts.uk.ctx.at.record.dom.monthly.excessoutside.ExcessOutsideWorkRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcoutTime;
import nts.uk.ctx.at.record.infra.entity.monthly.excessoutside.KrcdtMonExcoutTimePK;

/**
 * リポジトリ実装：時間外超過
 * @author shuichu_ishida
 */
@Stateless
public class JpaExcessOutsideWork extends JpaRepository implements ExcessOutsideWorkRepository {

	private static final String DELETE_BY_PARENT_PK = "DELETE FROM KrcdtMonExcoutTime a "
			+ "WHERE a.PK.employeeID = :employeeID "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay ";

	/** 追加 */
	@Override
	public void insert(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, ExcessOutsideWork excessOutsideWork) {
		
		this.getEntityManager().persist(toEntity(attendanceTimeOfMonthlyKey, excessOutsideWork, false));
	}

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey, ExcessOutsideWork excessOutsideWork) {

		this.toEntity(attendanceTimeOfMonthlyKey, excessOutsideWork, true);
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
	 * @param domain ドメイン：時間外超過
	 * @param execUpdate 更新を実行する
	 * @return エンティティ：時間外超過
	 */
	private KrcdtMonExcoutTime toEntity(AttendanceTimeOfMonthlyKey domainKey,
			ExcessOutsideWork domain, boolean execUpdate){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonExcoutTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0),
				domain.getBreakdownNo(),
				domain.getExcessNo());
		
		KrcdtMonExcoutTime entity;
		if (execUpdate){
			entity = this.getEntityManager().find(KrcdtMonExcoutTime.class, key);
		}
		else {
			entity = new KrcdtMonExcoutTime();
			entity.PK = key;
		}
		entity.excessTime = domain.getExcessTime().v();
		return entity;
	}
}
