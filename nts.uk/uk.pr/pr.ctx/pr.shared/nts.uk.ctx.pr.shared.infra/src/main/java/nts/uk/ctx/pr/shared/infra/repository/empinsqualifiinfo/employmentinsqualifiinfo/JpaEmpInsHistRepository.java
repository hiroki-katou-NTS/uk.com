package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsHist;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmpInsHistRepository extends JpaRepository implements EmpInsHistRepository,EmpInsNumInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsHist f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid IN :sids AND f.startDate <= :baseDate AND  f.endDate >= :baseDate ";
    private static final String SELECT_BY_KEY_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid =:sid AND  f.empInsHistPk.histId =:histId ";
    private static final String SELECT_BY_EMP_IDS_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empInsHistPk.cid = :cid AND f.empInsHistPk.sid IN :sids AND f.startDate <= :startDate AND f.endDate >= :startDate";
    private static final String SELECT_BY_HIST_IDS = SELECT_ALL_QUERY_STRING + " WHERE f.empInsHistPk.cid = :cid AND f.empInsHistPk.histId IN :histIds";
	private static final String SELECT_BY_EMP_AND_PERIOD = SELECT_ALL_QUERY_STRING
			+ " WHERE f.empInsHistPk.sid = :sid AND f.endDate >= :startDate AND f.endDate <= :endDate";

	@Override
	public List<EmpInsHist> getAllEmpInsHist() {
		return null;
	}


	@Override
	public List<EmpInsHist> getByEmpIdsAndStartDate(List<String> empIds, GeneralDate startDate) {
		List<EmpInsHist> result = new ArrayList<>();
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitIdList -> {
			result.addAll(toEmpInsHistDomain(this.queryProxy().query(SELECT_BY_EMP_IDS_AND_DATE, QqsmtEmpInsHist.class)
					.setParameter("cid", AppContexts.user().companyId()).setParameter("sids", splitIdList)
					.setParameter("startDate", startDate).getList()));
		});
		return result;
	}


    @Override
    public List<EmpInsHist> getEmpInsHistById(String cid, List<String> sids, GeneralDate baseDate){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsHist.class)
                .setParameter("sids", sids)
                .setParameter("cid", cid)
                .setParameter("baseDate", baseDate)
                .getList(QqsmtEmpInsHist::toEmploymentHistory);
    }
	private EmpInsHist toEmploymentHistory(List<QqsmtEmpInsHist> listHist) {
		EmpInsHist empment = new EmpInsHist(listHist.get(0).empInsHistPk.sid, new ArrayList<>());
		DateHistoryItem dateItem = null;
		for (QqsmtEmpInsHist item : listHist) {
			dateItem = new DateHistoryItem(item.empInsHistPk.histId, new DatePeriod(item.startDate, item.endDate));
			empment.getHistoryItem().add(dateItem);
		}
		return empment;
	}
	private List<EmpInsHist> toEmpInsHistDomain(List<QqsmtEmpInsHist> listHist) {
		List<EmpInsHist> domains = new ArrayList<>();
		listHist.stream().collect(Collectors.groupingBy(e -> e.empInsHistPk.sid, Collectors.toList()))
				.forEach((k, v) -> {
					List<DateHistoryItem> historyItem = v.stream()
							.sorted((o1, o2) -> o2.startDate.compareTo(o1.startDate))
							.map(e -> new DateHistoryItem(e.empInsHistPk.histId,
									new DatePeriod(e.startDate, e.endDate)))
							.collect(Collectors.toList());
					domains.add(new EmpInsHist(k, historyItem));
				});
		return domains;
	}

    @Override
    public Optional<EmpInsNumInfo> getEmpInsNumInfoById(String cid, String sid, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_HIS, QqsmtEmpInsHist.class)
                .setParameter("sid", sid)
                .setParameter("cid", cid)
                .setParameter("histId", hisId)
                .getSingle(e ->{
                    return new EmpInsNumInfo(e.empInsHistPk.histId,e.empInsNumber);
                });
    }
	@Override
	public List<EmpInsNumInfo> getByCidAndHistIds(String cid, List<String> histIds) {
		List<EmpInsNumInfo> result = new ArrayList<>();
		CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT,
				splitList -> result.addAll(this.queryProxy().query(SELECT_BY_HIST_IDS, QqsmtEmpInsHist.class)
						.setParameter("histIds", histIds).setParameter("cid", cid)
						.getList(e -> new EmpInsNumInfo(e.empInsHistPk.histId, e.empInsNumber))));
		return result;
	}

	@Override
	public Optional<EmpInsHist> getByEmpIdsAndPeriod(String sId, DatePeriod period) {
		List<QqsmtEmpInsHist> empInsHists = this.queryProxy().query(SELECT_BY_EMP_AND_PERIOD, QqsmtEmpInsHist.class)
				.setParameter("sid", sId).setParameter("startDate", period.start())
				.setParameter("endDate", period.end()).getList();
		if (empInsHists != null && !empInsHists.isEmpty()) {
			return Optional.of(toEmploymentHistory(empInsHists));
		}
		return Optional.empty();
	}

	@Override
	public List<EmpInsHist> getByEmpIdsAndStartDateInPeriod(String companyId, List<String> empIds,
			GeneralDate startDate, GeneralDate endDate) {
		List<EmpInsHist> result = new ArrayList<>();
		if (empIds == null || empIds.isEmpty())
			return result;
		String query = "SELECT f FROM QqsmtEmpInsHist f WHERE f.empInsHistPk.cid = :cid AND f.empInsHistPk.sid IN :sids AND f.startDate >= :startDate AND f.startDate <= :endDate";
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitIdList -> {
			result.addAll(toEmpInsHistDomain(this.queryProxy().query(query, QqsmtEmpInsHist.class)
					.setParameter("cid", companyId).setParameter("sids", splitIdList)
					.setParameter("startDate", startDate).setParameter("endDate", endDate).getList()));
		});
		return result;
	}

	@Override
	public List<EmpInsHist> getByEmpIdsAndEndDateInPeriod(String companyId, List<String> empIds, GeneralDate startDate,
			GeneralDate endDate) {
		List<EmpInsHist> result = new ArrayList<>();
		if (empIds == null || empIds.isEmpty())
			return result;
		String query = "SELECT f FROM QqsmtEmpInsHist f WHERE f.empInsHistPk.cid = :cid AND f.empInsHistPk.sid IN :sids AND f.endDate >= :startDate AND f.endDate <= :endDate";
		CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitIdList -> {
			result.addAll(toEmpInsHistDomain(this.queryProxy().query(query, QqsmtEmpInsHist.class)
					.setParameter("cid", companyId).setParameter("sids", splitIdList)
					.setParameter("startDate", startDate).setParameter("endDate", endDate).getList()));
		});
		return result;
	}
}
