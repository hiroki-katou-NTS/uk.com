package repository.wageprovision.companyuniformamount;

import entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHis;
import entity.wageprovision.companyuniformamount.QpbmtPayUnitPriceHisPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistory;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
@Stateless
public class JpaPayrollUnitPriceHistoryRepository extends JpaRepository implements PayrollUnitPriceHistoryRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPriceHis f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriceHisPk.cid =:cid AND  f.payUnitPriceHisPk.code =:code AND  f.payUnitPriceHisPk.hisId =:hisId ";

    @Override
    public List<PayrollUnitPriceHistory> getAllPayrollUnitPriceHistory(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPayUnitPriceHis.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PayrollUnitPriceHistory> getPayrollUnitPriceHistoryById(String cid, String code, String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPriceHis.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
                .setParameter("hisId", hisId)
                .getSingle(c->c.toDomain());
    }

    @Override
    public void add(PayrollUnitPriceHistory domain){
        this.commandProxy().insert(QpbmtPayUnitPriceHis.toEntity(domain));
    }

    @Override
    public void update(PayrollUnitPriceHistory domain){
        this.commandProxy().update(QpbmtPayUnitPriceHis.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code, String hisId){
        this.commandProxy().remove(QpbmtPayUnitPriceHis.class, new QpbmtPayUnitPriceHisPk(cid, code, hisId));
    }
}
