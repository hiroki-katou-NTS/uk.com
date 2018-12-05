package nts.uk.file.com.infra.role.employment;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.com.app.role.employment.RoleEmpExportRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
public class JpaRoleEmploymentExport extends JpaRepository implements RoleEmpExportRepository {

    private static final String CAS005_122 = "コードカラム";
    private static final String CAS005_123 = "名称カラム";
    private static final String CAS005_124 = "担当区分カラム";
    private static final String CAS005_125 = "社員１参照範囲カラム";
    private static final String CAS005_126 = "未来日参照権限カラム";
    private static final String CAS005_127 = "メニュー設定カラム";
    private static final String CAS005_128 = "スケジュール画面社員１参照カラム";
    private static final String FUNCTION_NO_ = "FUNCTION_NO_";
    private static final int ROLE_TYPE_CAS005 = 3;

    private static final String GET_FUNCTION_ON = "SELECT FUNCTION_NO" +
            "FROM KASMT_WORPLACE_FUNCTION";


    @Override
    public List<MasterData> findAllRoleEmployment(int roleType, String cId) {
        List<MasterData> datas = new ArrayList<>();
        List<Integer> listFunctionNo = GenFunctionNo();
        StringBuffer functionNo = new StringBuffer();
        for (int i = 0; i > listFunctionNo.size(); i++) {
            functionNo.append("[");
            functionNo.append(listFunctionNo.get(i));
            if (i == listFunctionNo.size() - 1) {
                functionNo.append("]");
            } else {
                functionNo.append("],");
            }
        }
        String GET_EXPORT_EXCEL = "SELECT ROLE_ID ,ROLE_CD,ROLE_TYPE,REF_RANGE ,REFER_FUTURE_DATE, "
                 +functionNo+ "FROM ( SELECT wm.ROLE_ID , wm.ROLE_CD,wm.ROLE_TYPE, wm.REF_RANGE ,wi.REFER_FUTURE_DATE, IS_AVAILABLE, wkf.FUNCTION_NO" +
                "FROM SACMT_ROLE wm " +
                "LEFT JOIN SACMT_PERSON_ROLE wi ON wm.ROLE_ID = wi.ROLE_ID \n" +
                "INNER JOIN PPEMT_PER_INFO_AUTH kwa ON  wm.CID = kwa.CID AND wm.ROLE_ID = kwa.ROLE_ID\n" +
                "INNER JOIN PPEMT_PER_INFO_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO\n" +
                "WHERE wm.CID = '?1' AND wm.ROLE_TYPE ='?2'" +
                ")" +
                "AS sourceTable PIVOT (" +
                "    MAX(IS_AVAILABLE)\n" +
                "    FOR [FUNCTION_NO] IN (" +
                functionNo +
                ") AS pvt \n";

        Query query = this.getEntityManager().createNativeQuery(GET_EXPORT_EXCEL)
                .setParameter(1, cId)
                .setParameter(2, roleType);
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            datas.add(new MasterData(dataContent(objects, listFunctionNo), null, ""));
        }
        return datas;
    }

    private Map<String, Object> dataContent(Object[] objects, List<Integer> listFunctionNo) {
        Map<String, Object> data = new HashMap<>();
        data.put(CAS005_122, objects[0]);
        data.put(CAS005_123, objects[1]);
        data.put(CAS005_124, objects[2]);
        data.put(CAS005_125, objects[3]);
        data.put(CAS005_126, objects[4]);
        data.put(CAS005_127, objects[5]);
        data.put(CAS005_128, objects[6]);
        listFunctionNo.stream().forEach(x -> {
            data.put(FUNCTION_NO_ + x.toString(), (int) objects[x + 3] == 1 ? "○" : "ー");
        });
        return data;
    }

    public List<Integer> GenFunctionNo() {
        List<Integer> resulf = new ArrayList<Integer>();
        Query query = this.getEntityManager().createNativeQuery(GET_FUNCTION_ON.toString());
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            resulf.add((int) (objects[0]));
        }
        return resulf;
    }
}