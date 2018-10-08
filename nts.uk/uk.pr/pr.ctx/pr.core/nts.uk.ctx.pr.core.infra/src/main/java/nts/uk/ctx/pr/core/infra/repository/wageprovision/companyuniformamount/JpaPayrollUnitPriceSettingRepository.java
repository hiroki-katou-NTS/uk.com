package nts.uk.ctx.pr.core.infra.repository.wageprovision.companyuniformamount;


import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceSettingRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPriSet;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.companyuniformamount.QpbmtPayUnitPriSetPk;

import java.util.List;
import java.util.Optional;

@Stateless
public class JpaPayrollUnitPriceSettingRepository extends JpaRepository implements PayrollUnitPriceSettingRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtPayUnitPriSet f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.payUnitPriSetPk.hisId =:hisId ";

    @Override
    public List<PayrollUnitPriceSetting> getAllpayrollUnitPriceSetting(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtPayUnitPriSet.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<PayrollUnitPriceSetting> getpayrollUnitPriceSettingById(String hisId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtPayUnitPriSet.class)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(PayrollUnitPriceSetting domain){
        this.commandProxy().insert(QpbmtPayUnitPriSet.toEntity(domain));
    }

    @Override
    public void update(PayrollUnitPriceSetting domain){
        this.commandProxy().update(QpbmtPayUnitPriSet.toEntity(domain));
    }

    @Override
    public void remove(String hisId){
        this.commandProxy().remove(QpbmtPayUnitPriSet.class, new QpbmtPayUnitPriSetPk(hisId));
    }
}
