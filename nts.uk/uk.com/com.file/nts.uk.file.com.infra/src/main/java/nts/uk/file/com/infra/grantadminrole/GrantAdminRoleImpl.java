package nts.uk.file.com.infra.grantadminrole;

import nts.uk.file.com.app.grantadminrole.GrantAdminRoleColumn;
import nts.uk.file.com.app.grantadminrole.GrantAdminRoleRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class GrantAdminRoleImpl implements GrantAdminRoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_EXPORT_EXCEL = "SELECT u.LOGIN_ID, p.BUSINESS_NAME, g.STR_D, g.END_D FROM SACMT_ROLE_INDIVI_GRANT g " +
            "JOIN SACMT_USER u ON g.USER_ID = u.USER_ID " +
            "LEFT JOIN BPSMT_PERSON p ON p.PID = u.ASSO_PID WHERE CID = ? AND ROLE_TYPE = ? ORDER BY LOGIN_ID";
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

    private Map<String, Object> dataContent(Object[] object){
        Map<String, Object> data = new HashMap<>();
        data.put(GrantAdminRoleColumn.CAS012_43, object[0] == null ? null :  object[0].toString());
        data.put(GrantAdminRoleColumn.CAS012_44, object[1] == null ? null : object[1].toString());
        data.put(GrantAdminRoleColumn.CAS012_45, object[2] == null ? null : object[2].toString().substring(0,10));
        data.put(GrantAdminRoleColumn.CAS012_46, object[3] == null ? null : object[3].toString().substring(0,10));
        return data;
    }
}
