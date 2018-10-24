package nts.uk.ctx.at.record.infra.repository.monthly.mergetable;

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
import nts.uk.ctx.at.record.dom.monthly.mergetable.MonthMergeKey;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMerge;
import nts.uk.ctx.at.record.dom.monthly.mergetable.RemainMergeRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonMergePk;
import nts.uk.ctx.at.record.infra.entity.monthly.mergetable.KrcdtMonRemain;

/**
 * リポジトリ実装：残数系
 * @author shuichi_ishida
 */
@Stateless
public class JpaRemainMerge extends JpaRepository implements RemainMergeRepository {

	private static final String DELETE_BY_PK = String.join(" ", "DELETE FROM KrcdtMonRemain a ",
			"WHERE  a.krcdtMonRemainPk.employeeId = :employeeId ",
			"AND    a.krcdtMonRemainPk.yearMonth = :yearMonth ",
			"AND    a.krcdtMonRemainPk.closureId = :closureId",
			"AND    a.krcdtMonRemainPk.closureDay = :closureDay",
			"AND    a.krcdtMonRemainPk.isLastDay = :isLastDay");
	
	private static final String FIND_BY_SIDS_AND_MONTHS = "SELECT a FROM KrcdtMonRemain a "
			+ "WHERE a.krcdtMonRemainPk.employeeId IN :employeeIds "
			+ "AND a.krcdtMonRemainPk.yearMonth IN :yearMonths "
			+ "ORDER BY a.krcdtMonRemainPk.employeeId, a.startDate ";
	
	/** 検索 */
	@Override
	public Optional<RemainMerge> find(MonthMergeKey key) {
		
		return this.queryProxy()
				.find(new KrcdtMonMergePk(
						key.getEmployeeId(),
						key.getYearMonth().v(),
						key.getClosureId().value,
						key.getClosureDate().getClosureDay().v(),
						(key.getClosureDate().getLastDayOfMonth() ? 1 : 0)),
						KrcdtMonRemain.class)
				.map(c -> c.toDomain());
	}
	
	/** 検索　（社員IDリストと年月リスト） */
	@Override
	public List<RemainMerge> findBySidsAndYearMonths(List<String> employeeIds, List<YearMonth> yearMonths) {
		
		val yearMonthValues = yearMonths.stream().map(c -> c.v()).collect(Collectors.toList());
		
		List<RemainMerge> results = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			results.addAll(this.queryProxy().query(FIND_BY_SIDS_AND_MONTHS, KrcdtMonRemain.class)
											.setParameter("employeeIds", splitData)
											.setParameter("yearMonths", yearMonthValues)
											.getList(c -> c.toDomain()));
		});
		return results;
	}
	
	/** 登録および更新 */
	@Override
	public void persistAndUpdate(MonthMergeKey key, RemainMerge domains) {
		
		// キー
		val entityKey = new KrcdtMonMergePk(
				key.getEmployeeId(),
				key.getYearMonth().v(),
				key.getClosureId().value,
				key.getClosureDate().getClosureDay().v(),
				(key.getClosureDate().getLastDayOfMonth() ? 1 : 0));
		
		// 登録・更新
		KrcdtMonRemain entity = this.getEntityManager().find(KrcdtMonRemain.class, entityKey);
		if (entity == null){
			entity = new KrcdtMonRemain();
			entity.setKrcdtMonRemainPk(entityKey);
			entity.toEntityRemainMerge(domains);
			this.getEntityManager().persist(entity);
		}
		else {
			entity.toEntityRemainMerge(domains);
		}
	}
	
	/** 削除 */
	@Override
	public void remove(MonthMergeKey key) {
		
		this.getEntityManager().createQuery(DELETE_BY_PK)
				.setParameter("employeeId", key.getEmployeeId())
				.setParameter("yearMonth", key.getYearMonth().v())
				.setParameter("closureId", key.getClosureId().value)
				.setParameter("closureDay", key.getClosureDate().getClosureDay().v())
				.setParameter("isLastDay", (key.getClosureDate().getLastDayOfMonth() ? 1 : 0))
				.executeUpdate();
	}
}
