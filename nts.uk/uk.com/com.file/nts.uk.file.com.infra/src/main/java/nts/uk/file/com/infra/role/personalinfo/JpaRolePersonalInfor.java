package nts.uk.file.com.infra.role.personalinfo;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.com.app.role.personalinfo.RolePersonalInforExportImpl;
import nts.uk.file.com.app.role.personalinfo.RolePersonalInforRepository;
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
public class JpaRolePersonalInfor extends JpaRepository implements RolePersonalInforRepository {
    /*It's a point to start Generate function_no */
    public static final int ROW_START_FUNCTION_NO = 6;
    public static final int TRUE = 1;
    @Override
    public List<MasterData> findAllRolePersonalInfor(int roleType, String cId) {      
        List<MasterData> datas = new ArrayList<>();
        Map<Integer, String> arrFunctionNo;
        arrFunctionNo = findAllFunctionNo();
        if(arrFunctionNo.isEmpty()){
            return new ArrayList<MasterData>();
        }
        List<Integer> listFunctionNo = arrFunctionNo.keySet().stream().collect(Collectors.toList());
        String functionNo = CommonRole.getQueryFunctionNo(listFunctionNo);
        String GET_EXPORT_EXCEL = "SELECT ROLE_CD,ROLE_NAME,ASSIGN_ATR,REF_RANGE ,REFER_FUTURE_DATE," +
                functionNo +
                " FROM ( " +
                "SELECT r.ROLE_ID , r.ROLE_CD,r.ROLE_TYPE, r.REF_RANGE ,pr.REFER_FUTURE_DATE,r.ROLE_NAME,r.ASSIGN_ATR, " +
                "CASE WHEN a.IS_AVAILABLE IS NULL THEN f.DEFAULT_VALUE " +
                "ELSE a.IS_AVAILABLE " +
                "END IS_AVAILABLE, f.FUNCTION_NO " +
                "FROM SACMT_ROLE r " +
                "LEFT JOIN SACMT_ROLE_PERSON pr ON r.ROLE_ID = pr.ROLE_ID  " +
                "LEFT JOIN PPECT_ROLE_FUNC f on f.FUNCTION_NO = f.FUNCTION_NO " +
                "LEFT JOIN PPEMT_ROLE_AUTH a ON  r.CID = a.CID AND r.ROLE_ID = a.ROLE_ID AND f.FUNCTION_NO = a.FUNCTION_NO " +
                "WHERE r.CID = ? AND r.ROLE_TYPE = ? " +
                ") " +
                "AS sourceTable PIVOT ( " +
                "    MAX(IS_AVAILABLE) " +
                "    FOR [FUNCTION_NO] IN (" +
                functionNo +
                ") " +
                ") AS pvt " +
                "ORDER BY ASSIGN_ATR,ROLE_CD ASC";
        try (
                PreparedStatement stmt = this.connection().prepareStatement(GET_EXPORT_EXCEL)) {
            stmt.setString(1,cId);
            stmt.setString(2,String.valueOf(roleType));
            datas.addAll(new NtsResultSet(stmt.executeQuery()).getList(i -> toData(i,listFunctionNo)));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;

    }

    private MasterData toData(NtsResultSet.NtsResultRecord r, List<Integer> listFunctionNo)
    {
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(RolePersonalInforExportImpl.CAS009_23, MasterCellData.builder()
                .columnId(RolePersonalInforExportImpl.CAS009_23)
                .value(r.getString("ROLE_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RolePersonalInforExportImpl.CAS009_24, MasterCellData.builder()
                .columnId(RolePersonalInforExportImpl.CAS009_24)
                .value(r.getString("ROLE_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RolePersonalInforExportImpl.CAS009_25, MasterCellData.builder()
                .columnId(RolePersonalInforExportImpl.CAS009_25)
                .value(CommonRole.getTextRoleAtr(r.getString("ASSIGN_ATR")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RolePersonalInforExportImpl.CAS009_26, MasterCellData.builder()
                .columnId(RolePersonalInforExportImpl.CAS009_26)
                .value(CommonRole.getTextEnumEmplReferRange(Integer.valueOf(r.getString("REF_RANGE"))))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(RolePersonalInforExportImpl.CAS009_27, MasterCellData.builder()
                .columnId(RolePersonalInforExportImpl.CAS009_27)
                .value(r.getString("REFER_FUTURE_DATE").equals("1")?I18NText.getText("CAS009_18"):I18NText.getText("CAS009_19"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        for (int i = 0 ; i < listFunctionNo.size() ; i++){
            data.put(RolePersonalInforExportImpl.FUNCTION_NO_+listFunctionNo.get(i), MasterCellData.builder()
                    .columnId(RolePersonalInforExportImpl.FUNCTION_NO_+listFunctionNo.get(i))
                    .value(r.getString(i+ROW_START_FUNCTION_NO) != null && r.getString(i+ROW_START_FUNCTION_NO).equals(TRUE+"")? "○" : "-")
                    .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                    .build());
        }
        return MasterData.builder().rowData(data).build();
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
