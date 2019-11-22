package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;
import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHistRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsHist;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaEmpInsHistRepository extends JpaRepository implements EmpInsHistRepository,EmpInsNumInfoRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpInsHist f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid IN :sids AND f.startDate <= :baseDate AND  f.endDate >= :baseDate ";
    private static final String SELECT_BY_KEY_HIS = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsHistPk.cid =:cid AND  f.empInsHistPk.sid =:sid AND  f.empInsHistPk.histId =:histId ";


    @Override
    public List<EmpInsHist> getEmpInsHistById(String cid, List<String> sids, GeneralDate baseDate){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsHist.class)
                .setParameter("sids", sids)
                .setParameter("cid", cid)
                .setParameter("baseDate", baseDate)
                .getList(QqsmtEmpInsHist::toEmploymentHistory);
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
}
