package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOffice;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpInsOfficeRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo.QqsmtEmpInsEsmHist;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo.QqsmtEmpInsEsmHistPk;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpInsEstabHistRepository extends JpaRepository implements EmpEstabInsHistRepository, EmpInsOfficeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsEsmHist f";
    private static final String SELECT_BY_HIST_IDS_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empInsEsmHistPk.cid = :cid AND f.empInsEsmHistPk.histId IN :histIds AND f.startDate <= :endDate AND f.startDate >= :startDate";
    private static final String SELECT_BY_HIST_IDS_AND_END_DATE_IN_PERIOD = SELECT_ALL_QUERY_STRING + " WHERE f.empInsEsmHistPk.cid = :cid AND f.empInsEsmHistPk.histId IN :histIds AND f.endDate <= :endDate AND f.endDate >= :startDate";
    private static final String SELECT_BY_KEY = SELECT_ALL_QUERY_STRING + " WHERE f.empInsEsmHistPk.histId = :histId and f.empInsEsmHistPk.cid = :cid and f.empInsEsmHistPk.sid = :sid";


    @Override
    public List<EmpInsOffice> getByHistIdsAndStartDateInPeriod(List<String> histIds, GeneralDate startDate, GeneralDate endDate) {
        List<EmpInsOffice> result = new ArrayList<>();
        CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, histList ->
            result.addAll(this.queryProxy().query(SELECT_BY_HIST_IDS_AND_DATE, QqsmtEmpInsEsmHist.class)
                    .setParameter("cid", AppContexts.user().companyId())
                    .setParameter("histIds", histIds)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getList(e -> new EmpInsOffice(e.empInsEsmHistPk.histId, e.empInsEsmHistPk.laborInsCd)))
        );
         return result;
    }

    @Override
    public List<EmpInsOffice> getByHistIdsAndEndDateInPeriod(List<String> hisIds, GeneralDate startDate, GeneralDate endDate){
        List<EmpInsOffice> result = new ArrayList<>();
        CollectionUtil.split(hisIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, histList ->
                result.addAll(this.queryProxy().query(SELECT_BY_HIST_IDS_AND_END_DATE_IN_PERIOD, QqsmtEmpInsEsmHist.class)
                        .setParameter("cid", AppContexts.user().companyId())
                        .setParameter("histIds", hisIds)
                        .setParameter("startDate", startDate)
                        .setParameter("endDate", endDate)
                        .getList(e -> new EmpInsOffice(e.empInsEsmHistPk.histId, e.empInsEsmHistPk.laborInsCd)))
        );
        return result;
    }

    private EmpEstabInsHist toEmploymentHistory(List<QqsmtEmpInsEsmHist> listHist) {
        EmpEstabInsHist empment = new EmpEstabInsHist(listHist.get(0).empInsEsmHistPk.sid,
                new ArrayList<>());
        DateHistoryItem dateItem = null;
        for (QqsmtEmpInsEsmHist item : listHist) {
            dateItem = new DateHistoryItem(item.empInsEsmHistPk.histId, new DatePeriod(item.startDate, item.endDate));
            empment.getDateHistoryItemList().add(dateItem);
        }
        return empment;
    }

	@Override
	public Optional<EmpInsOffice> getEmpInsOfficeById(String cid, String sid, String histId) {
		Optional<QqsmtEmpInsEsmHist> entity = this.queryProxy().query(SELECT_BY_KEY, QqsmtEmpInsEsmHist.class)
                .setParameter("cid", cid)
		        .setParameter("sid", sid)
                .setParameter("histId",histId).getSingle();
		return entity.map(r -> new EmpInsOffice(r.empInsEsmHistPk.histId, r.empInsEsmHistPk.laborInsCd));
	}

}
