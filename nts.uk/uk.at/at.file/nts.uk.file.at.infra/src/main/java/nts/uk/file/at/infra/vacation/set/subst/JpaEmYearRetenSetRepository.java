package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
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

@Stateless
public class JpaEmYearRetenSetRepository  extends JpaRepository implements EmplYearlyRetenSetRepository {
    private static final String GET_EMPLOY_YEARLY_RETEN =
            "SELECT ctr.EMP_CTR_CD ," +
                 "em.NAME," +
                 "ctr.MANAGEMENT_CTR_ATR," +
                 "ctr.NUMBER_OF_YEAR," +
                 "ctr.MAX_NUMBER_OF_DAYS " +
            "FROM (" +
                    "SELECT BSYMT_EMPLOYMENT.CID,BSYMT_EMPLOYMENT.CODE,BSYMT_EMPLOYMENT.NAME " +
                    "FROM BSYMT_EMPLOYMENT " +
                    "WHERE BSYMT_EMPLOYMENT.CID= ? " +
                    ") as em " +
            "INNER JOIN KMFMT_RETENTION_EMP_CTR ctr ON em.CODE = ctr.EMP_CTR_CD AND ctr.CID = em.CID " +
            "ORDER BY em.CODE ASC";


    @Override
    public List<MasterData> getAllEmplYearlyRetenSet(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOY_YEARLY_RETEN)) {
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
            datas.add(buildARow(rs.getString(0),rs.getString(1)+I18NText.getText("KMF001_198"),rs.getString(2)+I18NText.getText("KMF001_197"),rs.getString(3)));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4) {
        Map<String, Object> data = new HashMap<>();
        data.put(RetenYearlySetImpl.KMF001_200, value1);
        data.put(RetenYearlySetImpl.KMF001_201, value2);
        data.put(RetenYearlySetImpl.KMF001_202, value3);
        data.put(RetenYearlySetImpl.KMF001_203, value4);
        return new MasterData(data, null, "");
    }
}
