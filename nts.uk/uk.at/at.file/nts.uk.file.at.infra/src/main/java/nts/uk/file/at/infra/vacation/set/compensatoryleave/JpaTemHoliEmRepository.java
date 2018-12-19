package nts.uk.file.at.infra.vacation.set.compensatoryleave;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TemHoliEmployeeImpl;
import nts.uk.file.at.app.export.vacation.set.compensatoryleave.TemHoliEmployeeRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JpaTemHoliEmRepository extends JpaRepository implements TemHoliEmployeeRepository {

    private static final String GET_TEM_HOLIDAYS_EMPLOYEE=
                "SELECT " +
                    " EM.CODE, " +
                    " EM.NAME, " +
                    " LE.EMPCD, " +
                    " LE.MANAGE_ATR, " +
                    " AE.EXP_TIME, " +
                    " AE.PREEMP_PERMIT_ATR, " +
                    " TE.MANAGE_ATR, " +
                    " TE.DIGESTIVE_UNIT " +
                "FROM ( " +
                    " SELECT EM1.CID,EM1.CODE,EM1.NAME  " +
                    " FROM BSYMT_EMPLOYMENT EM1 " +
                    " WHERE EM1.CID= ?  " +
                    " ) as EM  " +
                "LEFT JOIN KCLMT_COMPENS_LEAVE_EMP LE ON LE.EMPCD = EM.CODE AND LE.CID = EM.CID " +
                "LEFT JOIN KCLMT_ACQUISITION_EMP AE ON LE.EMPCD = AE.EMPCD AND EM.CID = AE.CID " +
                "LEFT JOIN KCTMT_DIGEST_TIME_EMP TE ON TE.CID = EM.CID AND TE.EMPCD = LE.EMPCD " +
                "ORDER BY EM.CODE ASC";


    @Override
    public List<MasterData> getAllTemHoliEmployee(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_TEM_HOLIDAYS_EMPLOYEE)) {
            stmt.setString(1, cid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return buildMasterListData(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    private List<MasterData> buildMasterListData(ResultSet rs) {
        List<MasterData> datas = new ArrayList<>();
        try {
            /*※11*/
            boolean checkManagerAtr = rs.getString(3).equals("1");
            /*※12*/
            boolean checkManagerAtrTe = rs.getString(6).equals("1");
            datas.add(buildARow(
                    rs.getString(0),
                    rs.getString(1),
                    CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString(2))),
                    checkManagerAtr ? CommonTempHolidays.getTextEnumExpirationTime(Integer.valueOf(rs.getString(3))) : null,
                    checkManagerAtr ? CommonTempHolidays.getTextEnumApplyPermission(Integer.valueOf(rs.getString(4))): null,
                    checkManagerAtr ? CommonTempHolidays.getTextEnumManageDistinct((Integer.valueOf(rs.getString(5)))) : null,
                    checkManagerAtr && checkManagerAtrTe ? rs.getString(6) : null
                    ));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5, String value6, String value7) {
        Map<String, Object> data = new HashMap<>();
        data.put(TemHoliEmployeeImpl.KMF001_204, value1);
        data.put(TemHoliEmployeeImpl.KMF001_205, value2);
        data.put(TemHoliEmployeeImpl.KMF001_207, value3);
        data.put(TemHoliEmployeeImpl.KMF001_208, value4);
        data.put(TemHoliEmployeeImpl.KMF001_210, value5);
        data.put(TemHoliEmployeeImpl.KMF001_211, value6);
        data.put(TemHoliEmployeeImpl.KMF001_223, value7);
        return new MasterData(data, null, "");
    }

}
