package nts.uk.file.pr.infra.core.socialinsurance.healthinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.healthinsurance.HealthInsuranceRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaHealthInsuranceExRepository extends JpaRepository implements HealthInsuranceRepository {


    @Override
    public List<Object[]> getHeathyInsuranceMonth(String cid, int startDate){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
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
        sql.append("    AND m.START_YEAR_MONTH <= ?startDate");
        sql.append("    AND m.END_YEAR_MONTH >= ?startDate");
        sql.append("    ORDER BY CODE");

        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }

    @Override
    public List<Object[]> getBonusHeathyInsurance(String cid, int startDate) {
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("    SELECT ");
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
        sql.append("    INNER JOIN QPBMT_BOUNUS_HEALTH_INS i ON o.CODE = i.SOCIAL_INSURANCE_OFFICE_CD AND i.CID = o.CID");
        sql.append("    WHERE o.CID = ?cid");
        sql.append("    AND START_YEAR_MONTH <= ?startDate ");
        sql.append("    AND END_YEAR_MONTH >= ?startDate");
        sql.append("    ORDER BY CODE");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }
}
