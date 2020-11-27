package nts.uk.file.at.infra.vacation.set.sixtyhours;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.nursingleave.NursingLeaveSetRepository;
import nts.uk.file.at.infra.vacation.set.DataEachBox;
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

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumManageDistinct;

@Stateless
public class JpaNursingLeaveSetRepository extends JpaRepository implements NursingLeaveSetRepository {


    private static final String GET_NURSING_LEAVE_SETTING =
            "SELECT  " +
                    "                     LV.NURSING_TYPE,  " +
                    "                     LV.MANAGE_ATR,  " +
                    "                     LV.STR_MD,   " +
                    "                     LV.STR_MD,   " +
                    "                     LV.NUM_LEAVE_DAY,  " +
                    "                     LV.NUM_PERSON,  " +
                    "                     LV.HDSP_FRAME_NO,  " +
                    "                     LV.ABSENCE_FRAME_NO,  " +
                    "					  LV.TIME_MANAGE_ATR,"+
                    "					  LV.DIGESTIVE_UNIT,"+
                    "                     LV2.NURSING_TYPE AS NURSING_TYPE_LV2 ,  " +
                    "                     LV2.MANAGE_ATR AS MANAGE_ATR_LV2,  " +
                    "                     LV2.STR_MD AS STR_MD_LV2,  " +
                    "                     LV2.STR_MD AS STR_MD_LV2,  " +
                    "                     LV2.NUM_LEAVE_DAY  AS NUM_LEAVE_DAY_LV2,  " +
                    "                     LV2.NUM_PERSON  AS NUM_PERSON_LV2,  " +
                    "					  LV2.TIME_MANAGE_ATR AS TIME_MANAGE_ATR_LV2 ,"+
                    "					  LV2.DIGESTIVE_UNIT AS DIGESTIVE_UNIT_LV2 ,"+
                    "                     SF.NAME,  " +
                    "                     AF.NAME AS NAME_AF ," +
                    "                     SF1.NAME AS NAME_SF1,  " +
                    "                     AF1.NAME AS NAME_AF1  " +
                    "                    FROM (  " +
                    "                     SELECT  " +
                    "                      *  " +
                    "                     FROM KNLMT_NURSING_LEAVE_SET LVA   " +
                    "                     WHERE LVA.CID = ? AND LVA.NURSING_TYPE = 0   " +
                    "                     ) AS LV  " +
                    "                    INNER JOIN KNLMT_NURSING_LEAVE_SET LV2 ON LV2.CID = LV.CID AND LV2.NURSING_TYPE = 1   " +
                    "                    LEFT JOIN KSHMT_SPHD_FRAME SF ON SF.CID = LV.CID AND SF.SPHD_FRAME_NO = LV2.HDSP_FRAME_NO  " +
                    "                    LEFT JOIN KSHMT_ABSENCE_FRAME AF ON AF.CID = LV.CID AND AF.ABSENCE_FRAME_NO = LV2.ABSENCE_FRAME_NO " +
                    "				     LEFT JOIN KSHMT_SPHD_FRAME SF1 ON SF1.CID = LV.CID AND SF1.SPHD_FRAME_NO = LV.HDSP_FRAME_NO  " +
                    "                    LEFT JOIN KSHMT_ABSENCE_FRAME AF1 ON AF1.CID = LV.CID  AND AF1.ABSENCE_FRAME_NO = LV.ABSENCE_FRAME_NO ;";


    @Override
    public List<MasterData> getAllNursingLeaveSetting(String cid) {
        List<MasterData> datas = new ArrayList<>();
        try (PreparedStatement stmt = this.connection().prepareStatement(GET_NURSING_LEAVE_SETTING)) {
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
        // Row 1
        datas.add(buildARow(new DataEachBox(/*A24_4*/I18NText.getText("KMF001_231"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , /*A25_1*/new DataEachBox(null, ColumnTextAlign.LEFT)
        ));
        // Row 2 validated by 16
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*A24_3*/I18NText.getText("KMF001_232"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*A24_5*/ I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*A24_6*/ I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                , /*A25_2*/new DataEachBox(null, ColumnTextAlign.LEFT)
        ));
        // Row 3
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)

                , new DataEachBox(/*A24_7*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                , /*A25_3*/new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 4
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_8*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_9*/I18NText.getText("KMF001_237"), ColumnTextAlign.LEFT)
                , /*25_4*/new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 5
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_10*/I18NText.getText("KMF001_238"), ColumnTextAlign.LEFT)
                ,/*25_5*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 6
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_11*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_12*/I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                ,/*25_6*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 7
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_13*/ I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                ,/*25_7*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 7.1
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_25*/I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                ,/*25_15*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 7.2
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_26*/I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                ,/*25_16*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 8
        datas.add(buildARow(new DataEachBox(/*24_14*/I18NText.getText("KMF001_242"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*A25_8*/null, ColumnTextAlign.LEFT
                )));
        // Row 9
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_15*/I18NText.getText("KMF001_243"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_16*/I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_17*/I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                ,/*25_9*/ new DataEachBox(null, ColumnTextAlign.LEFT))
        );
        // Row 10
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_18*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                ,/*25_10*/new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 11
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_19*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_20*/I18NText.getText("KMF001_244"), ColumnTextAlign.LEFT)
                , /*25_11*/new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 12
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_21*/I18NText.getText("KMF001_245"), ColumnTextAlign.LEFT)
                ,/*25_12*/new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 13
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_22*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_23*/ I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                ,/*25_13*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
        // Row 14
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*24_24*/I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                ,/*25_14*/ new DataEachBox(null, ColumnTextAlign.LEFT))
        
        );
     // Row 14.1
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_27*/ I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*25_17*/ null, ColumnTextAlign.LEFT)
		));
		// Row 14.2
        datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(/*24_28*/ I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                , new DataEachBox(/*25_18*/ null, ColumnTextAlign.LEFT)		
		));
        return datas;
    }

    private List<MasterData> buildMasterListData(NtsResultSet.NtsResultRecord rs) {
        List<MasterData> datas = new ArrayList<>();
        /*※16*/
        boolean checkIsManager = rs.getString("NURSING_TYPE").equals("0") && rs.getString("MANAGE_ATR").equals("1");
        /*※17*/
        boolean checkIsManagerLv2 = rs.getString("NURSING_TYPE_LV2").equals("1") && rs.getString("MANAGE_ATR_LV2").equals("1");

        // Row 1
        datas.add(buildARow(new DataEachBox(/*A24_4*/I18NText.getText("KMF001_231"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , /*A25_1*/new DataEachBox(getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR"))), ColumnTextAlign.LEFT)
        ));
        if (checkIsManager) {
            // Row 2 validated by 16
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    ,new DataEachBox( /*A24_3*/I18NText.getText("KMF001_232"), ColumnTextAlign.LEFT)
                    ,new DataEachBox(/*A24_5*/ I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                    ,new DataEachBox(/*A24_6*/ I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                    , /*A25_2*/new DataEachBox(convertToMonth(rs.getString("STR_MD")) +/*25_15*/I18NText.getText("KMF001_246"), ColumnTextAlign.RIGHT)
            ));
            // Row 3
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*A24_7*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*A25_3*/convertToDays(rs.getString("STR_MD")) +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 4
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_8*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_9*/I18NText.getText("KMF001_237"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_4*/rs.getString("NUM_LEAVE_DAY") +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 5
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_10*/I18NText.getText("KMF001_238"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_5*/ rs.getString("NUM_PERSON") +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 6
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_11*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_12*/I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_6*/ rs.getString("NAME_SF1") == null ? "なし " : rs.getString("NAME_SF1"), ColumnTextAlign.LEFT)
            ));
            // Row 7
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_13*/ I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_7*/ rs.getString("NAME_AF1") == null ? "なし " : rs.getString("NAME_AF1"), ColumnTextAlign.LEFT)
            ));
            		
    		// Row 7.1
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_25*/ I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_15*/ ManageDistinct.valueOf(Integer.parseInt(rs.getString("TIME_MANAGE_ATR"))).nameId, ColumnTextAlign.LEFT)
    		));
    		// Row 7.2
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_26*/ I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_16*/ rs.getString("DIGESTIVE_UNIT") ==null?null: TimeDigestiveUnit.valueOf(Integer.parseInt(rs.getString("DIGESTIVE_UNIT"))).nameId, ColumnTextAlign.LEFT)		
    		));
        } else {
            // Row 2 validated by 16
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*A24_3*/I18NText.getText("KMF001_232"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*A24_5*/ I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*A24_6*/ I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                    , /*A25_2*/new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 3
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*A24_7*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                    , /*A25_3*/new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 4
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_8*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_9*/I18NText.getText("KMF001_237"), ColumnTextAlign.LEFT)
                    , /*25_4*/new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 5
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_10*/I18NText.getText("KMF001_238"), ColumnTextAlign.LEFT)
                    ,/*25_5*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 6
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_11*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_12*/I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                    ,/*25_6*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 7
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_13*/ I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                    ,/*25_7*/ new DataEachBox(null, ColumnTextAlign.LEFT)));
            // Row 7.1
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_25*/ I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_15*/ null, ColumnTextAlign.LEFT)
    		));
    		// Row 7.2
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_26*/ I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_16*/ null, ColumnTextAlign.LEFT)		
    		));
        }


        // Row 8
        datas.add(buildARow(new DataEachBox(/*24_14*/I18NText.getText("KMF001_242"), ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox(null, ColumnTextAlign.LEFT)
                , new DataEachBox( /*A25_8*/getTextEnumManageDistinct(Integer.valueOf(rs.getString("MANAGE_ATR_LV2"))), ColumnTextAlign.LEFT)
        ));
        if (checkIsManagerLv2) {
            // Row 9
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_15*/I18NText.getText("KMF001_243"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_16*/I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_17*/I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_9*/ convertToMonth(rs.getString("STR_MD_LV2")) +/*25_15*/I18NText.getText("KMF001_246"), ColumnTextAlign.RIGHT)
            ));
            // Row 10
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_18*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_10*/ convertToDays(rs.getString("STR_MD_LV2")) +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 11
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_19*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_20*/I18NText.getText("KMF001_244"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*25_11*/rs.getString("NUM_LEAVE_DAY_LV2") +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 12
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_21*/I18NText.getText("KMF001_245"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*25_12*/ rs.getString("NUM_PERSON_LV2") +/*A25_16*/I18NText.getText("KMF001_197"), ColumnTextAlign.RIGHT)
            ));
            // Row 13
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_22*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_23*/ I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_13*/ rs.getString("NAME") == null ? "なし " : rs.getString("NAME"), ColumnTextAlign.LEFT)
            ));
            // Row 14
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_24*/I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*25_14*/ rs.getString("NAME_AF") == null ? "なし " : rs.getString("NAME_AF"), ColumnTextAlign.LEFT)
            ));
            // Row 14.1
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_27*/ I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_17*/ ManageDistinct.valueOf(Integer.parseInt(rs.getString("TIME_MANAGE_ATR_LV2"))).nameId, ColumnTextAlign.LEFT)
    		));
    		// Row 14.2
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_28*/ I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_18*/rs.getString("DIGESTIVE_UNIT_LV2") ==null?null: TimeDigestiveUnit.valueOf(Integer.parseInt(rs.getString("DIGESTIVE_UNIT_LV2"))).nameId, ColumnTextAlign.LEFT)		
    		));
        } else {
            // Row 9
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_15*/I18NText.getText("KMF001_243"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_16*/I18NText.getText("KMF001_233"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_17*/I18NText.getText("KMF001_234"), ColumnTextAlign.LEFT)
                    ,/*25_9*/ new DataEachBox(null, ColumnTextAlign.LEFT))
            );
            // Row 10
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_18*/I18NText.getText("KMF001_235"), ColumnTextAlign.LEFT)
                    ,/*25_10*/new DataEachBox(null, ColumnTextAlign.LEFT)
            ));
            // Row 11
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_19*/I18NText.getText("KMF001_236"), ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_20*/I18NText.getText("KMF001_244"), ColumnTextAlign.LEFT)
                    , /*25_11*/new DataEachBox(null, ColumnTextAlign.LEFT)
            ));
            // Row 12
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_21*/I18NText.getText("KMF001_245"), ColumnTextAlign.LEFT)
                    ,/*25_12*/new DataEachBox(null, ColumnTextAlign.LEFT)
            ));
            // Row 13
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_22*/I18NText.getText("KMF001_239"), ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_23*/ I18NText.getText("KMF001_240"), ColumnTextAlign.LEFT)
                    ,/*25_13*/ new DataEachBox(null, ColumnTextAlign.LEFT)
            ));
            // Row 14
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox( /*24_24*/I18NText.getText("KMF001_241"), ColumnTextAlign.LEFT)
                    ,/*25_14*/ new DataEachBox(null, ColumnTextAlign.LEFT))
            );
            // Row 14.1
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_27*/ I18NText.getText("KMF001_52"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_17*/ null, ColumnTextAlign.LEFT)
    		));
    		// Row 14.2
            datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*24_28*/ I18NText.getText("KMF001_53"), ColumnTextAlign.LEFT)
                    , new DataEachBox(null, ColumnTextAlign.LEFT)
                    , new DataEachBox(/*25_18*/ null, ColumnTextAlign.LEFT)		
    		));
        }


        return datas;
    }


    private MasterData buildARow(DataEachBox value1, DataEachBox value2, DataEachBox value3, DataEachBox value4, DataEachBox value5) {
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(EmployeeSystemImpl.KMF001_166, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_166)
                .value(value1.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value1.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_2, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_2)
                .value(value2.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value2.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_3, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_3)
                .value(value3.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value3.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_4, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_4)
                .value(value4.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value4.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_167, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_167)
                .value(value5.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value5.getPositon()))
                .build());

        return MasterData.builder().rowData(data).build();
    }
    private String convertToMonth(String value){
        return (Integer.parseInt(value)/100)+"";
    }
    private String convertToDays(String value){
        return (Integer.parseInt(value)%100)+"";
    }

}
