package nts.uk.file.com.infra.role.personalinfo;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.role.personalinfo.RolePersonalInforRepository;
import nts.uk.file.com.infra.role.CommonRole;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class JpaRolePersonalInfor extends JpaRepository implements RolePersonalInforRepository {
  
    private Map<Integer, String> functionNo;
    @Override
    public List<MasterData> findAllRolePersonalInfor(int roleType, String cId) {
        List<MasterData> datas = new ArrayList<>();
        functionNo = findAllFunctionNo();
        if(functionNo.isEmpty()){
            return new ArrayList<MasterData>();
        }
        List<Integer> listFunctionNo = functionNo.keySet().stream().collect(Collectors.toList());
        String functionNo = CommonRole.getQueryFunctionNo(listFunctionNo);
        String GET_EXPORT_EXCEL = "SELECT ROLE_CD ,ROLE_NAME,ASSIGN_ATR,REF_RANGE ,REFER_FUTURE_DATE, "
                + functionNo +
                " FROM (" +
                "SELECT wm.ROLE_CD,wm.ROLE_NAME, wm.ASSIGN_ATR, wm.REF_RANGE ,wi.REFER_FUTURE_DATE, IS_AVAILABLE, wkf.FUNCTION_NO " +
                "FROM (SELECT r.ROLE_ID , r.ROLE_CD,r.ROLE_TYPE, r.REF_RANGE ,pr.REFER_FUTURE_DATE, " +
                " CASE WHEN a.IS_AVAILABLE IS NULL THEN f.DEFAULT_VALUE " +
                " ELSE a.IS_AVAILABLE " +
                " END IS_AVAILABLE, f.FUNCTION_NO " +
                " FROM (Select * FROM SACMT_ROLE wm1 WHERE wm1.CID = ?1 AND wm1.ROLE_TYPE = ?2 ) As  r" +
                " LEFT JOIN SACMT_PERSON_ROLE pr ON r.ROLE_ID = pr.ROLE_ID " +
                " LEFT JOIN PPEMT_PER_INFO_FUNCTION f on f.FUNCTION_NO = f.FUNCTION_NO " +
                " LEFT JOIN PPEMT_PER_INFO_AUTH a ON  r.CID = a.CID AND r.ROLE_ID = a.ROLE_ID AND f.FUNCTION_NO = a.FUNCTION_NO " +
                "AS sourceTable PIVOT (" +
                "    MAX(IS_AVAILABLE)" +
                "    FOR [FUNCTION_NO] IN (" +
                functionNo +
                ") ) AS pvt ";

        Query query =  this.getEntityManager().createNativeQuery(GET_EXPORT_EXCEL)
                .setParameter(1, cId)
                .setParameter(2, roleType);
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            datas.add(new MasterData(dataContent(objects,listFunctionNo), null, ""));
        }
        return datas;
    }



    private Map<String, Object> dataContent(Object[] objects,List<Integer> listFunctionNo) {
        Map<String, Object> data = new HashMap<>();
        data.put(CommonRole.CAS009_23, objects[0]);
        data.put(CommonRole.CAS009_24, objects[1]);
        data.put(CommonRole.CAS009_25, CommonRole.getTextRoleAtr(objects[2].toString()));
        data.put(CommonRole.CAS009_26, CommonRole.getTextEnumEmplReferRange(Integer.valueOf(objects[3].toString())));
        data.put(CommonRole.CAS009_27, objects[4].toString().equals("1")?I18NText.getText("CAS009_18"):I18NText.getText("CAS009_19"));
        for (int i = 0 ; i < listFunctionNo.size() ; i++){
            data.put(CommonRole.FUNCTION_NO_+listFunctionNo.get(i) ,objects[i+5].toString().equals("1")? "○" : "ー");
        }
        return data;
    }
    @Override
    public Map<Integer, String> findAllFunctionNo() {
        Map<Integer, String> resulf = new HashMap<>();
        Query query =  this.getEntityManager().createNativeQuery(CommonRole.GET_FUNCTION_NO_CAS009);
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            resulf.put(Integer.valueOf(objects[0].toString()),objects[1].toString());
        }
        return resulf;
    }

}
