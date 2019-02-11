package nts.uk.file.at.infra.vacation.set.compensatoryleave;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TemHoliEmployeeRepository;
import nts.uk.file.at.infra.vacation.set.CommonTempHolidays;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Stateless
public class JpaTemHoliEmRepository extends JpaRepository implements TemHoliEmployeeRepository {

    private static final String GET_TEM_HOLIDAYS_EMPLOYEE=
            " SELECT  " +
                    "                     LE.EMPCD,  " +
                    "                     EM.NAME,  " +
                    "                     LE.MANAGE_ATR,  " +
                    "                     AE.EXP_TIME,  " +
                    "                     AE.PREEMP_PERMIT_ATR,  " +
                    "                     TE.MANAGE_ATR AS MANAGE_ATR_TE,  " +
                    "                     TE.DIGESTIVE_UNIT  " +
                    "                FROM (  " +
                    "                     SELECT EM1.CID,EM1.CODE,EM1.NAME   " +
                    "                     FROM BSYMT_EMPLOYMENT EM1  " +
                    "                     WHERE EM1.CID = ?   " +
                    "                     ) as EM   " +
                    "                RIGHT JOIN (SELECT * FROM KCLMT_COMPENS_LEAVE_EMP LE WHERE LE.CID = ? ) as LE ON LE.EMPCD = EM.CODE " +
                    "                LEFT JOIN (SELECT * FROM KCLMT_ACQUISITION_EMP AE WHERE AE.CID = ? )as AE ON LE.EMPCD = AE.EMPCD " +
                    "                LEFT JOIN (SELECT * FROM KCTMT_DIGEST_TIME_EMP TE WHERE TE.CID = ? )as TE ON TE.EMPCD = LE.EMPCD  " +
                    "                ORDER BY LE.EMPCD ASC;";


    @Override
    public List<MasterData> getTemHoliEmployee(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_TEM_HOLIDAYS_EMPLOYEE)) {
            stmt.setString(1, cid);
            stmt.setString(2, cid);
            stmt.setString(3, cid);
            stmt.setString(4, cid);
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
        /*※11*/
        boolean checkManagerAtr = rs.getString("MANAGE_ATR").equals("1");

        datas.add(buildARow(
                rs.getString("EMPCD"),
                rs.getString("NAME"),
                CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR"))),
                checkManagerAtr ? CommonTempHolidays.getTextEnumExpirationTime(Integer.valueOf(rs.getString("EXP_TIME"))) : null,
                checkManagerAtr ? CommonTempHolidays.getTextEnumApplyPermission(Integer.valueOf(rs.getString("PREEMP_PERMIT_ATR"))): null,
                checkManagerAtr ? CommonTempHolidays.getTextEnumManageDistinct((Integer.valueOf(rs.getString("MANAGE_ATR_TE")))) : null,
                checkManagerAtr ? CommonTempHolidays.getTextEnumTimeDigestiveUnit(Integer.valueOf(rs.getString("DIGESTIVE_UNIT"))) : null
                ));
        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        Map<String, MasterCellData> data = new HashMap<>();
        data.put(EmployeeSystemImpl.KMF001_204, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_204)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_205, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_205)
                .value(value2 == null ? "マスタ未登録" : value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_223, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_223)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_207, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_207)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_208, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_208)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_210, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_210)
                .value(value6)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_211, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_211)
                .value(value7)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        return MasterData.builder().rowData(data).build();
    }

}
