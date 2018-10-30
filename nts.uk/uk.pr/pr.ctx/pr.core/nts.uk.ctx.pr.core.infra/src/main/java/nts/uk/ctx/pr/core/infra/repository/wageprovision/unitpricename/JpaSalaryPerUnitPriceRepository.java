package nts.uk.ctx.pr.core.infra.repository.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPrice;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPricePk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaSalaryPerUnitPriceRepository extends JpaRepository implements SalaryPerUnitPriceRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPerUnitPrice f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.perUnitPricePk.cid =:cid AND  f.perUnitPricePk.code =:code ";
    private static final String SELECT_ALL_IN_COMPANY = SELECT_ALL_QUERY_STRING + " WHERE  f.perUnitPricePk.cid =:cid ORDER BY f.perUnitPricePk.code";

    @Override
    public List<SalaryPerUnitPrice> getAllSalaryPerUnitPrice(){
        String cid = AppContexts.user().companyId();
        return this.queryProxy().query(SELECT_ALL_IN_COMPANY, QpbmtPerUnitPrice.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalaryPerUnitPrice> getSalaryPerUnitPriceById(String cid, String code){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPerUnitPrice.class)
        .setParameter("cid", cid)
        .setParameter("code", code)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalaryPerUnitPrice domain){
        this.commandProxy().insert(new QpbmtPerUnitPrice(domain));
    }

    @Override
    public void update(SalaryPerUnitPrice domain){
        this.commandProxy().update(new QpbmtPerUnitPrice(domain));
    }

    @Override
    public void remove(String cid, String code){
        this.commandProxy().remove(QpbmtPerUnitPrice.class, new QpbmtPerUnitPricePk(cid, code));
    }
}
