package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	private static final String SELECT_BY_YM = SELECT_ALL_QUERY_STRING + " WHERE  f.companyId =:cid AND"
			+ " f.startYm <= :yearMonth AND f.endYm >= :yearMonth ";

	@Override
	public List<WageTableHistory> getWageTableHistByYearMonth(String cid, YearMonth yearMonth) {
		Map<String, List<QpbmtWageTableHistory>> mapEntities = this.queryProxy()
				.query(SELECT_BY_YM, QpbmtWageTableHistory.class).setParameter("cid", cid)
				.setParameter("yearMonth", yearMonth.v()).getList().stream()
				.collect(Collectors.groupingBy(x -> x.code));
		List<WageTableHistory> result = new ArrayList<>();
		mapEntities.entrySet().forEach(x -> {
			result.add(QpbmtWageTableHistory.toDomain(x.getValue()));
		});
		return result;
	}

}
