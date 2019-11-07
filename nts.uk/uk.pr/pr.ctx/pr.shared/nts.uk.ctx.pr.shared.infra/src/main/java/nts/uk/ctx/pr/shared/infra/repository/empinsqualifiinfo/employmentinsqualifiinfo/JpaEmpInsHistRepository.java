package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmpInsHistRepository extends JpaRepository implements EmpInsHistRepository,EmpInsNumInfoRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsHist f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid =:sid ";
    private static final String SELECT_BY_KEY_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid =:sid AND  f.empInsHistPk.histId =:histId ";

    @Override
    public List<EmpInsHist> getAllEmpInsHist(){
       return null;
    }

    @Override
    public Optional<EmpInsHist> getEmpInsHistById(String cid, String sid){
        List<QqsmtEmpInsHist> listHist = this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsHist.class)
                .setParameter("sid", sid)
                .setParameter("cid", cid)
                .getList();
        if (listHist != null && !listHist.isEmpty()) {
            return Optional.of(toEmploymentHistory(listHist));
        }
        return Optional.empty();
    }
    private EmpInsHist toEmploymentHistory(List<QqsmtEmpInsHist> listHist) {
        EmpInsHist empment = new EmpInsHist(listHist.get(0).empInsHistPk.sid,
                new ArrayList<>());
        DateHistoryItem dateItem = null;
        for (QqsmtEmpInsHist item : listHist) {
            dateItem = new DateHistoryItem(item.empInsHistPk.histId, new DatePeriod(item.startDate, item.endDate));
            empment.getHistoryItem().add(dateItem);
        }
        return empment;
    }


    @Override
    public Optional<EmpInsNumInfo> getEmpInsNumInfoById(String cid, String sid, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_HIS, QqsmtEmpInsHist.class)
                .setParameter("sid", sid)
                .setParameter("cid", cid)
                .setParameter("hisId", hisId)
                .getSingle(e ->{
                    return new EmpInsNumInfo(e.empInsHistPk.histId,e.empInsNumber);
                });
    }
}
