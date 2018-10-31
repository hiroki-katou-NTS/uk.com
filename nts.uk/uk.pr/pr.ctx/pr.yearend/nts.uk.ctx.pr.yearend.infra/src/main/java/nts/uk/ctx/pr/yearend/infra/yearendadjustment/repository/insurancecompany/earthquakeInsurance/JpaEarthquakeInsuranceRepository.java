package nts.uk.ctx.pr.yearend.infra.yearendadjustment.repository.insurancecompany.earthquakeInsurance;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsuranceRepository;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.earthquakeInsurance.QetmtEarthQuakeInsu;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.earthquakeInsurance.QetmtEarthQuakeInsuPk;

@Stateless
public class JpaEarthquakeInsuranceRepository extends JpaRepository implements EarthquakeInsuranceRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QetmtEarthQuakeInsu f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.earthQuakeInsuPk.cid =:cid AND  f.earthQuakeInsuPk.earthquakeCode =:earthquakeCode ";

    @Override
    public List<EarthquakeInsurance> getAllEarthquakeInsurance(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QetmtEarthQuakeInsu.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EarthquakeInsurance> getEarthquakeInsuranceById(String cid, String earthquakeCode){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QetmtEarthQuakeInsu.class)
        .setParameter("cid", cid)
        .setParameter("earthquakeCode", earthquakeCode)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(EarthquakeInsurance domain){
        this.commandProxy().insert(QetmtEarthQuakeInsu.toEntity(domain));
    }

    @Override
    public void update(EarthquakeInsurance domain){
        this.commandProxy().update(QetmtEarthQuakeInsu.toEntity(domain));
    }

    @Override
    public void remove(String cid, String earthquakeCode){
        this.commandProxy().remove(QetmtEarthQuakeInsu.class, new QetmtEarthQuakeInsuPk(cid, earthquakeCode));
    }
}
