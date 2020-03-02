package nts.uk.ctx.pr.yearend.infra.yearendadjustment.repository.insurancecompany.lifeInsurance;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.earthquakeInsurance.EarthquakeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsurance;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.lifeInsurance.LifeInsuranceRepository;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.earthquakeInsurance.QetmtEarthQuakeInsu;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.insuranceType.QetmtInsuranceType;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.lifeInsurance.QetmtLifeInsurance;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.lifeInsurance.QetmtLifeInsurancePk;

@Stateless
public class JpaLifeInsuranceRepository extends JpaRepository implements LifeInsuranceRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QetmtLifeInsurance f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.lifeInsurancePk.cid =:cid AND  f.lifeInsurancePk.lifeInsuranceCode =:lifeInsuranceCode ";
    private static final String SELECT_BY_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.lifeInsurancePk.cid =:cid";
    private static final String DELETE_BY_LIFEINSURANCECODE = "DELETE  FROM QetmtInsuranceType f WHERE  f.insuranceTypePk.cid =:cid AND  f.insuranceTypePk.lifeInsuranceCode =:lifeInsuranceCode";
    private static final String SELECT_EARTHQUAKE_BY_KEY_STRING = "SELECT f FROM QetmtEarthQuakeInsu f WHERE  f.earthQuakeInsuPk.cid =:cid AND  f.earthQuakeInsuPk.earthquakeCode IN :lifeInsuranceCode ";
    private static final String SELECT_BY_LIFEINSURANCE_CODE = SELECT_ALL_QUERY_STRING + " WHERE  f.lifeInsurancePk.cid =:cid AND  f.lifeInsurancePk.lifeInsuranceCode IN :lstEarthquakeInsuranceCode ";
    @Override
    public List<LifeInsurance> getAllLifeInsurance() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QetmtLifeInsurance.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<LifeInsurance> getLifeInsuranceBycId(String cid) {
        return this.queryProxy().query(SELECT_BY_CID, QetmtLifeInsurance.class)
                .setParameter("cid", cid)
                .getList(c -> c.toDomain());
    }

    @Override
    public Optional<LifeInsurance> getLifeInsuranceById(String cid, String lifeInsuranceCode) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QetmtLifeInsurance.class)
                .setParameter("cid", cid)
                .setParameter("lifeInsuranceCode", lifeInsuranceCode)
                .getSingle(c -> c.toDomain());
    }

    @Override
    public void add(LifeInsurance domain) {
        this.commandProxy().insert(QetmtLifeInsurance.toEntity(domain));
    }

    @Override
    public void update(LifeInsurance domain) {
        this.commandProxy().update(QetmtLifeInsurance.toEntity(domain));
    }

    @Override
    public void remove(String cid, String lifeInsuranceCode) {
        this.commandProxy().remove(QetmtLifeInsurance.class, new QetmtLifeInsurancePk(cid, lifeInsuranceCode));
    }

    @Override
    public void removeLifeInsurance(String cid, String lifeInsuranceCode) {
        this.getEntityManager().createQuery(DELETE_BY_LIFEINSURANCECODE, QetmtInsuranceType.class)
                .setParameter("cid", cid)
                .setParameter("lifeInsuranceCode", lifeInsuranceCode).executeUpdate();
    }

    @Override
    public void copyAddEarthQuakeInsu(List<LifeInsurance> lstLifeInsurance) {
        List<QetmtEarthQuakeInsu> data = lstLifeInsurance.stream().map(i -> QetmtEarthQuakeInsu.toEntityInsurance(i)).collect(Collectors.toList());
        this.commandProxy().insertAll(data);
    }

    @Override
    public void updarteAddEarthQuakeInsu(List<LifeInsurance> lstLifeInsurance) {
        List<QetmtEarthQuakeInsu> data = lstLifeInsurance.stream().map(i -> QetmtEarthQuakeInsu.toEntityInsurance(i)).collect(Collectors.toList());
        this.commandProxy().updateAll(data);
    }

    @Override
    public List<EarthquakeInsurance> getEarthquakeByLstLifeInsuranceCode(String cid, List lifeInsuranceCode) {
        return this.queryProxy().query(SELECT_EARTHQUAKE_BY_KEY_STRING, QetmtEarthQuakeInsu.class)
                .setParameter("cid", cid)
                .setParameter("lifeInsuranceCode", lifeInsuranceCode).getList(item -> item.toDomain());
    }

    @Override
    public List<LifeInsurance> getLifeInsurancedByLstEarthquakeInsuranceCode(String cid, List lstEarthquakeInsuranceCode) {
        return this.queryProxy().query(SELECT_BY_LIFEINSURANCE_CODE, QetmtLifeInsurance.class)
                .setParameter("cid", cid)
                .setParameter("lstEarthquakeInsuranceCode", lstEarthquakeInsuranceCode)
                .getList(c -> c.toDomain());
    }

}
