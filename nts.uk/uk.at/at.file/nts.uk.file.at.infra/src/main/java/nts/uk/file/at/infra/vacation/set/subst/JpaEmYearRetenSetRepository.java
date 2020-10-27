package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.subst.EmplYearlyRetenSetRepository;
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
public class JpaEmYearRetenSetRepository  extends JpaRepository implements EmplYearlyRetenSetRepository {
    private static final String GET_EMPLOY_YEARLY_RETEN =
            "SELECT EC.EMP_CTR_CD , " +
                    "EM.NAME, " +
                    "EC.MANAGEMENT_CTR_ATR, " +
                    "EC.NUMBER_OF_YEAR, " +
                    "EC.MAX_NUMBER_OF_DAYS  " +
                    "                    FROM ( " +
                    "                    SELECT BSYMT_EMPLOYMENT.CID,BSYMT_EMPLOYMENT.CODE,BSYMT_EMPLOYMENT.NAME  " +
                    "                    FROM BSYMT_EMPLOYMENT  " +
                    "                    WHERE BSYMT_EMPLOYMENT.CID= ?  " +
                    "                    ) as  EM " +
                    "RIGHT JOIN (SELECT * FROM KSHMT_HDSTK_SET_EMP CTR WHERE CTR.CID = ? ) as EC ON EC.EMP_CTR_CD = EM.CODE" +
                    "" +
                    "                    ORDER BY EC.EMP_CTR_CD ASC;";


    @Override
    public List<MasterData> getAllEmplYearlyRetenSet(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_EMPLOY_YEARLY_RETEN)) {
            stmt.setString(1, cid);
            stmt.setString(2, cid);
            datas = new NtsResultSet(stmt.executeQuery())
                    .getList(x -> buildARow(
                            x.getString("EMP_CTR_CD")
                            ,x.getString("NAME")
                            ,CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(x.getString("MANAGEMENT_CTR_ATR")))
                            ,x.getString("NUMBER_OF_YEAR")+I18NText.getText("KMF001_198")
                            ,x.getString("MAX_NUMBER_OF_DAYS")+I18NText.getText("KMF001_197")
                    ));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4,String value5) {
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(EmployeeSystemImpl.KMF001_204, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_204)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_205, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_205)
                .value(value2 == null? "マスタ未登録" :value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_200, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_200)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_201, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_201)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_202, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_202)
                .value(value5)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        return MasterData.builder().rowData(data).build();
    }
}
