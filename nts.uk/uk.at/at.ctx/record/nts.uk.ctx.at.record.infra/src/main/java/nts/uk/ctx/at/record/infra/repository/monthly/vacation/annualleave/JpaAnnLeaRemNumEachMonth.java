package nts.uk.ctx.at.record.infra.repository.monthly.vacation.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave.KrcdtMonAnnleaRemain;
import nts.uk.ctx.at.record.infra.entity.monthly.vacation.annualleave.KrcdtMonAnnleaRemainPK;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * リポジトリ実装：年休月別残数データ
 * @author shuichu_ishida
 */
@Stateless
public class JpaAnnLeaRemNumEachMonth extends JpaRepository implements AnnLeaRemNumEachMonthRepository {

	private static final String FIND_BY_YEAR_MONTH = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "ORDER BY a.startDate ";

	private static final String FIND_BY_YM_AND_CLOSURE_ID = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "ORDER BY a.startDate ";

	private static final String FIND_BY_SIDS = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId IN :employeeIds "
			+ "AND a.PK.yearMonth = :yearMonth "
			+ "AND a.PK.closureId = :closureId "
			+ "AND a.PK.closureDay = :closureDay "
			+ "AND a.PK.isLastDay = :isLastDay "
			+ "ORDER BY a.PK.employeeId, a.startDate ";

	private static final String FIND_BY_SIDS_AND_MONTHS = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId IN :employeeIds "
			+ "AND a.PK.yearMonth IN :yearMonths "
			+ "ORDER BY a.PK.employeeId, a.startDate ";

	private static final String FIND_BY_CLOSURE_PERIOD = "SELECT a FROM KrcdtMonAnnleaRemain a "
			+ "WHERE a.PK.employeeId = :employeeId "
			+ "AND a.startDate >= :startDate "
			+ "AND a.endDate <= :endDate "
			+ "AND a.closureStatus = 1 "
			+ "ORDER BY a.startDate ";
	
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
	
	/** 検索　（社員IDリスト） */
	@Override
	public List<AnnLeaRemNumEachMonth> findbyEmployees(List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId, ClosureDate closureDate) {
		
		List<AnnLeaRemNumEachMonth> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_SIDS, KrcdtMonAnnleaRemain.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonth", yearMonth.v())
					.setParameter("closureId", closureId.value)
					.setParameter("closureDay", closureDate.getClosureDay().v())
					.setParameter("isLastDay", (closureDate.getLastDayOfMonth() ? 1 : 0))
					.getList(c -> c.toDomain()));
		});
		return results;
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<AnnLeaRemNumEachMonth> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<AnnLeaRemNumEachMonth> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_MONTHS, KrcdtMonAnnleaRemain.class)
					.setParameter("employeeIds", splitData)
					.setParameter("yearMonths", yearMonthValues)
					.getList(c -> c.toDomain()));
		});
		return results;
	}

	/** 検索　（社員IDと締め期間、条件＝締め済み） */
	@Override
	public List<AnnLeaRemNumEachMonth> findByClosurePeriod(String employeeId, DatePeriod closurePeriod) {
		
		return this.queryProxy().query(FIND_BY_CLOSURE_PERIOD, KrcdtMonAnnleaRemain.class)
				.setParameter("employeeId", employeeId)
				.setParameter("startDate", closurePeriod.start())
				.setParameter("endDate", closurePeriod.end())
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
