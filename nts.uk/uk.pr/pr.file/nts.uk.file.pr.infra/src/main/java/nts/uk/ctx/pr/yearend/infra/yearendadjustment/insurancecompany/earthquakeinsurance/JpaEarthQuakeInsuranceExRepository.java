package nts.uk.ctx.pr.yearend.infra.yearendadjustment.insurancecompany.earthquakeinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.earthquakeinsurance.EarthQuakeInsuranceExRepository;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaEarthQuakeInsuranceExRepository extends JpaRepository implements EarthQuakeInsuranceExRepository {


    @Override
    public List<Object[]> getEarthQuakeInsurances(String cid){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append("        INSURANCE_EARTHQ_CD,");
        sql.append("        INSURANCE_EARTHQ_NAME");
        sql.append("    FROM QETMT_INSURANCE_EARTHQ ");
        sql.append("    WHERE CID = ?cid");
        sql.append("    ORDER BY INSURANCE_EARTHQ_CD");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }
}
