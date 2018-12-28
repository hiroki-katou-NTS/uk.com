package nts.uk.file.at.infra.vacation.set.annualpaidleave;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.annualpaidleave.AcquisitionRuleExportRepository;
import nts.uk.file.at.infra.vacation.set.CommonTempHolidays;
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

@Stateless
public class JpaAcquisitionRuleExRepository extends JpaRepository implements AcquisitionRuleExportRepository {
    private static final String GET_ACQUISITION_RULE =
                    "SELECT  "
                            +"AR.MANAGE_ATR, "
                            +"AR.EXCESS_HOLIDAY, "
                            +"AR.SABSTITUTE_HOLIDAY, "
                            +"AR.FUNDED_PAID_HOLIDAY, "
                            +"AR.EXCESS_HOLIDAY, "
                            +"AR.OVERRIDE_HOLIDAY "
                    +"FROM KARST_ACQUISITION_RULE AR "
                    +"WHERE AR.CID = ? ";

    @Override
    public List<MasterData> getAllAcquisitionRule(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection()
                .prepareStatement(GET_ACQUISITION_RULE)){
            stmt.setString(1,cid);
            NtsResultSet result = new NtsResultSet(stmt.executeQuery());
            result.forEach(i->{
                datas.addAll(buildMasterListData(i));
            });

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datas;
    }
    private List<MasterData> buildMasterListData(NtsResultSet.NtsResultRecord rs) {
        List<MasterData> datas = new ArrayList<>();
        /*※1*/
        boolean checkIsManger = rs.getString("MANAGE_ATR").equals("1");
        if(checkIsManger){
            // Row 1
            datas.add(toData(I18NText.getText("KMF001_168"),"", "", CommonTempHolidays.getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR")))));
            // Row 2
            datas.add(toData("",I18NText.getText("KMF001_169"), I18NText.getText("KMF001_170"),fillValue( rs.getString("EXCESS_HOLIDAY"))));
            // Row 3
            datas.add(toData("", "", I18NText.getText("KMF001_171"), fillValue(rs.getString("SABSTITUTE_HOLIDAY"))));
            // Row 4
            datas.add(toData("", "", I18NText.getText("KMF001_172"), fillValue(rs.getString("FUNDED_PAID_HOLIDAY"))));
            // Row 5
            datas.add(toData("",I18NText.getText("KMF001_173"),I18NText.getText("KMF001_174"),fillValue(rs.getString("EXCESS_HOLIDAY"))));
            // Row 6
            datas.add(toData("", "",I18NText.getText("KMF001_175"), fillValue(rs.getString("OVERRIDE_HOLIDAY"))));
        }
        else{
            // Row 1
            datas.add(toData(I18NText.getText("KMF001_168"),"", "", null));
            // Row 2
            datas.add(toData("",I18NText.getText("KMF001_169"), I18NText.getText("KMF001_170"),null));
            // Row 3
            datas.add(toData("", "", I18NText.getText("KMF001_171"),null));
            // Row 4
            datas.add(toData("", "", I18NText.getText("KMF001_172"), null));
            // Row 5
            datas.add(toData("",I18NText.getText("KMF001_173"),I18NText.getText("KMF001_174"),null));
            // Row 6
            datas.add(toData("", "",I18NText.getText("KMF001_175"), null));
        }


        return datas;
    }
    private String fillValue(String value){
        return value.equals("1") ? "○" : "-";
    }
    private MasterData toData(String value1, String value2, String value3, String value4)
    {
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(EmployeeSystemImpl.KMF001_166, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_166)
                .value(value1)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_B01, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_B01)
                .value(value2)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_B02, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_B02)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(EmployeeSystemImpl.KMF001_167, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_167)
                .value(value4)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        return MasterData.builder().rowData(data).build();
    }

}
