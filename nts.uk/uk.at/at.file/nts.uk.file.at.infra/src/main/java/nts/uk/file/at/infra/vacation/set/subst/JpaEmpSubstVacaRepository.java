package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.subst.EmpSubstVacationImpl;
import nts.uk.file.at.app.export.vacation.set.subst.EmpSubstVacaRepository;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.getTextEnumExpirationTime;
import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.getTextEnumManageDistinct;
@Stateless
public class JpaEmpSubstVacaRepository extends JpaRepository implements EmpSubstVacaRepository {
    private static final String GET_EM_SUBST_VACATION =
            "SELECT " +
                    " SV.EMPCD, " +
                    " EM.NAME, " +
                    " SV.IS_MANAGE, " +
                    " SV.EXPIRATION_DATE_SET, " +
                    " SV.ALLOW_PREPAID_LEAVE " +
                    "            FROM ( " +
                    "                    SELECT BSYMT_EMPLOYMENT.CID,BSYMT_EMPLOYMENT.CODE,BSYMT_EMPLOYMENT.NAME " +
                    "                    FROM BSYMT_EMPLOYMENT " +
                    "                    WHERE BSYMT_EMPLOYMENT.CID= ? " +
                    "                    ) as EM " +
                    "            LEFT JOIN KSVST_EMP_SUBST_VACATION SV ON EM.CODE = SV.EMPCD AND SV.CID = EM.CID " +
                    "            ORDER BY EM.CODE ASC";


    @Override
    public List<MasterData> getAllEmpSubstVacation(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_EM_SUBST_VACATION)) {
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
            /*※14*/
            boolean checkIsManager = rs.getString(1).equals("1");
            datas.add(buildARow(
                    rs.getString(0),
                    rs.getString(1),
                    getTextEnumManageDistinct(Integer.valueOf(rs.getString(2))),
                    checkIsManager ? getTextEnumExpirationTime(Integer.valueOf(rs.getString(3))) : null,
                    checkIsManager ? getTextEnumExpirationTime(Integer.valueOf(rs.getString(4))) : null
                    ));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }

    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5) {
        Map<String, Object> data = new HashMap<>();
        data.put(EmpSubstVacationImpl.KMF001_204, value1);
        data.put(EmpSubstVacationImpl.KMF001_205, value2);
        data.put(EmpSubstVacationImpl.KMF001_224, value3);
        data.put(EmpSubstVacationImpl.KMF001_225, value4);
        data.put(EmpSubstVacationImpl.KMF001_226, value5);
        return new MasterData(data, null, "");
    }
}

