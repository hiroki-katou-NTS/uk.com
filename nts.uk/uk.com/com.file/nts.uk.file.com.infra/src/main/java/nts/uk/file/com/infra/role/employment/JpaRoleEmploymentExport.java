package nts.uk.file.com.infra.role.employment;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.com.app.role.employment.RoleEmpExportRepository;
import nts.uk.file.com.app.role.employment.RoleEmploymentExportImpl;
import nts.uk.file.com.infra.role.CommonRole;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import javax.persistence.Query;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class JpaRoleEmploymentExport  extends JpaRepository implements RoleEmpExportRepository {
    /* // 自分のみ
        ONLY_MYSELF(3, "Enum_EmployeeReferenceRange_onlyMyself", "自分のみ");*/
    public static final int EmployeeReferenceRange_ONLY_MYSELF = 3;
    /*It's a point to start Generate function_no */
    public static final int ROW_START_FUNCTION_NO = 8;

    private MasterData toData(NtsResultSet.NtsResultRecord r, List<Integer> listFunctionNo) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(RoleEmploymentExportImpl.CAS005_122, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_122)
                .value(r.getString("ROLE_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_123, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_123)
                .value(r.getString("ROLE_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_124, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_124)
                .value(CommonRole.getTextRoleAtr(r.getString("ASSIGN_ATR")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_125, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_125)
                .value( CommonRole.getTextEnumEmplReferRange(Integer.valueOf(r.getString("REF_RANGE"))))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_126, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_126)
                .value(r.getString("FUTURE_DATE_REF_PERMIT").equals("1")?I18NText.getText("CAS005_42"):I18NText.getText("CAS005_41"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_127, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_127)
                .value(r.getString("WEB_MENU_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RoleEmploymentExportImpl.CAS005_128, MasterCellData.builder()
                .columnId(RoleEmploymentExportImpl.CAS005_128)
                .value(CommonRole.getFutureDateRefPermit(Integer.valueOf(r.getString("SCHEDULE_EMPLOYEE_REF"))))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        for (int i = 0 ; i < listFunctionNo.size() ; i++){
            data.put(RoleEmploymentExportImpl.FUNCTION_NO_+listFunctionNo.get(i), MasterCellData.builder()
                    .columnId(RoleEmploymentExportImpl.FUNCTION_NO_+listFunctionNo.get(i))
                    .value(r.getString("REF_RANGE")!= null && r.getString("REF_RANGE").equals(EmployeeReferenceRange_ONLY_MYSELF+"") ? null : r.getString(i+ROW_START_FUNCTION_NO) != null && r.getString(i+ROW_START_FUNCTION_NO).equals("1")? "○" : "-")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
        }
        return MasterData.builder().rowData(data).build();
    }

    @Override
    public List<MasterData> findAllRoleEmployment(int roleType, String cId) {
        List<MasterData> datas = new ArrayList<>();
        Map<Integer, String> arrFunctionNo;
        arrFunctionNo = findAllFunctionNo();
        if(arrFunctionNo.isEmpty()){
            return new ArrayList<MasterData>();
        }
        List<Integer> listFunctionNo = arrFunctionNo.keySet().stream().collect(Collectors.toList());
        String functionNo = CommonRole.getQueryFunctionNo(listFunctionNo);
        String GET_EXPORT_EXCEL = "SELECT ROLE_CD , ROLE_NAME , ASSIGN_ATR , REF_RANGE , FUTURE_DATE_REF_PERMIT, WEB_MENU_NAME,SCHEDULE_EMPLOYEE_REF, " +
                functionNo +
                " FROM ( SELECT wm.ROLE_CD , wm.ROLE_NAME, wm.ASSIGN_ATR, wm.REF_RANGE,wi.FUTURE_DATE_REF_PERMIT , edm.WEB_MENU_NAME ,wi.SCHEDULE_EMPLOYEE_REF, AVAILABILITY, wkf.FUNCTION_NO " +
                "FROM (Select * FROM SACMT_ROLE wm1 WHERE wm1.CID = ? AND wm1.ROLE_TYPE = ? ) As  wm " +
                "INNER JOIN KACMT_ROLE_ATTENDANCE wi ON wm.ROLE_ID = wi.ROLE_ID AND wi.CID = wm.CID " +
                "LEFT JOIN SPTMT_ROLE_BY_ROLE_TIES  rbrt on rbrt.CID = wm.CID AND rbrt.ROLE_ID = wm.ROLE_ID " +
                "LEFT JOIN SPTMT_WEB_MENU edm ON wm.CID = edm.CID AND edm.WEB_MENU_CD = rbrt.WEB_MENU_CD " +
                "INNER JOIN SACMT_WKP_AUTHORITY kwa ON wm.ROLE_ID = kwa.ROLE_ID AND wm.CID = kwa.CID " +
                "INNER JOIN SACCT_WKP_FUNCTION wkf on wkf.FUNCTION_NO = kwa.FUNCTION_NO )" +
                "AS sourceTable PIVOT (" +
                "    MAX(AVAILABILITY)" +
                "    FOR [FUNCTION_NO] IN (" +
                functionNo +
                ")) AS pvt "+
                "ORDER BY ASSIGN_ATR,ROLE_CD ASC";
        try (PreparedStatement stmt = this.connection()
                .prepareStatement(GET_EXPORT_EXCEL)){
            stmt.setString(1,cId);
            stmt.setString(2,String.valueOf(roleType));
            datas.addAll(new NtsResultSet(stmt.executeQuery()).getList(i -> toData(i,listFunctionNo)));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;
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
