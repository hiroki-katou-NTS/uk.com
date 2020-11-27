package nts.uk.file.at.infra.vacation.set.subst;

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumApplyPermission;
import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumExpirationTime;
import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumManageDistinct;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacatRepository;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaComSubstVacatRepository extends JpaRepository implements ComSubstVacatRepository {
    private static final String GET_COM_SUBST_VACATION =
            "SELECT " +
                    " VA.MANAGE_ATR, " +
                    " VA.EXPIRATION_DATE_SET, " +
                    " VA.ALLOW_PREPAID_LEAVE, " +
                    " VA.EXP_DATE_MNG_METHOD, " +
                    " VA.LINK_MNG_ATR " +
                    "FROM KSHMT_HDSUB_CMP VA " +
                    "WHERE VA.CID = ?";


    @Override
    public List<MasterData> getAllComSubstVacation(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_COM_SUBST_VACATION)) {
            stmt.setString(1, cid);
            NtsResultSet result = new NtsResultSet(stmt.executeQuery());
            result.forEach(i->{
                datas.addAll(buildMasterListData(i));
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
    private List<MasterData> buildMasterListData(NtsResultSet.NtsResultRecord rs) {
        List<MasterData> datas = new ArrayList<>();
        /*※13*/
        boolean checkIsManager = rs.getString("MANAGE_ATR").equals("1");
        datas.add(buildARow(
                getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR"))),
                checkIsManager ? getTextEnumExpirationTime(Integer.valueOf(rs.getString("EXPIRATION_DATE_SET"))) : null,
                checkIsManager ? getTextEnumApplyPermission(Integer.valueOf(rs.getString("ALLOW_PREPAID_LEAVE"))) : null,
                Integer.valueOf(rs.getString("EXP_DATE_MNG_METHOD")) == 1?I18NText.getText("Enum_TermManagement_MANAGE_BASED_ON_THE_DATE"):I18NText.getText("Enum_TermManagement_MANAGE_BY_TIGHTENING"),
        		Integer.valueOf(rs.getString("LINK_MNG_ATR")) == 1?I18NText.getText("Enum_ApplyPermission_ALLOW"):I18NText.getText("Enum_ApplyPermission_NOT_ALLOW")
                ));

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3,String value4,String value5) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(EmployeeSystemImpl.KMF001_224, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_224)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_225, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_225)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_226, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_226)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_327, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_327)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_330, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_330)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }
}
