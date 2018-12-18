package nts.uk.file.com.infra.grantadminrole;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.uk.file.com.app.grantadminrole.GrantAdminRoleColumn;
import nts.uk.file.com.app.grantadminrole.GrantAdminRoleRepository;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.util.*;

@Stateless
public class GrantAdminRoleImpl extends JpaRepository implements GrantAdminRoleRepository {

    private static final String GET_EXPORT_EXCEL = " SELECT "+
            " CASE WHEN p.BUSINESS_NAME IS NULL THEN u.USER_NAME"+
            " ELSE p.BUSINESS_NAME"+
            " END BUSINESS_NAME,"+
            " u.LOGIN_ID, tb.STR_D, tb.END_D FROM"+
            " (SELECT g.USER_ID, g.STR_D, g.END_D FROM SACMT_ROLE_INDIVI_GRANT g WHERE g.CID = ? AND ROLE_TYPE = ?) tb INNER JOIN SACMT_USER u ON tb.USER_ID = u.USER_ID LEFT JOIN BPSMT_PERSON p ON p.PID = u.ASSO_PID ORDER BY LOGIN_ID";

    private static final String GET_EXPORT_EXCEL_COMPANY_MANAGER = " SELECT " +
            " CASE WHEN tb.ROW_NUMBER = 1 THEN tb.CID" +
            " ELSE NULL" +
            " END CID," +
            " CASE WHEN tb.ROW_NUMBER = 1 THEN tb.NAME" +
            " ELSE NULL" +
            " END COMPANY_NAME," +
            " tb.LOGIN_ID," +
            " tb.BUSINESS_NAME, tb.STR_D, tb.END_D" +
            " FROM" +
            " (SELECT " +
            " ROW_NUMBER() OVER(PARTITION BY c.CID ORDER BY c.CID, u.LOGIN_ID) AS ROW_NUMBER,c.CID ,c.NAME, u.LOGIN_ID, g.ROLE_TYPE, g.STR_D, g.END_D," +
            " CASE WHEN p.BUSINESS_NAME IS NULL THEN u.USER_NAME" +
            " ELSE p.BUSINESS_NAME" +
            " END BUSINESS_NAME" +
            " FROM BCMMT_COMPANY c INNER JOIN SACMT_ROLE_INDIVI_GRANT g ON c.CID = g.CID " +
            " INNER JOIN SACMT_USER u ON g.USER_ID = u.USER_ID " +
            " LEFT JOIN BPSMT_PERSON p ON p.PID = u.ASSO_PID WHERE g.ROLE_TYPE = ? AND c.ABOLITION_ATR = 0) tb ORDER BY tb.CID";

    @SneakyThrows
    @Override
    public List<MasterData> getDataExport(String companyId, int roleType) {

        List<MasterData> datas;
        try(PreparedStatement stmt = this.connection().prepareStatement(GET_EXPORT_EXCEL)){
            stmt.setString(1,companyId);
            stmt.setInt(2,roleType);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterData(x));
        }
        return datas;
    }

    private MasterData toMasterData(NtsResultSet.NtsResultRecord r){
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(GrantAdminRoleColumn.CAS012_37, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_37)
                .value(r.getString("LOGIN_ID"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_38, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_38)
                .value(r.getString("BUSINESS_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_39, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_39)
                .value(GeneralDate.localDate(r.getDate("STR_D").toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_40, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_40)
                .value(GeneralDate.localDate(r.getDate("END_D").toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterData1(NtsResultSet.NtsResultRecord r){
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(GrantAdminRoleColumn.CAS012_41, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_41)
                .value(r.getString("CID"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_42, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_42)
                .value(r.getString("COMPANY_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_37, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_37)
                .value(r.getString("LOGIN_ID"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_38, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_38)
                .value(r.getString("BUSINESS_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_39, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_39)
                .value(GeneralDate.localDate(r.getDate("STR_D").toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(GrantAdminRoleColumn.CAS012_40, MasterCellData.builder()
                .columnId(GrantAdminRoleColumn.CAS012_40)
                .value(GeneralDate.localDate(r.getDate("END_D").toLocalDate()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    @SneakyThrows
    @Override
    public List<MasterData> getDataExportCompanyManagerMode(int roleType) {
        List<MasterData> datas;

        try(PreparedStatement stmt = this.connection().prepareStatement(GET_EXPORT_EXCEL_COMPANY_MANAGER)){
            stmt.setInt(1,roleType);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterData1(x));
        }
        return datas;
    }


}
