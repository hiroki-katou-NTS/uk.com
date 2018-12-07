package nts.uk.file.com.infra.grantadminrole;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.grantadminrole.GrantAdminRoleColumn;
import nts.uk.file.com.app.grantadminrole.GrantAdminRoleRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
public class GrantAdminRoleImpl extends JpaRepository implements GrantAdminRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_EXPORT_EXCEL = "SELECT u.LOGIN_ID, p.BUSINESS_NAME, g.STR_D, g.END_D FROM SACMT_ROLE_INDIVI_GRANT g " +
            " INNER JOIN SACMT_USER u ON g.USER_ID = u.USER_ID " +
            " INNER JOIN BPSMT_PERSON p ON p.PID = u.ASSO_PID WHERE CID = ? AND ROLE_TYPE = ? ORDER BY LOGIN_ID";

    private static final String GET_EXPORT_EXCEL_COMPANY_MANAGER = " SELECT " +
            " CASE WHEN tb.ROW_NUMBER = 1 THEN tb.CID" +
            " ELSE NULL" +
            " END CID," +
            " CASE WHEN tb.ROW_NUMBER = 1 THEN tb.NAME" +
            " ELSE NULL" +
            " END COMPANYNAME," +
            " tb.LOGIN_ID," +
            " tb.BUSINESS_NAME," +
            " tb.STR_D, tb.END_D" +
            " FROM" +
            " (SELECT " +
            " ROW_NUMBER() OVER(PARTITION BY c.CID ORDER BY c.CID, u.LOGIN_ID) AS ROW_NUMBER,c.CID ,c.NAME, u.LOGIN_ID, p.BUSINESS_NAME,g.ROLE_TYPE, g.STR_D, g.END_D " +
            " FROM BCMMT_COMPANY c LEFT JOIN SACMT_ROLE_INDIVI_GRANT g ON c.CID = g.CID " +
            " LEFT JOIN SACMT_USER u ON g.USER_ID = u.USER_ID " +
            " LEFT JOIN BPSMT_PERSON p ON p.PID = u.ASSO_PID WHERE g.ROLE_TYPE = ?) tb ORDER BY tb.CID";

    @Override
    public List<MasterData> getDataExport(String companyId, int roleType) {

        List<MasterData> datas = new ArrayList<>();
        Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL)
                .setParameter(1,companyId)
                .setParameter(2,roleType);

        List<Object[]> data = query.getResultList();


        for(Object[] objects : data){
            datas.add(new MasterData(this.dataContent(objects),null,""));
        }
        return datas;
    }

    @Override
    public List<MasterData> getDataExportCompanyManagerMode(int roleType) {
        List<MasterData> datas = new ArrayList<>();

        Query query = entityManager.createNativeQuery(GET_EXPORT_EXCEL_COMPANY_MANAGER).setParameter(1,roleType);

        List<Object[]> data = query.getResultList();

        for (Object[] objects : data){
            datas.add(new MasterData(this.dataContentCompanyManagerMode(objects),null,""));
        }

        return datas;
    }

    private Map<String, Object> dataContentCompanyManagerMode(Object[] object){
        Map<String, Object> data = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        data.put(GrantAdminRoleColumn.CAS012_41, object[0] == null ? null : object[0].toString());
        data.put(GrantAdminRoleColumn.CAS012_42, object[1] == null ? null : object[1].toString());
        data.put(GrantAdminRoleColumn.CAS012_37, object[2] == null ? null : object[2].toString());
        data.put(GrantAdminRoleColumn.CAS012_38, object[3] == null ? null : object[3].toString());
        data.put(GrantAdminRoleColumn.CAS012_39, object[4] == null ? null : df.format(object[4]).toString());
        data.put(GrantAdminRoleColumn.CAS012_40, object[5] == null ? null : df.format(object[5]).toString());
        return data;
    }

    private Map<String, Object> dataContent(Object[] object){
        Map<String, Object> data = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        data.put(GrantAdminRoleColumn.CAS012_37, object[0] == null ? null : object[0].toString());
        data.put(GrantAdminRoleColumn.CAS012_38, object[1] == null ? null : object[1].toString());
        data.put(GrantAdminRoleColumn.CAS012_39, object[2] == null ? null : df.format(object[2]).toString());
        data.put(GrantAdminRoleColumn.CAS012_40, object[3] == null ? null : df.format(object[3]).toString());
        return data;
    }
}
