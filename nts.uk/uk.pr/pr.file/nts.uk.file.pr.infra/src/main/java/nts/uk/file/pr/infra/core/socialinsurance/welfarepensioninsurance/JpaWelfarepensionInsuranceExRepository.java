package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaWelfarepensionInsuranceExRepository extends JpaRepository implements SocialInsuranceRepository {


    @Override
    public List<Object[]> getHeathyInsurance(String cid){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
       /* sql.append("    SELECT ");
        sql.append("           CODE,");
        sql.append("           NAME,");
        sql.append("           INDIVIDUAL_HEALTH_INSURANCE_RATE,");
        sql.append("           INDIVIDUAL_LONG_CARE_INSURANCE_RATE,");
        sql.append("           INDIVIDUAL_SPECIAL_INSURANCE_RATE,");
        sql.append("           INDIVIDUAL_BASIC_INSURANCE_RATE,");
        sql.append("           INDIVIDUAL_FRACTION_CLS,");
        sql.append("           EMPLOYEE_HEALTH_INSURANCE_RATE,");
        sql.append("           EMPLOYEE_LONG_CARE_INSURANCE_RATE,");
        sql.append("           EMPLOYEE_SPECIAL_INSURANCE_RATE,");
        sql.append("           EMPLOYEE_BASIC_INSURANCE_RATE,");
        sql.append("           EMPLOYEE_FRACTION_CLS");
        sql.append("    FROM QPBMT_SOCIAL_INS_OFFICE o");
        sql.append("    INNER JOIN QPBMT_HEALTH_INS_MON_FEE m ON o.CODE = m.SOCIAL_INSURANCE_OFFICE_CD AND o.CID = m.CID");
        sql.append("    WHERE o.CID = ?cid");
        sql.append("    ORDER BY CODE");*/

        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }


    @Override
    public List<Object[]> getWelfarepensionInsurance(String cid) {
        return Collections.emptyList();
    }

    @Override
    public List<Object[]> getContributionRate(String cid) {
        return Collections.emptyList();
    }

    @Override
    public List<Object[]> getSocialInsuranceoffice(String cid) {
        return Collections.emptyList();
    }

    @Override
    public List<Object[]> getSalaryHealth(String cid) {
        return Collections.emptyList();
    }
}
