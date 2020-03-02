package nts.uk.file.pr.infra.core.laborinsurance.laborinsuranceoffice;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.laborinsurance.laborinsuranceoffice.LaborInsuranceOfficeRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JpaLaborInsuranceOfficeExRepository extends JpaRepository implements LaborInsuranceOfficeRepository {


    @Override
    public List<Object[]> getLaborInsuranceOfficeByCompany(String cid){
        List<Object[]> resultQuery = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    LABOR_OFFICE_CODE,");
        sql.append("    REPRESENTATIVE_NAME,");
        sql.append("    POSTAL_CODE,");
        sql.append("    LABOR_OFFICE_NAME,");
        sql.append("    REPRESENTATIVE_POSITION,");
        sql.append("    PHONE_NUMBER,");
        sql.append("    ADDRESS1,");
        sql.append("    ADDRESS2,");
        sql.append("    ADDRESS_KANA1,");
        sql.append("    ADDRESS_KANA2,");
        sql.append("    CITY_CODE,");
        sql.append("    EMPLOYMENT_OFFFICE_CODE,");
        sql.append("    EMPLOYMENT_OFFICE_NUMBER1,");
        sql.append("    EMPLOYMENT_OFFICE_NUMBER2,");
        sql.append("    EMPLOYMENT_OFFICE_NUMBER3,");
        sql.append("    NOTES ");
        sql.append("FROM ");
        sql.append("    QPBMT_LABOR_INSU_OFFICE ");
        sql.append("WHERE");
        sql.append("    CID = ?cid");
        sql.append("    ORDER BY LABOR_OFFICE_CODE");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(sql.toString()).setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }

}
