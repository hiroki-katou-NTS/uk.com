package nts.uk.ctx.pr.yearend.infra.yearendadjustment.repository.insurancecompany.lifeInsurance;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.lifeInsurance.QetmtLifeInsurance;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.lifeInsurance.QetmtLifeInsurancePk;

@Stateless
public class JpaLifeInsuranceRepository extends JpaRepository implements LifeInsuranceRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QetmtLifeInsurance f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.lifeInsurancePk.cid =:cid AND  f.lifeInsurancePk.lifeInsuranceCode =:lifeInsuranceCode ";

    @Override
    public List<LifeInsurance> getAllLifeInsurance(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QetmtLifeInsurance.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<LifeInsurance> getLifeInsuranceById(String cid, String lifeInsuranceCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QetmtLifeInsurance.class)
        .setParameter("cid", cid)
        .setParameter("lifeInsuranceCode", lifeInsuranceCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(LifeInsurance domain){
        this.commandProxy().insert(QetmtLifeInsurance.toEntity(domain));
    }

    @Override
    public void update(LifeInsurance domain){
        this.commandProxy().update(QetmtLifeInsurance.toEntity(domain));
    }

    @Override
    public void remove(String cid, String lifeInsuranceCode){
        this.commandProxy().remove(QetmtLifeInsurance.class, new QetmtLifeInsurancePk(cid, lifeInsuranceCode));
    }
}
