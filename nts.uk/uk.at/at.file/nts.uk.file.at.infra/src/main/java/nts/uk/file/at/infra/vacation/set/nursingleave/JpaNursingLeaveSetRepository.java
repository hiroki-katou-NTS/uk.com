package nts.uk.file.at.infra.vacation.set.nursingleave;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.file.at.app.export.vacation.set.nursingleave.NursingLeaveSetRepository;
import nts.uk.file.at.app.export.vacation.set.nursingleave.NursingLeaveSettingImpl;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacatRepository;
import nts.uk.file.at.app.export.vacation.set.subst.ComSubstVacationImpl;
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
public class JpaNursingLeaveSetRepository extends JpaRepository implements NursingLeaveSetRepository {
    private static final String GET_NURSING_LEAVE_SETTING =
            "SELECT " +
                    " LV.MANAGE_ATR, " +
                    " LV.STR_MD,  " +
                    " LV.NUM_LEAVE_DAY, " +
                    " LV.NUM_PERSON, " +
                    " LV.SPE_HOLIDAY, " +
                    " LV.WORK_ABS, " +
                    " LV2.MANAGE_ATR, " +
                    " LV2.STR_MD, " +
                    " LV2.NUM_LEAVE_DAY, " +
                    " LV2.NUM_PERSON, " +
                    " SF.NAME, " +
                    " AF.ABSENCE_FRAME_NO " +
                    "FROM ( " +
                    " SELECT " +
                    "  * " +
                    " FROM KNLMT_NURSING_LEAVE_SET LVA  " +
                    " WHERE LVA.CID = ? AND LVA.NURSING_TYPE = 0  " +
                    " ) AS LV " +
                    "INNER JOIN KNLMT_NURSING_LEAVE_SET LV2 ON LV2.CID = LV.CID AND LV2.NURSING_TYPE = 1  " +
                    "LEFT JOIN KSHMT_SPHD_FRAME SF ON SF.CID = LV.CID AND SF.SPHD_FRAME_NO = LV2.SPE_HOLIDAY  " +
                    "LEFT JOIN KSHMT_ABSENCE_FRAME AF ON AF.CID = LV.CID AND AF.ABSENCE_FRAME_NO = LV2.WORK_ABS  ";


    @Override
    public List<MasterData> getAllNursingLeaveSetting(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_NURSING_LEAVE_SETTING)) {
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
            /*※16*/
            boolean checkIsManager = rs.getString(0).equals("1");
            /*※17*/
            boolean checkIsManagerLv2 = rs.getString(6).equals("1");


            // Row 1
            datas.add(buildARow(/*A24_4*/I18NText.getText("KMF001_232"), "", "", "", /*A25_1*/getTextEnumManageDistinct(Integer.valueOf(rs.getString(0)))));
            // Row 2 validated by 16
            datas.add(buildARow("", /*A24_3*/I18NText.getText("KMF001_231"),/*A24_5*/ I18NText.getText("KMF001_233"),/*A24_6*/ I18NText.getText("KMF001_234"), /*A25_2*/rs.getString(1) +/*A25_15*/I18NText.getText("KMF001_246") ));
            // Row 3
            datas.add(buildARow("", "", "", /*A24_7*/I18NText.getText("KMF001_235"), /*A25_3*/rs.getString(2)+/*A25_16*/I18NText.getText("KMF001_233")));
            // Row 4
            datas.add(buildARow("", "",/*24_8*/I18NText.getText("KMF001_236"), /*24_9*/I18NText.getText("KMF001_237"), /*25_4*/rs.getString(4)+/*A25_16*/I18NText.getText("KMF001_233")));
            // Row 5
            datas.add(buildARow("", "", "",/*24_10*/I18NText.getText("KMF001_238"),/*25_5*/ rs.getString(5)+/*A25_16*/I18NText.getText("KMF001_233") ));
            // Row 6
            datas.add(buildARow("", "",/*24_11*/I18NText.getText("KMF001_239"), /*24_12*/I18NText.getText("KMF001_240"),/*25_6*/ rs.getString(6)));
            // Row 7
            datas.add(buildARow("", "", "",/*24_13*/ I18NText.getText("KMF001_241"),/*25_7*/ rs.getInt(7) == 0 ? "管理する" : "管理しない"));
            // Row 8
            datas.add(buildARow(/*24_14*/I18NText.getText("KMF001_242"), "","", "", /*A25_8*/rs.getInt(8) == 0 ? "会社一律" : "年休付与テーブルを参照"));
            // Row 9
            datas.add(buildARow("", /*24_15*/I18NText.getText("KMF001_243"),/*24_16*/I18NText.getText("KMF001_233"), /*24_17*/I18NText.getText("KMF001_234"),/*25_9*/ rs.getString(9)+/*25_15*/I18NText.getText("KMF001_186")));
            // Row 10
            datas.add(buildARow("", "", "",/*24_18*/I18NText.getText("KMF001_235"),/*25_10*/ rs.getString(10)+/*A25_16*/I18NText.getText("KMF001_197")));
            // Row 11
            datas.add(buildARow("","",/*24_19*/I18NText.getText("KMF001_189"),/*24_20*/I18NText.getText("KMF001_189"), /*25_11*/rs.getString(11)+/*A25_16*/I18NText.getText("KMF001_197")));
            // Row 12
            datas.add(buildARow("","","",/*24_21*/I18NText.getText("KMF001_191"),/*25_12*/ rs.getString(12)+/*A25_16*/I18NText.getText("KMF001_197")));
            // Row 13
            datas.add(buildARow("", "", /*24_22*/I18NText.getText("KMF001_192"),/*24_23*/ I18NText.getText("KMF001_192"),/*25_13*/ rs.getString(13)));
            // Row 14
            datas.add(buildARow("", "","", /*24_24*/I18NText.getText("KMF001_193"),/*25_14*/ rs.getString(14)));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datas;
    }
    private MasterData buildARow(String value1, String value2, String value3, String value4, String value5) {
        Map<String, Object> data = new HashMap<>();
        data.put(NursingLeaveSettingImpl.KMF001_166, value1);
        data.put(NursingLeaveSettingImpl.KMF001_168, value2);
        data.put(NursingLeaveSettingImpl.KMF001_169, value3);
        data.put(NursingLeaveSettingImpl.KMF001_170, value4);
        data.put(NursingLeaveSettingImpl.KMF001_167, value5);
        return new MasterData(data, null, "");
    }
}

