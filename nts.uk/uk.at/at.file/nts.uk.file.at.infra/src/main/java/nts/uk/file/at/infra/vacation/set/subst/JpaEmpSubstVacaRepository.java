package nts.uk.file.at.infra.vacation.set.subst;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.subst.EmpSubstVacaRepository;
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

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.*;
@Stateless
public class JpaEmpSubstVacaRepository extends JpaRepository implements EmpSubstVacaRepository {
    private static final String GET_EM_SUBST_VACATION =
            "SELECT  " +
                    "                     SV.EMPCD,  " +
                    "                     EM.NAME,  " +
                    "                     SV.MANAGE_ATR  " +
            "                                FROM (  " +
            "                                        SELECT BE.CID,BE.CODE,BE.NAME  " +
            "                                        FROM BSYMT_EMPLOYMENT  BE " +
            "                                        WHERE BE.CID= ?  " +
            "                                        ) as EM  " +
            "                                RIGHT JOIN (SELECT * FROM KSHMT_HDSUB_EMP SV WHERE SV.CID = ? ) as SV ON EM.CODE = SV.EMPCD AND SV.CID = EM.CID  " +
            "                                ORDER BY SV.EMPCD ASC;";


    @Override
    public List<MasterData> getAllEmpSubstVacation(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_EM_SUBST_VACATION)) {
            stmt.setString(1, cid);
            stmt.setString(2, cid);
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
        /*※14*/
        boolean checkIsManager = rs.getString("MANAGE_ATR").equals("1");
        datas.add(buildARow(
                rs.getString("EMPCD"),
                rs.getString("NAME"),
                getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR")))
                ));

        return datas;
    }

    private MasterData buildARow(String value1, String value2, String value3) {
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
        data.put(EmployeeSystemImpl.KMF001_224, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_224)
                .value(value3)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }
}

