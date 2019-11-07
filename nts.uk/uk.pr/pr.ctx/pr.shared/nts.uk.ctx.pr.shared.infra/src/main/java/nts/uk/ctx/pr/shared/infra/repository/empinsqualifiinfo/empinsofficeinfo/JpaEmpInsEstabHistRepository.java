package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.empinsofficeinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.empinsofficeinfo.EmpEstabInsHistRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.empinsofficeinfo.QqsmtEmpInsEsmHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpInsEstabHistRepository extends JpaRepository implements EmpEstabInsHistRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsEsmHist f";

    @Override
    public Optional<EmpEstabInsHist> getEmpInsHistById(String cid, String sid, String histId) {
        return Optional.empty();
    }

    @Override
    public Optional<EmpEstabInsHist> getListEmpInsHistByDate(String cid, String sid, GeneralDate fillingDate) {
        return Optional.empty();
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

}
