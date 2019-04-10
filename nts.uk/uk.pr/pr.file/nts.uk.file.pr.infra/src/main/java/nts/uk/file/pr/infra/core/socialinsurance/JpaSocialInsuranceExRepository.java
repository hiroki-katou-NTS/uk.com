package nts.uk.file.pr.infra.core.socialinsurance;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.socialinsurance.SocialInsuranceRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaSocialInsuranceExRepository extends JpaRepository implements SocialInsuranceRepository {


    @Override
    public List<Object[]> getHeathyInsurance(String cid){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("   CODE,");
        sql.append("   ADDRESS_1,");
        sql.append("   NAME,");
        sql.append("   ADDRESS_2,");
        sql.append("   SHORT_NAME,");
        sql.append("   ADDRESS_KANA_1,");
        sql.append("   REPRESENTATIVE_NAME,");
        sql.append("   ADDRESS_KANA_2,");
        sql.append("   REPRESENTATIVE_POSITION,");
        sql.append("   HEALTH_INSURANCE_OFFICE_NUMBER,");
        sql.append("   POSTAL_CODE,");
        sql.append("   HEALTH_INSURANCE_OFFICE_NUMBER_1,");
        sql.append("   WELFARE_PENSION_FUND_NUMBER,");
        sql.append("   HEALTH_INSURANCE_CITY_CODE,");
        sql.append("   HEALTH_INSURANCE_OFFICE_CODE,");
        sql.append("   HEALTH_INSURANCE_OFFICE_NUMBER_2,");
        sql.append("   PHONE_NUMBER,");
        sql.append("   WELFARE_PENSION_OFFICE_NUMBER,");
        sql.append("   WELFARE_PENSION_OFFICE_NUMBER_1,");
        sql.append("   WELFARE_PENSION_PREFECTURE_NO,");
        sql.append("   WELFARE_PENSION_OFFICE_CODE,");
        sql.append("   MEMO ");
        sql.append("FROM QPBMT_SOCIAL_INS_OFFICE o ");
        sql.append("WHERE o.CID = ?cid");
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
