package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacatRepository;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.*;

@Stateless
public class JpaComSubstVacatRepository extends JpaRepository implements ComSubstVacatRepository {
    private static final String GET_COM_SUBST_VACATION =
            "SELECT " +
                    " VA.IS_MANAGE, " +
                    " VA.EXPIRATION_DATE_SET, " +
                    " VA.ALLOW_PREPAID_LEAVE " +
                    "FROM KSVST_COM_SUBST_VACATION VA " +
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
        boolean checkIsManager = rs.getString("IS_MANAGE").equals("1");
        datas.add(buildARow(
                getTextEnumManageDistinct(Integer.valueOf(rs.getString("IS_MANAGE"))),
                checkIsManager ? getTextEnumExpirationTime(Integer.valueOf(rs.getString("EXPIRATION_DATE_SET"))) : null,
                checkIsManager ? getTextEnumApplyPermission(Integer.valueOf(rs.getString("ALLOW_PREPAID_LEAVE"))) : null
                ));

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3) {
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

        return MasterData.builder().rowData(data).build();
    }
}
