package nts.uk.ctx.pr.core.infra.repository.wageprovision.unitpricename;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPriceSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.unitpricename.QpbmtPerUnitPriceSetPk;

@Stateless
public class JpaSalaryPerUnitPriceSettingRepository extends JpaRepository implements SalaryPerUnitPriceSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPerUnitPriceSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.perUnitPriceSetPk.cid =:cid AND  f.perUnitPriceSetPk.code =:code ";

    @Override
    public List<SalaryPerUnitPriceSetting> getAllSalaryPerUnitPriceSetting(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPerUnitPriceSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<SalaryPerUnitPriceSetting> getSalaryPerUnitPriceSettingById(String cid, String code){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPerUnitPriceSet.class)
        .setParameter("cid", cid)
        .setParameter("code", code)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(SalaryPerUnitPriceSetting domain){
        this.commandProxy().insert(QpbmtPerUnitPriceSet.toEntity(domain));
    }

    @Override
    public void update(SalaryPerUnitPriceSetting domain){
        this.commandProxy().update(QpbmtPerUnitPriceSet.toEntity(domain));
    }

    @Override
    public void remove(String cid, String code){
        this.commandProxy().remove(QpbmtPerUnitPriceSet.class, new QpbmtPerUnitPriceSetPk(cid, code));
    }
}
