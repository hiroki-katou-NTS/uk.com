package nts.uk.ctx.at.record.infra.repository.monthly.vacation.reserveleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave.RsvLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.reserveleave.KrcdtMonRsvleaRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.reserveleave.KrcdtMonRsvleaRemainPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * リポジトリ実装：積立年休月別残数データ
 * @author shuichu_ishida
 */
@Stateless
public class JpaRsvLeaRemNumEachMonth extends JpaRepository implements RsvLeaRemNumEachMonthRepository {

	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonRsvleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_YM_AND_CLOSURE_ID = "SELECT a FROM KrcdtMonRsvleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "ORDER BY a.startYmd ";

	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonRsvleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth ";

	/** 検索 */
	@Override
	public Optional<RsvLeaRemNumEachMonth> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.queryProxy()
				.find(new KrcdtMonRsvleaRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)),
						KrcdtMonRsvleaRemain.class)
				.map(c -> c.toDomain());
	}
	
	/** 検索　（年月） */
	@Override
	public List<RsvLeaRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonRsvleaRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> c.toDomain());
	}
	
	/** 検索　（年月と締めID） */
	@Override
	public List<RsvLeaRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonRsvleaRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(RsvLeaRemNumEachMonth domain) {
		
		// キー
		val key = new KrcdtMonRsvleaRemainPK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonRsvleaRemain entity = this.getEntityManager().find(KrcdtMonRsvleaRemain.class, key);
		if (entity == null){
			entity = new KrcdtMonRsvleaRemain();
			entity.fromDomainForPersist(domain);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.fromDomainForUpdate(domain);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(String employeeId, YearMonth yearMonth, ClosureId closureId, ClosureDate closureDate) {
		
		this.commandProxy().remove(KrcdtMonRsvleaRemain.class,
				new KrcdtMonRsvleaRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)));
	}
	
	/** 削除　（年月） */
	@Override
	public void removeByYearMonth(String employeeId, YearMonth yearMonth) {
		
		this.getEntityManager().createQuery(DELETE_BY_YEAR_MONTH)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.executeUpdate();
	}
}
