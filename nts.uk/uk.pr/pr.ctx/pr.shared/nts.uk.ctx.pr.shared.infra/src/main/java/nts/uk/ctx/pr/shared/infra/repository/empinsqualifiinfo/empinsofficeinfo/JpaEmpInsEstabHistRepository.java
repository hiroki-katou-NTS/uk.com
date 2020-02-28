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
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaEmpInsEstabHistRepository extends JpaRepository implements EmpEstabInsHistRepository, EmpInsOfficeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsEsmHist f";
    private static final String SELECT_BY_HIST_IDS_AND_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.empInsEsmHistPk.histId IN :histIds AND f.startDate <= :endDate AND f.endDate >= :endDate";


    @Override
    public Optional<EmpEstabInsHist> getEmpInsHistById(String cid, String sid, String histId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpEstabInsHist> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate) {
        return Optional.empty();
    }

    @Override
    public List<EmpInsOffice> getByHistIdsAndDate(List<String> histIds, GeneralDate endDate) {
        List<EmpInsOffice> result = new ArrayList<>();
        CollectionUtil.split(histIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, histList ->
            result.addAll(toEmpInsOfficeDomain(this.queryProxy().query(SELECT_BY_HIST_IDS_AND_DATE, QqsmtEmpInsEsmHist.class)
                    .setParameter("histIds", histIds)
                    .setParameter("endDate", endDate)
                    .getList()))
        );
         return result;
    }

    private List<EmpInsOffice> toEmpInsOfficeDomain(List<QqsmtEmpInsEsmHist> listHist) {
        List<EmpInsOffice> domains = new ArrayList<>();
        listHist.stream().collect(Collectors.groupingBy(e -> e.empInsEsmHistPk.sid, Collectors.toList())).forEach((k, v) ->
                domains.add(new EmpInsOffice(k, v.get(0).laborInsCd)));
        return domains;
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
    public List<EmpInsOffice> getAllEmpEmpmInsOffice() {
        return null;
    }

	@Override
	public Optional<EmpInsOffice> getEmpInsOfficeById(String cid, String sid, String histId) {
		Optional<QqsmtEmpInsEsmHist> entity = this.queryProxy().find(new QqsmtEmpInsEsmHistPk(cid, sid, histId), QqsmtEmpInsEsmHist.class);
		return entity.map(r -> new EmpInsOffice(r.empInsEsmHistPk.histId, r.laborInsCd));
	}
}
