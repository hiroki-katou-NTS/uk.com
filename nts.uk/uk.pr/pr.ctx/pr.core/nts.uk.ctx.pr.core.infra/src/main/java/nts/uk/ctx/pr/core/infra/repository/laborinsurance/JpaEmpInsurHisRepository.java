package nts.uk.ctx.pr.core.infra.repository.laborinsurance;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;

import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHis;
import nts.uk.ctx.pr.core.dom.laborinsurance.EmpInsurHisRepository;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtEmpInsurHis;
import nts.uk.ctx.pr.core.infra.entity.laborinsurance.QpbmtEmpInsurHisPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class JpaEmpInsurHisRepository extends JpaRepository implements EmpInsurHisRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtEmpInsurHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurHisPk.cid =:cid AND  f.empInsurHisPk.hisId =:hisId ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.empInsurHisPk.cid =:cid ORDER BY f.startYearMonth DESC ";

    @Override
    public Optional<EmpInsurHis> getEmpInsurHisById(String cid, String hisId){
        List<QpbmtEmpInsurHis> qpbmtEmpInsurHisList = this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtEmpInsurHis.class)
        .setParameter("cid", cid)
        .setParameter("hisId", hisId)
        .getList();
        return Optional.ofNullable(new EmpInsurHis(cid,toDomain(qpbmtEmpInsurHisList)));
    }
    private List<YearMonthHistoryItem> toDomain(List<QpbmtEmpInsurHis> entities) {
        List<YearMonthHistoryItem> yearMonthHistoryItemList = new ArrayList<YearMonthHistoryItem>();
        if (entities == null || entities.isEmpty()) {
            return yearMonthHistoryItemList;
        }
        entities.forEach(entity -> {
            yearMonthHistoryItemList.add( new YearMonthHistoryItem(entity.empInsurHisPk.hisId,new YearMonthPeriod(new YearMonth(entity.startYearMonth),new YearMonth(entity.endYearMonth))));
        });
        return yearMonthHistoryItemList;
    }

    @Override
    public void add(YearMonthHistoryItem domain, String cId){
        this.commandProxy().insert(QpbmtEmpInsurHis.toEntity(domain, cId));
    }

    @Override
    public void update(YearMonthHistoryItem domain, String cId){
        this.commandProxy().update(QpbmtEmpInsurHis.toEntity(domain, cId));
    }

    @Override
    public void remove(String cid, String hisId){
        this.commandProxy().remove(QpbmtEmpInsurHis.class, new QpbmtEmpInsurHisPk(cid, hisId));
    }

	@Override
	public Optional<EmpInsurHis> getEmpInsurHisByCid(String cid) {
        List<QpbmtEmpInsurHis> qpbmtEmpInsurHisList = this.queryProxy().query(SELECT_BY_CID, QpbmtEmpInsurHis.class)
                .setParameter("cid", cid)
                .getList();
        return Optional.ofNullable(new EmpInsurHis(cid,toDomain(qpbmtEmpInsurHisList)));
	}
}
