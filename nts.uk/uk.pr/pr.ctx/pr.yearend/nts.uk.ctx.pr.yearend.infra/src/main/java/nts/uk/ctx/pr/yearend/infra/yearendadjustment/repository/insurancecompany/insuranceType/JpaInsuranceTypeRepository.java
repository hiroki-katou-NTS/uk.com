package nts.uk.ctx.pr.yearend.infra.yearendadjustment.repository.insurancecompany.insuranceType;

import java.util.Optional;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceType;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceTypeRepository;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.insuranceType.QetmtInsuranceType;
import nts.uk.ctx.pr.yearend.infra.yearendadjustment.entity.insurancecompany.insuranceType.QetmtInsuranceTypePk;

@Stateless
public class JpaInsuranceTypeRepository extends JpaRepository implements InsuranceTypeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QetmtInsuranceType f";
    private static final String SELECT_BY_LIFEINSURANCECODE = SELECT_ALL_QUERY_STRING + " WHERE  f.insuranceTypePk.cid =:cid AND  f.insuranceTypePk.lifeInsuranceCode =:lifeInsuranceCode";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.insuranceTypePk.cid =:cid AND  f.insuranceTypePk.lifeInsuranceCode =:lifeInsuranceCode AND f.insuranceTypePk.insuranceTypeCode =:insuranceTypeCode";


    @Override
    public List<InsuranceType> getAllInsuranceType() {
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QetmtInsuranceType.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public List<InsuranceType> getInsuranceTypeBycId(String cid, String lifeInsuranceCode) {
        return this.queryProxy().query(SELECT_BY_LIFEINSURANCECODE, QetmtInsuranceType.class)
                .setParameter("cid", cid)
                .setParameter("lifeInsuranceCode", lifeInsuranceCode)
                .getList(c -> c.toDomain());
    }

    @Override
    public Optional<InsuranceType> getInsuranceTypeById(String cid, String lifeInsuranceCode, String insuranceTypeCode) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QetmtInsuranceType.class)
                .setParameter("cid", cid)
                .setParameter("lifeInsuranceCode", lifeInsuranceCode)
                .setParameter("insuranceTypeCode", insuranceTypeCode)
                .getSingle(item -> item.toDomain());
    }

    @Override
    public void add(InsuranceType domain) {
        this.commandProxy().insert(QetmtInsuranceType.toEntity(domain));
    }

    @Override
    public void update(InsuranceType domain) {
        this.commandProxy().update(QetmtInsuranceType.toEntity(domain));
    }

    @Override
    public void remove(String cid, String lifeInsuranceCode, String insuranceTypeCode) {
        this.commandProxy().remove(QetmtInsuranceType.class, new QetmtInsuranceTypePk(cid, lifeInsuranceCode, insuranceTypeCode));
    }

}
