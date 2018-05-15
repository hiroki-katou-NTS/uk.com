package nts.uk.ctx.at.record.infra.repository.monthly.vacation.annualleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave.KrcdtMonAnnleaRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave.KrcdtMonAnnleaRemainPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

/**
 * リポジトリ実装：年休月別残数データ
 * @author shuichu_ishida
 */
@Stateless
public class JpaAnnLeaRemNumEachMonth extends JpaRepository implements AnnLeaRemNumEachMonthRepository {

	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "ORDER BY a.startYmd ";

	private static final String FIND_BY_YM_AND_CLOSURE_ID = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "ORDER BY a.startYmd ";

	private static final String DELETE_BY_YEAR_MONTH = "DELETE FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth ";

	/** 検索 */
	@Override
	public Optional<AnnLeaRemNumEachMonth> find(String employeeId, YearMonth yearMonth, ClosureId closureId,
			ClosureDate closureDate) {
		
		return this.queryProxy()
				.find(new KrcdtMonAnnleaRemainPK(
						employeeId,
						yearMonth.v(),
						closureId.value,
						closureDate.getClosureDay().v(),
						(closureDate.getLastDayOfMonth() ? 1 : 0)),
						KrcdtMonAnnleaRemain.class)
				.map(c -> c.toDomain());
	}
	
	/** 検索　（年月） */
	@Override
	public List<AnnLeaRemNumEachMonth> findByYearMonthOrderByStartYmd(String employeeId, YearMonth yearMonth) {
		
		return this.queryProxy().query(FIND_BY_YEAR_MONTH, KrcdtMonAnnleaRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.getList(c -> c.toDomain());
	}
	
	/** 検索　（年月と締めID） */
	@Override
	public List<AnnLeaRemNumEachMonth> findByYMAndClosureIdOrderByStartYmd(String employeeId, YearMonth yearMonth,
			ClosureId closureId) {
		
		return this.queryProxy().query(FIND_BY_YM_AND_CLOSURE_ID, KrcdtMonAnnleaRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("yearMonth", yearMonth.v())
				.setParameter("closureId", closureId.value)
				.getList(c -> c.toDomain());
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(AnnLeaRemNumEachMonth domain) {
		
		// キー
		val key = new KrcdtMonAnnleaRemainPK(
				domain.getEmployeeId(),
				domain.getYearMonth().v(),
				domain.getClosureId().value,
				domain.getClosureDate().getClosureDay().v(),
				(domain.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonAnnleaRemain entity = this.getEntityManager().find(KrcdtMonAnnleaRemain.class, key);
		if (entity == null){
			entity = new KrcdtMonAnnleaRemain();
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
		
		this.commandProxy().remove(KrcdtMonAnnleaRemain.class,
				new KrcdtMonAnnleaRemainPK(
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
