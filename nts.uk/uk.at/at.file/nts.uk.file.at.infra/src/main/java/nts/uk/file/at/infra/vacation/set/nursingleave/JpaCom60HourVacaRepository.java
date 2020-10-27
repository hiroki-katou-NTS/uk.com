package nts.uk.file.at.infra.vacation.set.nursingleave;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.sixtyhours.Com60HourVacaRepository;
import nts.uk.file.at.infra.vacation.set.DataEachBox;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

import javax.ejb.Stateless;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.util.*;

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.*;

@Stateless
public class JpaCom60HourVacaRepository extends JpaRepository implements Com60HourVacaRepository {
    private static final String GET_COM_60HOUR_VACATION =
            "SELECT " +
                    " HV.IS_MANAGE, " +
                    " HV.SIXTY_HOUR_EXTRA, " +
                    " HV.DIGESTIVE_UNIT " +
                    "FROM KSHMT_HD60H_COM HV " +
                    "WHERE HV.CID = ? ";


    @Override
    public List<MasterData> getAllCom60HourVacation(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_COM_60HOUR_VACATION)) {
            stmt.setString(1, cid);
            NtsResultSet result = new NtsResultSet(stmt.executeQuery());
            result.forEach(i -> {
                datas.addAll(buildMasterListData(i));
            });

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(datas.isEmpty()){
            return buildMasterListData();
        }
        return datas;
    }
    private List<MasterData> buildMasterListData() {
        List<MasterData> datas = new ArrayList<>();
        datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_227"),ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.LEFT)
        ));
        // Row 2
        datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                , new DataEachBox(I18NText.getText("KMF001_228"),ColumnTextAlign.LEFT)
                , new DataEachBox(I18NText.getText("KMF001_229"),ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.RIGHT)
        ));
        // Row 3
        datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.LEFT)
                , new DataEachBox( I18NText.getText("KMF001_230"),ColumnTextAlign.LEFT)
                , new DataEachBox(null,ColumnTextAlign.RIGHT)
        ));
        return datas;
    }

    private List<MasterData> buildMasterListData(NtsResultSet.NtsResultRecord rs) {
        List<MasterData> datas = new ArrayList<>();
            /*※15*/
            boolean checkIsManager = rs.getString("IS_MANAGE").equals("1");
           if(checkIsManager){
               // Row 1
               datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_227"),ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(getTextEnumManageDistinct(Integer.valueOf(rs.getString("IS_MANAGE"))),ColumnTextAlign.LEFT)
               ));
               // Row 2
               datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(I18NText.getText("KMF001_228"),ColumnTextAlign.LEFT)
                       , new DataEachBox(I18NText.getText("KMF001_229"),ColumnTextAlign.LEFT)
                       ,   new DataEachBox(checkIsManager ?  getTextEnumTimeDigestiveUnit(Integer.valueOf(rs.getString("DIGESTIVE_UNIT"))) : null,ColumnTextAlign.RIGHT)
               ));
               // Row 3
               datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox( I18NText.getText("KMF001_230"),ColumnTextAlign.LEFT)
                       , new DataEachBox(checkIsManager ?  getTextEnumSixtyHourExtra(Integer.valueOf(rs.getString("SIXTY_HOUR_EXTRA"))) : null,ColumnTextAlign.RIGHT)
               ));
           }
           else {
               datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_227"),ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(getTextEnumManageDistinct(Integer.valueOf(rs.getString("IS_MANAGE"))),ColumnTextAlign.LEFT)
               ));
               // Row 2
               datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(I18NText.getText("KMF001_228"),ColumnTextAlign.LEFT)
                       , new DataEachBox(I18NText.getText("KMF001_229"),ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.RIGHT)
               ));
               // Row 3
               datas.add(buildARow(new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.LEFT)
                       , new DataEachBox( I18NText.getText("KMF001_230"),ColumnTextAlign.LEFT)
                       , new DataEachBox(null,ColumnTextAlign.RIGHT)
               ));
           }




        return datas;
    }
    private MasterData buildARow(DataEachBox value1, DataEachBox value2, DataEachBox value3, DataEachBox value4) {
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(EmployeeSystemImpl.KMF001_166, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_166)
                .value(value1.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value1.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_B01, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_B01)
                .value(value2.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value2.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_170, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_170)
                .value(value3.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value3.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_167, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_167)
                .value(value4.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value4.getPositon()))
                .build());

        return MasterData.builder().rowData(data).build();
    }
}
