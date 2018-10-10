package nts.uk.ctx.pr.core.infra.repository.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceNameRepository;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPriceName;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPriceNamePk;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaSalaryPerUnitPriceNameRepository extends JpaRepository implements SalaryPerUnitPriceNameRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPerUnitPriceName f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.perUnitPriceNamePk.cid =:cid AND  f.perUnitPriceNamePk.code =:code ";
    private static final String SELECT_ALL_IN_COMPANY = SELECT_ALL_QUERY_STRING + " WHERE  f.perUnitPriceNamePk.cid =:cid ORDER BY f.perUnitPriceNamePk.code";

    @Override
    public List<SalaryPerUnitPriceName> getAllSalaryPerUnitPriceName(){
        String cid = AppContexts.user().companyId();
        return this.queryProxy().query(SELECT_ALL_IN_COMPANY, QpbmtPerUnitPriceName.class)
                .setParameter("cid", cid)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalaryPerUnitPriceName> getSalaryPerUnitPriceNameById(String cid, String code){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPerUnitPriceName.class)
        .setParameter("cid", cid)
        .setParameter("code", code)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalaryPerUnitPriceName domain){
        this.commandProxy().insert(QpbmtPerUnitPriceName.toEntity(domain));
    }

    @Override
    public void update(SalaryPerUnitPriceName domain){
        this.commandProxy().update(QpbmtPerUnitPriceName.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code){
        this.commandProxy().remove(QpbmtPerUnitPriceName.class, new QpbmtPerUnitPriceNamePk(cid, code));
    }
}
