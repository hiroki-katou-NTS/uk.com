package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTableHistory;

@Stateless
public class JpaWageTableHistoryRepository extends JpaRepository implements WageTableHistoryRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtWageTableHistory f";
	private static final String SELECT_BY_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.pk.companyId =:cid AND"
			+ " f.startYm <= :yearMonth AND f.endYm >= :yearMonth ";

	@Override
	public List<WageTableHistory> getWageTableHistByYearMonth(String cid, YearMonth yearMonth) {
		Map<String, List<QpbmtWageTableHistory>> mapEntities = this.queryProxy()
				.query(SELECT_BY_YM, QpbmtWageTableHistory.class).setParameter("cid", cid)
				.setParameter("yearMonth", yearMonth.v()).getList().stream()
				.collect(Collectors.groupingBy(x -> x.pk.code));
		List<WageTableHistory> result = new ArrayList<>();
		mapEntities.entrySet().forEach(x -> {
			result.add(QpbmtWageTableHistory.toDomain(x.getValue()));
		});
		return result;
	}

	@Override
	public List<WageTableHistory> getWageTableHistByCodes(String companyId, List<String> codes) {
		if (codes == null || codes.isEmpty())
			return Collections.emptyList();
		String query = "SELECT f FROM QpbmtWageTableHistory f WHERE  f.pk.companyId =:cid AND f.pk.code IN :codes ORDER BY f.pk.code ASC, f.startYm DESC";
		Map<String, List<QpbmtWageTableHistory>> mapEntities = this.queryProxy()
				.query(query, QpbmtWageTableHistory.class).setParameter("cid", companyId).setParameter("codes", codes)
				.getList().stream().collect(Collectors.groupingBy(x -> x.pk.code));
		List<WageTableHistory> result = new ArrayList<>();
		mapEntities.entrySet().forEach(x -> {
			result.add(QpbmtWageTableHistory.toDomain(x.getValue()));
		});
		return result;
	}

	@Override
	public Optional<WageTableHistory> getWageTableHistByCode(String companyId, String code) {
		String query = "SELECT f FROM QpbmtWageTableHistory f WHERE  f.pk.companyId =:cid AND"
				+ " f.pk.code = :code ORDER BY f.startYm DESC";
		List<QpbmtWageTableHistory> listEntities = this.queryProxy().query(query, QpbmtWageTableHistory.class)
				.setParameter("cid", companyId).setParameter("code", code).getList();
		if (listEntities.isEmpty())
			return Optional.empty();
		else
			return Optional.of(QpbmtWageTableHistory.toDomain(listEntities));
	}

	@Override
	public void addOrUpdate(WageTableHistory domain) {
		this.remove(domain.getCid(), domain.getWageTableCode().v());
		List<QpbmtWageTableHistory> listEntities = QpbmtWageTableHistory.fromDomain(domain);
		this.commandProxy().insertAll(listEntities);
	}

	@Override
	public void remove(String companyId, String code) {
		String query = "DELETE FROM QpbmtWageTableHistory a WHERE a.pk.companyId = :companyId AND a.pk.code = :wageTableCode";
		this.getEntityManager().createQuery(query).setParameter("companyId", companyId)
				.setParameter("wageTableCode", code).executeUpdate();
	}

}
