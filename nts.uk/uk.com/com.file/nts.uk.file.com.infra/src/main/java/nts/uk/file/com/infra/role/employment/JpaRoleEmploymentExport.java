package nts.uk.file.com.infra.role.employment;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.com.app.role.employment.RoleEmpExportRepository;
import nts.uk.file.com.infra.role.CommonRole;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class JpaRoleEmploymentExport  extends JpaRepository implements RoleEmpExportRepository {
    private Map<Integer, String> functionNo;

    private MasterData toData(NtsResultSet.NtsResultRecord r) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(CommonRole.CAS005_122, MasterCellData.builder()
                .columnId(CommonRole.CAS005_122)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_123, MasterCellData.builder()
                .columnId(CommonRole.CAS005_123)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_124, MasterCellData.builder()
                .columnId(CommonRole.CAS005_124)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_125, MasterCellData.builder()
                .columnId(CommonRole.CAS005_125)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_126, MasterCellData.builder()
                .columnId(CommonRole.CAS005_126)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_127, MasterCellData.builder()
                .columnId(CommonRole.CAS005_127)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(CommonRole.CAS005_128, MasterCellData.builder()
                .columnId(CommonRole.CAS005_128)
                .value(r.getString(""))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        return MasterData.builder().rowData(data).build();
    }

    @Override
    public List<MasterData> findAllRoleEmployment(int roleType, String cId) {
        List<MasterData> datas = new ArrayList<>();
        functionNo = findAllFunctionNo();
        if(functionNo.isEmpty()){
            return new ArrayList<MasterData>();
        }
        List<Integer> listFunctionNo = functionNo.keySet().stream().collect(Collectors.toList());
        String functionNo = CommonRole.getQueryFunctionNo(listFunctionNo);
        String GET_EXPORT_EXCEL = "SELECT ROLE_CD , ROLE_NAME , ASSIGN_ATR , REF_RANGE , FUTURE_DATE_REF_PERMIT, WEB_MENU_NAME,SCHEDULE_EMPLOYEE_REF, " +
                functionNo +
                " FROM ( SELECT wm.ROLE_CD , wm.ROLE_NAME, wm.ASSIGN_ATR, wm.REF_RANGE,wi.FUTURE_DATE_REF_PERMIT , edm.WEB_MENU_NAME ,wi.SCHEDULE_EMPLOYEE_REF, AVAILABILITY, wkf.FUNCTION_NO " +
                "FROM (Select * FROM SACMT_ROLE wm1 WHERE wm1.CID = ?cId AND wm1.ROLE_TYPE = ?roleType ) As  wm " +
                "INNER JOIN KACMT_EMPLOYMENT_ROLE wi ON wm.ROLE_ID = wi.ROLE_ID AND wi.CID = wm.CID " +
                "LEFT JOIN SACMT_ROLE_BY_ROLE_TIES  rbrt on rbrt.CID = wm.CID AND rbrt.ROLE_ID = wm.ROLE_ID " +
                "LEFT JOIN CCGST_WEB_MENU edm ON wm.CID = edm.CID AND edm.WEB_MENU_CD = rbrt.WEB_MENU_CD " +
                "INNER JOIN KASMT_WORKPLACE_AUTHORITY kwa ON wm.ROLE_ID = kwa.ROLE_ID AND wm.CID = kwa.CID " +
                "INNER JOIN KASMT_WORPLACE_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO )" +
                "AS sourceTable PIVOT (" +
                "    MAX(AVAILABILITY)" +
                "    FOR [FUNCTION_NO] IN (" +
                functionNo +
                ")) AS pvt ";

        Query query = this.getEntityManager().createNativeQuery(GET_EXPORT_EXCEL)
                .setParameter("cId", cId)
                .setParameter("roleType", roleType);
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            datas.add(new MasterData(dataContent(objects, listFunctionNo), null, ""));
        }
        return datas;
    }
    private Map<String, Object> dataContent(Object[] objects, List<Integer> listFunctionNo) {
        Map<String, Object> data = new HashMap<>();
        data.put(CommonRole.CAS005_122, objects[0]);
        data.put(CommonRole.CAS005_123, objects[1]);
        data.put(CommonRole.CAS005_124, CommonRole.getTextRoleAtr(objects[2].toString()));
        data.put(CommonRole.CAS005_125, CommonRole.getTextEnumEmplReferRange(Integer.valueOf(objects[3].toString())));
        data.put(CommonRole.CAS005_126, objects[4].toString().equals("1")?I18NText.getText("CAS005_42"):I18NText.getText("CAS005_41"));
        data.put(CommonRole.CAS005_127, objects[5]);
        data.put(CommonRole.CAS005_128, CommonRole.getFutureDateRefPermit(Integer.valueOf(objects[6].toString())));
        for (int i = 0 ; i < listFunctionNo.size() ; i++){
            data.put(CommonRole.FUNCTION_NO_+listFunctionNo.get(i) ,objects[i+7].toString().equals("1")? "○" : "ー");
        }
        return data;
    }

    @Override
    public Map<Integer, String> findAllFunctionNo() {
        Map<Integer, String> resulf = new HashMap<>();
        Query query =  this.getEntityManager().createNativeQuery(CommonRole.GET_FUNCTION_NO_CAS005);
        @SuppressWarnings("unchecked")
        List<Object[]> data = query.getResultList();
        for (Object[] objects : data) {
            resulf.put(Integer.valueOf(objects[0].toString()),objects[1].toString());
        }
        return resulf;
    }
}
