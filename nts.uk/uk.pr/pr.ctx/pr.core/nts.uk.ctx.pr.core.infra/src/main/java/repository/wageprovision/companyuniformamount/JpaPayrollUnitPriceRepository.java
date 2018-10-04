package repository.wageprovision.companyuniformamount;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;


import entity.wageprovision.companyuniformamount.QpbmtPayUnitPrice;
import entity.wageprovision.companyuniformamount.QpbmtPayUnitPricePk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPrice;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceRepository;

@Stateless
public class JpaPayrollUnitPriceRepository extends JpaRepository implements PayrollUnitPriceRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPrice f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPricePk.code =:code AND  f.payUnitPricePk.cid =:cid ";
    private static final String SELECT_BY_KEY_STRING_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPricePk.cid =:cid ";


    @Override
    public List<PayrollUnitPrice> getAllPayrollUnitPriceByCID(String cid) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING_BY_CID, QpbmtPayUnitPrice.class)
                .setParameter("cid", cid)
                .getList(item ->item.toDomain());
    }

    @Override
    public Optional<PayrollUnitPrice> getPayrollUnitPriceById(String code, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPrice.class)
        .setParameter("code", code)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(PayrollUnitPrice domain){

        this.commandProxy().insert(QpbmtPayUnitPrice.toEntity(domain));
    }

    @Override
    public void update(PayrollUnitPrice domain){

        this.commandProxy().update(QpbmtPayUnitPrice.toEntity(domain));
    }

    @Override
    public void remove(String code, String cid){
        this.commandProxy().remove(QpbmtPayUnitPrice.class, new QpbmtPayUnitPricePk(code, cid));
    }
}
