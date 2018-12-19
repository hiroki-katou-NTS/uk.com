package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AnnPaidLeaveImpl;
import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AnnPaidLeaveRepository;
import nts.uk.file.at.app.export.vacation.set.subst.EmplYearlyRetenSetImpl;
import nts.uk.file.at.app.export.vacation.set.subst.RetenYearlySetImpl;
import nts.uk.file.at.app.export.vacation.set.subst.RetenYearlySetRepository;
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
public class JpaRetenYearlySetRepository extends JpaRepository implements RetenYearlySetRepository {
    private static final String GET_RENTEN_YEARLY_SETTING =
            "SELECT RY.MANAGEMENT_YEARLY_ATR," +
                "RY.NUMBER_OF_YEAR," +
                "RY.MAX_NUMBER_OF_DAYS," +
                "RY.LEAVE_AS_WORK_DAYS "
            + "FROM KMFMT_RETENTION_YEARLY RY "
            + "WHERE RY.CID = ?";



    @Override
    public List<MasterData> getAllRetenYearlySet(String cid) {
        List<MasterData> datas = new ArrayList<>();
        String sql = String.format(GET_RENTEN_YEARLY_SETTING);
        try(PreparedStatement stmt = this.connection().prepareStatement(sql)) {
            stmt.setString(1,cid);
            datas = new NtsResultSet(stmt.executeQuery())
                    .getList(x -> buildARow(
                            x.getString(0)
                            ,x.getString(1)
                            ,x.getString(2)
                            ,x.getString(3)
                            ,x.getString(4)
                    ));

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }

    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5) {
        Map<String, Object> data = new HashMap<>();
        data.put(EmplYearlyRetenSetImpl.KMF001_205, value1);
        data.put(EmplYearlyRetenSetImpl.KMF001_204, value2);
        data.put(EmplYearlyRetenSetImpl.KMF001_202, value3);
        data.put(EmplYearlyRetenSetImpl.KMF001_201, value4);
        data.put(EmplYearlyRetenSetImpl.KMF001_200, value5);
        return new MasterData(data, null, "");
    }
}
