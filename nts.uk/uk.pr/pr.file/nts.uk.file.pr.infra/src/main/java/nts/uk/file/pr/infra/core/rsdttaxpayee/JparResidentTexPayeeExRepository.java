package nts.uk.file.pr.infra.core.rsdttaxpayee;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.file.app.core.rsdttaxpayee.ResidentTexPayeeExportRepository;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Stateless
public class JparResidentTexPayeeExRepository  extends JpaRepository implements ResidentTexPayeeExportRepository {

    @Override
    public List<Object[]> getResidentTexPayeeByCompany(String cid) {
        List<Object[]> resultQuery = null;
        StringBuilder exportSQL = new StringBuilder();
        exportSQL.append("SELECT ROOT.RESIDENT_TAX_CD,");
        exportSQL.append("    ROOT.RESIDENT_TAX_NAME,");
        exportSQL.append("    ROOT.RESIDENT_TAX_KNNAME,");
        exportSQL.append("    ROOT.PREFECTURES_NO,");
        exportSQL.append("    ROOT.REPORTS_CD,");
        exportSQL.append("    PAY.RESIDENT_TAX_NAME AS REPORTS_NAME,");
        exportSQL.append("    ROOT.ACCOUNT_NUM,");
        exportSQL.append("    ROOT.SUBCRIBER_NAME,");
        exportSQL.append("    ROOT.DESIGNATION_NUM,");
        exportSQL.append("    ROOT.COMPILE_STATION_ZIP_CD,");
        exportSQL.append("    ROOT.COMPILE_STATION_NAME ");
        exportSQL.append("FROM (SELECT * FROM QBTMT_RSDT_TAX_PAYEE PAYEE WHERE PAYEE.CID = ?cid) AS ROOT ");
        exportSQL.append("LEFT JOIN QBTMT_RSDT_TAX_PAYEE PAY ");
        exportSQL.append("ON ROOT.REPORTS_CD = PAY.RESIDENT_TAX_CD AND ROOT.CID = PAY.CID ");
        exportSQL.append("ORDER BY ROOT.PREFECTURES_NO, ROOT.RESIDENT_TAX_CD ");
        try {
            resultQuery = this.getEntityManager().createNativeQuery(exportSQL.toString()).setParameter("cid", cid)
                    .getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
        return resultQuery;

    }

}
