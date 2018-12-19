package nts.uk.file.at.infra.vacation.set.sixtyhours;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.sixtyhours.Com60HourVacaRepository;
import nts.uk.file.at.app.export.vacation.set.sixtyhours.Com60HourVacationImpl;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacatRepository;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacationImpl;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nts.uk.file.at.infra.vacation.set.compensatoryleave.CommonTempHolidays.*;

public class JpaCom60HourVacaRepository extends JpaRepository implements Com60HourVacaRepository {
    private static final String GET_COM_60HOUR_VACATION =
                    "SELECT " +
                    " HV.IS_MANAGE, " +
                    " HV.SIXTY_HOUR_EXTRA, " +
                    " HV.DIGESTIVE_UNIT " +
                    "FROM KSHST_COM_60H_VACATION HV " +
                    "WHERE HV.CID = ? ";


    @Override
    public List<MasterData> getAllCom60HourVacation(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_COM_60HOUR_VACATION)) {
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
            /*※15*/
            boolean checkIsManager = rs.getString(0).equals("1");
            // Row 1
            datas.add(buildARow(I18NText.getText("KMF001_227"), "", "", getTextEnumManageDistinct(Integer.valueOf(rs.getString(0)))));
            // Row 2
            datas.add(buildARow("", I18NText.getText("KMF001_228"), I18NText.getText("KMF001_229"),checkIsManager ?  getTextEnumTimeDigestiveUnit(Integer.valueOf(rs.getString(1))) : null));
            // Row 3
            datas.add(buildARow("", "", I18NText.getText("KMF001_230"), checkIsManager ?  getTextEnumSixtyHourExtra(Integer.valueOf(rs.getString(2))) : null));


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4) {
        Map<String, Object> data = new HashMap<>();
        data.put(Com60HourVacationImpl.KMF001_166, value1);
        data.put(Com60HourVacationImpl.KMF001_168, value2);
        data.put(Com60HourVacationImpl.KMF001_170, value3);
        data.put(Com60HourVacationImpl.KMF001_167, value4);
        return new MasterData(data, null, "");
    }
}

