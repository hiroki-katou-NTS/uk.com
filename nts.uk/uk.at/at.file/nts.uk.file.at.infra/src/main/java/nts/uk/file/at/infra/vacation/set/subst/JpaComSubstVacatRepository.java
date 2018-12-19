package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacatRepository;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacationImpl;
import nts.uk.file.at.app.export.vacation.set.subst.EmplYearlyRetenSetRepository;
import nts.uk.file.at.app.export.vacation.set.subst.RetenYearlySetImpl;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.getTextEnumApplyPermission;
import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.getTextEnumExpirationTime;
import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.getTextEnumManageDistinct;

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
            /*※13*/
            boolean checkIsManager = rs.getString(0).equals("1");
            datas.add(buildARow(
                    getTextEnumManageDistinct(Integer.valueOf(rs.getString(0))),
                    checkIsManager ? getTextEnumExpirationTime(Integer.valueOf(rs.getString(1))) : null,
                    checkIsManager ? getTextEnumApplyPermission(Integer.valueOf(rs.getString(2))) : null
                    ));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3) {
        Map<String, Object> data = new HashMap<>();
        data.put(ComSubstVacationImpl.KMF001_224, value1);
        data.put(ComSubstVacationImpl.KMF001_225, value2);
        data.put(ComSubstVacationImpl.KMF001_226, value3);
        return new MasterData(data, null, "");
    }
}
