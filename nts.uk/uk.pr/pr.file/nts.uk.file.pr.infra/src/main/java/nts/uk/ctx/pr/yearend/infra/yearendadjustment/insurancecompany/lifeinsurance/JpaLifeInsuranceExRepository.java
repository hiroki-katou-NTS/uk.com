package nts.uk.ctx.pr.yearend.infra.yearendadjustment.insurancecompany.lifeinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.yearend.app.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceExRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaLifeInsuranceExRepository extends JpaRepository implements LifeInsuranceExRepository {


    @Override
    public List<Object[]> getLifeInsurances(String cid){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
        sql.append("        l.INSURANCE_LIFE_CD,");
        sql.append("        INSURANCE_LIFE_NAME,");
        sql.append("        INSURANCE_TYPE_ATR,");
        sql.append("        INSURANCE_TYPE_CD,");
        sql.append("        INSURANCE_TYPE_NAME");
        sql.append("    FROM QETMT_INSURANCE_LIFE l LEFT JOIN QETMT_INSURANCE_TYPE t ");
        sql.append("    ON l.INSURANCE_LIFE_CD = t.INSURANCE_LIFE_CD AND t.CID = l.CID");
        sql.append("    WHERE l.CID = ?cid");
        sql.append("    ORDER BY l.INSURANCE_LIFE_CD, INSURANCE_TYPE_ATR, INSURANCE_TYPE_CD");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }
}
