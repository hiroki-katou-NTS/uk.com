package nts.uk.file.pr.infra.core.socialinsurance.welfarepensioninsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.welfarepensioninsurance.WelfarepensionInsuranceRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaWelfarepensionInsuranceExRepository extends JpaRepository implements WelfarepensionInsuranceRepository {

    @Override
    public List<Object[]> getWelfarepensionInsuranceEmp(String cid, int startDate) {
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append("   CODE,");
        sql.append("   NAME,");
        sql.append("   FUND_CLASSIFICATION,");
        sql.append("   MALE_INDIVIDUAL_BURDEN_RATIO,");
        sql.append("   [MALE_INDIVIDUAL_BURDEN_RATIO] - [MALE_INDIVIDUAL_EXEMPTION_RATE] AS MALE_RATE1,");
        sql.append("   MALE_INDIVIDUAL_EXEMPTION_RATE,");
        sql.append("   FEMALE_INDIVIDUAL_BURDEN_RATIO,");
        sql.append("   [FEMALE_INDIVIDUAL_BURDEN_RATIO] - [FEMALE_INDIVIDUAL_EXEMPTION_RATE] AS FEMALE_RATE,");
        sql.append("   FEMALE_INDIVIDUAL_EXEMPTION_RATE,");
        sql.append("   PERSONAL_FRACTION,");
        sql.append("   MALE_EMPLOYEE_CONTRIBUTION_RATIO,");
        sql.append("   [MALE_EMPLOYEE_CONTRIBUTION_RATIO] - [MALE_EMPLOYER_EXEMPTION_RATE] AS MALE_CONTRIBUTION_RATE,");
        sql.append("   MALE_EMPLOYER_EXEMPTION_RATE,");
        sql.append("   FEMALE_EMPLOYEE_CONTRIBUTION_RATIO,");
        sql.append("   [FEMALE_EMPLOYEE_CONTRIBUTION_RATIO] - [FEMALE_EMPLOYER_EXEMPTION_RATE] AS FEMALE_CONTRIBUTION_RATE,");
        sql.append("   FEMALE_EMPLOYER_EXEMPTION_RATE,");
        sql.append("   BUSINESS_OWNER_FRACTION");
        sql.append(" FROM QPBMT_SOCIAL_INS_OFFICE s");
        sql.append(" INNER JOIN (SELECT * ");
        sql.append("      FROM QPBMT_EMP_PEN_MON_INS_FEE ");
        sql.append("      WHERE START_YEAR_MONTH <= ?startDate AND END_YEAR_MONTH >= ?startDate");
        sql.append("      ) e ");
        sql.append(" ON s.CODE = e.SOCIAL_INSURANCE_OFFICE_CD AND e.CID = s.CID");
        sql.append(" INNER JOIN (SELECT * ");
        sql.append("      FROM QPBMT_WELFARE_PEN_INS_CLS ");
        sql.append("      WHERE START_YEAR_MONTH <= ?startDate AND END_YEAR_MONTH >= ?startDate");
        sql.append("      )c ON c.SOCIAL_INSURANCE_OFFICE_CD = s.CODE AND c.CID = e.CID");
        sql.append(" WHERE s.CID = ?cid ");
        sql.append(" ORDER BY CODE");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }

    @Override
    public List<Object[]> getWelfarepensionInsuranceBonus(String cid, int startDate) {
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("  SELECT ");
        sql.append("           CODE,");
        sql.append("           NAME,");
        sql.append("           FUND_CLASSIFICATION,");
        sql.append("           MALE_INDIVIDUAL_BURDEN_RATIO,");
        sql.append("           [MALE_INDIVIDUAL_BURDEN_RATIO] - [MALE_INDIVIDUAL_EXEMPTION_RATE] AS MALE_RATE1,");
        sql.append("           MALE_INDIVIDUAL_EXEMPTION_RATE,");
        sql.append("           FEMALE_INDIVIDUAL_BURDEN_RATIO,");
        sql.append("           [FEMALE_INDIVIDUAL_BURDEN_RATIO] - [FEMALE_INDIVIDUAL_EXEMPTION_RATE] AS FEMALE_RATE,");
        sql.append("           FEMALE_INDIVIDUAL_EXEMPTION_RATE,");
        sql.append("           PERSONAL_FRACTION,");
        sql.append("           MALE_EMPLOYEE_CONTRIBUTION_RATIO,");
        sql.append("           [MALE_EMPLOYEE_CONTRIBUTION_RATIO] - [MALE_EMPLOYER_EXEMPTION_RATE] AS MALE_CONTRIBUTION_RATE,");
        sql.append("           MALE_EMPLOYER_EXEMPTION_RATE,");
        sql.append("           FEMALE_EMPLOYEE_CONTRIBUTION_RATIO,");
        sql.append("           [FEMALE_EMPLOYEE_CONTRIBUTION_RATIO] - [FEMALE_EMPLOYER_EXEMPTION_RATE] AS FEMALE_CONTRIBUTION_RATE,");
        sql.append("           FEMALE_EMPLOYER_EXEMPTION_RATE,");
        sql.append("           BUSINESS_OWNER_FRACTION,");
        sql.append("           CHILD_CARE_CONTRIBUTION_RATIO");
        sql.append("         FROM QPBMT_SOCIAL_INS_OFFICE s");
        sql.append("         INNER JOIN (SELECT * ");
        sql.append("              FROM QPBMT_BONUS_EMP_PEN_RATE ");
        sql.append("              WHERE START_YEAR_MONTH <= ?startDate AND END_YEAR_MONTH >= ?startDate");
        sql.append("              ) e ");
        sql.append("         ON s.CODE = e.SOCIAL_INSURANCE_OFFICE_CD AND e.CID = s.CID");
        sql.append("         INNER JOIN (SELECT * ");
        sql.append("              FROM QPBMT_WELFARE_PEN_INS_CLS ");
        sql.append("              WHERE START_YEAR_MONTH <= ?startDate AND END_YEAR_MONTH >= ?startDate");
        sql.append("              )c ON c.SOCIAL_INSURANCE_OFFICE_CD = s.CODE AND c.CID = e.CID");
        sql.append("    LEFT JOIN (SELECT * ");
        sql.append("         FROM QPBMT_CONTRIBUTION_RATE");
        sql.append("         WHERE CID = ?cid");
        sql.append("         AND START_YEAR_MONTH <= ?startDate AND END_YEAR_MONTH >= ?startDate ");
        sql.append("         ) r ON r.CID = s.CID AND s.CODE = r.SOCIAL_INSURANCE_OFFICE_CD");
        sql.append("         WHERE s.CID = ?cid");
        sql.append("         ORDER BY CODE");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid).setParameter("startDate", startDate)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;
    }

}
