package nts.uk.file.at.infra.settingtimezone;

import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.file.at.app.export.settingtimezone.SettingTimeZoneRepository;
import nts.uk.file.at.app.export.settingtimezone.SettingTimeZoneUtils;
import nts.uk.shr.com.i18n.TextResource;
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
public class JpaSettingTimeZoneRepository extends JpaRepository implements SettingTimeZoneRepository {

    private static final String SQLSpecialBonusPayTimeItem = "SELECT  " +
            " tb1.TIME_ITEM_NO,  " +
            " tb1.USE_ATR,  " +
            " CASE WHEN tb1.USE_ATR = 1 THEN tb1.TIME_ITEM_NAME " +
            " ELSE NULL " +
            " END TIME_ITEM_NAME, " +
            " tb2.USE_ATR AS USE_ATR1,  " +
            " CASE WHEN tb2.USE_ATR = 1 THEN tb2.TIME_ITEM_NAME " +
            " ELSE NULL " +
            " END TIME_ITEM_NAME1 " +
            " FROM  " +
            "   ( " +
            "      SELECT TIME_ITEM_NO, USE_ATR, TIME_ITEM_NAME  " +
            "      FROM KBPST_BP_TIME_ITEM t1  " +
            "      WHERE CID = ? AND TYPE_ATR = 0 " +
            "   ) tb1  " +
            " INNER JOIN  " +
            "   ( " +
            "   SELECT TIME_ITEM_NO, USE_ATR, TIME_ITEM_NAME  " +
            "   FROM KBPST_BP_TIME_ITEM t1  " +
            "   WHERE CID = ? AND TYPE_ATR = 1 " +
            "   )  tb2  " +
            " ON tb1.TIME_ITEM_NO = tb2.TIME_ITEM_NO " +
            " ORDER BY tb1.TIME_ITEM_NO ";

    private static final String SQLAutoCalSetting = "SELECT " +
            "   tb1.TIME_ITEM_NAME, " +
            "   tb1.HOLIDAY_TIMESHEET_ATR, " +
            "   tb1.OVERTIME_TIMESHEET_ATR, " +
            "   tb1.WORKING_TIMESHEET_ATR, " +
            "   tb2.TIME_ITEM_NAME AS TIME_ITEM_NAME_TAB2, " +
            "   tb2.HOLIDAY_TIMESHEET_ATR AS HOLIDAY_TIMESHEET_ATR_TAB2, " +
            "   tb2.OVERTIME_TIMESHEET_ATR AS OVERTIME_TIMESHEET_ATR_TAB2, " +
            "   tb2.WORKING_TIMESHEET_ATR AS WORKING_TIMESHEET_ATR_TAB2  " +
            "FROM " +
            "   ( " +
            "   SELECT " +
            "      t1.TIME_ITEM_NO, " +
            "      t1.TIME_ITEM_NAME, " +
            "      t2.HOLIDAY_TIMESHEET_ATR, " +
            "      t2.OVERTIME_TIMESHEET_ATR, " +
            "      t2.WORKING_TIMESHEET_ATR, " +
            "      ROW_NUMBER () OVER (ORDER BY t1.TIME_ITEM_NO) AS ROW_NUMBER_TAB1 " +
            "   FROM " +
            "      ( " +
            "         SELECT *  " +
            "         FROM KBPST_BP_TIME_ITEM  " +
            "         WHERE CID = ? AND TYPE_ATR = 0 AND USE_ATR = 1) t1 " +
            "         INNER JOIN  " +
            "            (  " +
            "            SELECT *  " +
            "            FROM KBPST_BP_TIME_ITEM_SET  " +
            "            WHERE CID = ? AND TYPE_ATR = 0  " +
            "            ) t2 ON t1.TIME_ITEM_NO = t2.TIME_ITEM_NO  " +
            "      ) tb1 " +
            "FULL JOIN  " +
            "   ( " +
            "   SELECT " +
            "      t1.TIME_ITEM_NO, " +
            "      t1.TIME_ITEM_NAME, " +
            "      t2.HOLIDAY_TIMESHEET_ATR, " +
            "      t2.OVERTIME_TIMESHEET_ATR, " +
            "      t2.WORKING_TIMESHEET_ATR , " +
            "      ROW_NUMBER () OVER (ORDER BY t1.TIME_ITEM_NO) AS ROW_NUMBER_TAB2 " +
            "   FROM " +
            "      ( " +
            "         SELECT *  " +
            "         FROM KBPST_BP_TIME_ITEM  " +
            "         WHERE CID = ? AND TYPE_ATR = 1 AND USE_ATR = 1  " +
            "      ) t1 " +
            "      INNER JOIN  " +
            "      (  " +
            "         SELECT *  " +
            "         FROM KBPST_BP_TIME_ITEM_SET  " +
            "         WHERE CID = ? AND TYPE_ATR = 1  " +
            "      ) t2 ON t1.TIME_ITEM_NO = t2.TIME_ITEM_NO  " +
            "   ) tb2 ON tb1.ROW_NUMBER_TAB1 = tb2.ROW_NUMBER_TAB2 ";

    private static final String SQLDetailSettingTimeZone = "SELECT " +
            " CASE WHEN ROW_NUMBER = 1 THEN BONUS_PAY_SET_CD  " +
            " ELSE NULL  " +
            " END BONUS_CD, " +
            " CASE WHEN ROW_NUMBER = 1 THEN BONUS_PAY_SET_NAME  " +
            " ELSE NULL  " +
            " END BONUS_NAME, " +
            " BONUS_PAY_TIMESHEET_NO, " +
            " USE_ATR, " +
            " CASE WHEN USE_ATR = 1 THEN START_TIME " +
            " ELSE NULL " +
            " END START_TIME, " +
            " CASE WHEN USE_ATR = 1 THEN END_TIME " +
            " ELSE NULL " +
            " END END_TIME, " +
            " CASE WHEN USE_ATR = 1 THEN UNIT " +
            " ELSE NULL " +
            " END UNIT, " +
            " CASE WHEN USE_ATR = 1 THEN ROUNDING " +
            " ELSE NULL " +
            " END ROUNDING, " +
            " CASE WHEN USE_ATR = 1 AND TIME_USE_ATR_TAB1 = 1 THEN TIME_ITEM_ID " +
            " ELSE NULL " +
            " END TIME_ITEM_ID, " +
            " CASE WHEN USE_ATR = 1 AND TIME_USE_ATR_TAB1 = 1 THEN TIME_ITEM_NAME " +
            " ELSE NULL " +
            " END TIME_ITEM_NAME, " +
            " USE_ATR_TAB2,  " +
            " CASE WHEN USE_ATR_TAB2 = 1 AND DATE_USE_ATR = 1 THEN SPECIAL_DATE_ITEM_NO " +
            " ELSE NULL " +
            " END SPECIAL_DATE_ITEM_NO, " +
            " CASE WHEN USE_ATR_TAB2 = 1 AND DATE_USE_ATR = 1 THEN NAME " +
            " ELSE NULL " +
            " END NAME, " +
            " CASE WHEN USE_ATR_TAB2 = 1 THEN START_TIME_TAB2 " +
            " ELSE NULL " +
            " END START_TIME_TAB2, " +
            " CASE WHEN USE_ATR_TAB2 = 1 THEN END_TIME_TAB2 " +
            " ELSE NULL " +
            " END END_TIME_TAB2, " +
            " CASE WHEN USE_ATR_TAB2 = 1 THEN UNIT_TAB2 " +
            " ELSE NULL " +
            " END UNIT_TAB2, " +
            " CASE WHEN USE_ATR_TAB2 = 1 THEN ROUNDING_TAB2 " +
            " ELSE NULL " +
            " END ROUNDING_TAB2, " +
            " CASE WHEN USE_ATR_TAB2 = 1 AND TIME_USE_ATR_TAB2 = 1 THEN TIME_ITEM_ID_TAB2 " +
            " ELSE NULL " +
            " END TIME_ITEM_ID_TAB2, " +
            " CASE WHEN USE_ATR_TAB2 = 1 AND TIME_USE_ATR_TAB2 = 1 THEN TIME_ITEM_NAME_TAB2 " +
            " ELSE NULL " +
            " END TIME_ITEM_NAME_TAB2 " +
            "FROM " +
            "  ( " +
            "    SELECT " +
            "    tb1.*, " +
            "    tb2.TIME_ITEM_NAME, tb2.USE_ATR AS TIME_USE_ATR_TAB1," +
            "    ROW_NUMBER () OVER ( PARTITION BY BONUS_PAY_SET_CD ORDER BY BONUS_PAY_SET_CD, BONUS_PAY_TIMESHEET_NO ) AS     ROW_NUMBER  " +
            "    FROM " +
            "    ( " +
            "      SELECT " +
            "        *  " +
            "      FROM " +
            "        (  " +
            "        SELECT CID, BONUS_PAY_SET_CD, BONUS_PAY_SET_NAME  " +
            "        FROM KBPMT_BONUS_PAY_SET  " +
            "        WHERE CID = ?  " +
            "        ) t1 " +
            "      LEFT JOIN  " +
            "        (  " +
            "        SELECT BONUS_PAY_SET_CD AS BONUS_PAY_SET_CD1, USE_ATR, TIME_ITEM_ID, START_TIME, END_TIME, UNIT,               ROUNDING, BONUS_PAY_TIMESHEET_NO  " +
            "        FROM KBPMT_BP_TIMESHEET  " +
            "        WHERE CID = ?  " +
            "        ) t2 ON t1.BONUS_PAY_SET_CD = t2.BONUS_PAY_SET_CD1  " +
            "    ) tb1 " +
            "    LEFT JOIN KBPST_BP_TIME_ITEM tb2 ON tb2.TIME_ITEM_NO = tb1.TIME_ITEM_ID  " +
            "    AND tb1.CID = tb2.CID  " +
            "  WHERE " +
            "    tb2.TYPE_ATR = 0  " +
            "    OR tb2.TYPE_ATR IS NULL  " +
            "  ) temp1 " +
            "  LEFT JOIN  " +
            "  ( " +
            "    SELECT " +
            "      tb1.*, tb2.USE_ATR AS TIME_USE_ATR_TAB2," +
            "      tb2.TIME_ITEM_NAME AS TIME_ITEM_NAME_TAB2  " +
            "    FROM " +
            "    ( " +
            "      SELECT " +
            "        tb1.*, " +
            "        tb2.*, i.USE_ATR AS DATE_USE_ATR," +
            "        i.NAME  " +
            "      FROM " +
            "        (  " +
            "        SELECT CID, BONUS_PAY_SET_CD AS BONUS_PAY_SET_CD_TAB2, BONUS_PAY_SET_NAME AS BONUS_PAY_SET_NAME_TAB2         FROM KBPMT_BONUS_PAY_SET  " +
            "        WHERE CID = ?  " +
            "        ) tb1 " +
            "      LEFT JOIN  " +
            "      ( " +
            "        SELECT " +
            "          BONUS_PAY_SET_CD AS BONUS_PAY_SET_CD1, " +
            "          SPECIAL_DATE_ITEM_NO, " +
            "          USE_ATR AS USE_ATR_TAB2, " +
            "          TIME_ITEM_ID AS TIME_ITEM_ID_TAB2, " +
            "          START_TIME AS START_TIME_TAB2, " +
            "          END_TIME AS END_TIME_TAB2, " +
            "          UNIT AS UNIT_TAB2, " +
            "          ROUNDING AS ROUNDING_TAB2, " +
            "          BONUS_PAY_TIMESHEET_NO AS BONUS_PAY_TIMESHEET_NO_TAB2  " +
            "        FROM " +
            "          KBPMT_BP_TIMESHEET_SPEC  " +
            "        WHERE " +
            "          CID = ?  " +
            "      ) tb2 ON tb1.BONUS_PAY_SET_CD_TAB2 = tb2.BONUS_PAY_SET_CD1 " +
            "      LEFT JOIN  " +
            "      KSMST_SPECIFIC_DATE_ITEM i ON tb2.SPECIAL_DATE_ITEM_NO = i.SPECIFIC_DATE_ITEM_NO  " +
            "      AND tb1.CID = i.CID  " +
            "    ) tb1 " +
            "    LEFT JOIN KBPST_BP_TIME_ITEM tb2 ON tb2.TIME_ITEM_NO = tb1.TIME_ITEM_ID_TAB2  " +
            "    AND tb1.CID = tb2.CID  " +
            "  WHERE " +
            "    tb2.TYPE_ATR = 1  " +
            "    OR tb2.TYPE_ATR IS NULL  " +
            "  ) temp2 ON temp1.BONUS_PAY_SET_CD = temp2.BONUS_PAY_SET_CD_TAB2 AND temp1.BONUS_PAY_TIMESHEET_NO = temp2.BONUS_PAY_TIMESHEET_NO_TAB2  " +
            " ORDER BY " +
            "  BONUS_PAY_SET_CD, " +
            "  BONUS_PAY_TIMESHEET_NO_TAB2";

    /*private static final String SQLSetSubUseWorkPlace = "SELECT  " +
            "  wp.WKPCD,  " +
            "  wp.WKP_NAME,  " +
            "  s.BONUS_PAY_SET_CD,  " +
            "  s.BONUS_PAY_SET_NAME,  " +
            "  wph.END_DATE " +
            " FROM " +
            " BSYMT_WKP_CONFIG_INFO i " +
            " INNER JOIN KBPST_WP_BP_SET wps " +
            " ON i.CID = wps.CID AND i.WKPID = wps.WKPID " +
            " INNER JOIN BSYMT_WORKPLACE_INFO wp " +
            " ON wp.CID = wps.CID AND wp.WKPID = wps.WKPID " +
            "  INNER JOIN   " +
            "  (  " +
            "    SELECT H.CID, H.HIST_ID, H.WKPID, H.START_DATE, H.END_DATE  " +
            "    FROM BSYMT_WORKPLACE_HIST H  " +
            "    INNER JOIN   " +
            "    (  " +
            "    SELECT CID, WKPID, MAX(END_DATE) AS END_DATE  " +
            "    FROM BSYMT_WORKPLACE_HIST   " +
            "    GROUP BY CID, WKPID  " +
            "    ) M ON H.CID = M.CID AND H.WKPID = M.WKPID AND H.END_DATE = M.END_DATE  " +
            "  )   " +
            "  wph ON wp.CID = wph.CID   " +
            "  AND wp.HIST_ID = wph.HIST_ID   " +
            "  AND wp.WKPID = wph.WKPID  " +
            "  INNER JOIN KBPMT_BONUS_PAY_SET s ON s.CID = wp.CID   " +
            "  AND s.BONUS_PAY_SET_CD = wps.BONUS_PAY_SET_CD   " +
            "  WHERE  " +
            "  wp.CID = ?  " +
            " ORDER BY i.HIERARCHY_CD"; */

    private static final String SQLSetSubUseWorkPlace = "SELECT  " +
            "  temp1.*,  " +
            "  wci.HIERARCHY_CD,  " +
            "  bps.BONUS_PAY_SET_CD,  " +
            "  bps.BONUS_PAY_SET_NAME   " +
            "FROM  " +
            "  (  " +
            "  SELECT  " +
            "    wi.CID,  " +
            "    wi.WKPCD,  " +
            "    wi.WKP_NAME,  " +
            "    wh.START_DATE,  " +
            "    wh.END_DATE,  " +
            "    wi.HIST_ID,  " +
            "    wi.WKPID,  " +
            "    wc.HIST_ID AS HIST_ID_CONFIG,  " +
            "    wbs.BONUS_PAY_SET_CD   " +
            "  FROM  " +
            "    KBPST_WP_BP_SET wbs  " +
            "    INNER JOIN BSYMT_WORKPLACE_INFO wi ON wbs.WKPID = wi.WKPID  " +
            "    INNER JOIN BSYMT_WORKPLACE_HIST wh ON wi.HIST_ID = wh.HIST_ID   " +
            "    AND wi.WKPID = wh.WKPID   " +
            "    AND wi.CID = wh.CID   " +
            "    AND wh.END_DATE = ( SELECT MAX ( END_DATE ) FROM BSYMT_WORKPLACE_HIST wht WHERE wi.CID = wht.CID AND wi.WKPID = wht.WKPID )  " +
            "    INNER JOIN BSYMT_WKP_CONFIG wc ON wc.CID = wi.CID   " +
            "    AND wc.END_DATE = '9999-12-31 00:00:00'   " +
            "  WHERE  " +
            "    wbs.CID = ?   " +
            "  ) temp1  " +
            "  INNER JOIN BSYMT_WKP_CONFIG_INFO wci ON wci.WKPID = temp1.WKPID   " +
            "  AND wci.CID = temp1.CID  " +
            "  INNER JOIN KBPMT_BONUS_PAY_SET bps ON bps.BONUS_PAY_SET_CD = temp1.BONUS_PAY_SET_CD   " +
            "WHERE  " +
            "  wci.CID = ?   " +
            "  AND bps.CID = ?   " +
            "  AND temp1.HIST_ID_CONFIG = wci.HIST_ID   " +
            "ORDER BY  " +
            "  wci.HIERARCHY_CD";

    private static final String SQLSetEmployees = "SELECT   " +
            "  emp.SCD,   " +
            "  p.BUSINESS_NAME,   " +
            "  ps.BONUS_PAY_SET_CD,   " +
            "  ps.BONUS_PAY_SET_NAME   " +
            "FROM   " +
            "  KBPST_PS_BP_SET s   " +
            "  INNER JOIN BSYMT_EMP_DTA_MNG_INFO emp ON s.SID = emp.SID   " +
            "  INNER JOIN BPSMT_PERSON p ON p.PID = emp.PID    " +
            "  INNER JOIN KBPMT_BONUS_PAY_SET ps ON ps.BONUS_PAY_SET_CD = s.BONUS_PAY_SET_CD   " +
            "WHERE   " +
            "  emp.CID = ? AND ps.CID = ?" +
            "  ORDER BY SCD";

    private static final String SQLSetUsedWorkingHours = "SELECT    " +
            "   s.WORKING_CD,    " +
            "   w.NAME,    " +
            "   ps.BONUS_PAY_SET_CD,    " +
            "   ps.BONUS_PAY_SET_NAME     " +
            "FROM    " +
            "   KBPST_WT_BP_SET s    " +
            "   INNER JOIN KSHMT_WORK_TIME_SET w ON s.CID = w.CID     " +
            "   AND s.WORKING_CD = w.WORKTIME_CD    " +
            "   INNER JOIN KBPMT_BONUS_PAY_SET ps ON ps.BONUS_PAY_SET_CD = s.BONUS_PAY_SET_CD     " +
            "WHERE    " +
            "   w.CID = ?     " +
            "   AND ps.CID = ?    " +
            "ORDER BY s.WORKING_CD";

    private static final String SQLSetUpUseCompany = "SELECT " +
            "  ps.BONUS_PAY_SET_CD, " +
            "  ps.BONUS_PAY_SET_NAME  " +
            "FROM " +
            "  KBPST_CP_BP_SET s " +
            "  INNER JOIN KBPMT_BONUS_PAY_SET ps ON s.BONUS_PAY_SET_CD = ps.BONUS_PAY_SET_CD  " +
            "WHERE " +
            "  s.CID = ?  " +
            "  AND ps.CID = ?";

    @SneakyThrows
    @Override
    public List<MasterData> getListSpecialBonusPayTimeItem(String companyId) {
        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLSpecialBonusPayTimeItem)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSpecialBonusPayTimeItem(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getAutoCalSetting(String companyId) {
        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLAutoCalSetting)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            stmt.setString(3,companyId);
            stmt.setString(4,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfAutoCalSetting(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getDetailSettingTimeZone(String companyId) {
        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLDetailSettingTimeZone)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            stmt.setString(3,companyId);
            stmt.setString(4,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSettingTimeZone(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getInfoSetSubUseWorkPlace(String companyId) {
        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLSetSubUseWorkPlace)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            stmt.setString(3,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSetSubUseWorkPlace(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getInfoSetEmployees(String companyId) {
        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLSetEmployees)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSetEmployees(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getInfoSetUsedWorkingHours(String companyId) {

        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLSetUsedWorkingHours)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSetUsedWorkingHours(x));
        }
        return datas;
    }

    @SneakyThrows
    @Override
    public List<MasterData> getInfoSetUpUseCompany(String companyId) {

        List<MasterData> datas = new ArrayList<>();
        try(PreparedStatement stmt = this.connection().prepareStatement(SQLSetUpUseCompany)){
            stmt.setString(1,companyId);
            stmt.setString(2,companyId);
            datas = new NtsResultSet(stmt.executeQuery()).getList(x -> toMasterDataOfSetUpUseCompany(x));
        }
        return datas;
    }


    private MasterData toMasterDataOfSpecialBonusPayTimeItem(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_135, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_135)
                .value(rs.getBigDecimal("TIME_ITEM_NO"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_96, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_96)
                .value(rs.getInt("USE_ATR") == UseAtr.USE.value ? TextResource.localize("Enum_UseAtr_Use") : TextResource.localize("Enum_UseAtr_NotUse"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_97, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_97)
                .value(rs.getString("TIME_ITEM_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_98, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_98)
                .value(rs.getInt("USE_ATR1") == UseAtr.USE.value ? TextResource.localize("Enum_UseAtr_Use") : TextResource.localize("Enum_UseAtr_NotUse"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_99, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_99)
                .value(rs.getString("TIME_ITEM_NAME1"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfSettingTimeZone(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_106, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_106)
                .value(rs.getString("BONUS_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_107, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_107)
                .value(rs.getString("BONUS_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_135, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_135)
                .value(rs.getInt("BONUS_PAY_TIMESHEET_NO"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_108, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_108)
                .value(rs.getInt("USE_ATR") == null ? null : rs.getInt("USE_ATR") == UseAtr.USE.value ? TextResource.localize("Enum_UseAtr_Use") : TextResource.localize("Enum_UseAtr_NotUse"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_109, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_109)
                .value(rs.getInt("START_TIME") == null ? null : this.convertToTime(rs.getInt("START_TIME")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_110, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_110)
                .value(rs.getInt("END_TIME") == null ? null : this.convertToTime(rs.getInt("END_TIME")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_111, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_111)
                .value(rs.getString("TIME_ITEM_NAME") == null && rs.getInt("TIME_ITEM_ID" ) == null ? null : rs.getInt("TIME_ITEM_ID" ) + rs.getString("TIME_ITEM_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());


        data.put(SettingTimeZoneUtils.KMK005_112, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_112)
                .value(rs.getInt("UNIT") == null ? null : EnumAdaptor.valueOf(rs.getInt("UNIT"),Unit.class).nameId)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_113, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_113)
                .value(rs.getInt("ROUNDING") == null ? null : rs.getInt("ROUNDING") == 0 ? TextResource.localize("Enum_Rounding_Down") : TextResource.localize("Enum_Rounding_Up"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        //tab2

        data.put(SettingTimeZoneUtils.KMK005_114, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_114)
                .value(rs.getInt("USE_ATR_TAB2") == null ? null : rs.getInt("USE_ATR_TAB2") == UseAtr.USE.value ? TextResource.localize("Enum_UseAtr_Use") : TextResource.localize("Enum_UseAtr_NotUse"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_115, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_115)
                .value(rs.getInt("SPECIAL_DATE_ITEM_NO") == null && rs.getString("NAME") == null ? null : rs.getInt("SPECIAL_DATE_ITEM_NO") + rs.getString("NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_116, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_116)
                .value(rs.getInt("START_TIME_TAB2") == null ? null : this.convertToTime(rs.getInt("START_TIME_TAB2")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_117, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_117)
                .value(rs.getInt("END_TIME_TAB2") == null ? null : this.convertToTime(rs.getInt("END_TIME_TAB2")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_118, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_118)
                .value(rs.getInt("TIME_ITEM_ID_TAB2") == null && rs.getString("TIME_ITEM_NAME_TAB2") == null ? null : rs.getInt("TIME_ITEM_ID_TAB2") + rs.getString("TIME_ITEM_NAME_TAB2"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_119, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_119)
                .value(rs.getInt("UNIT_TAB2") == null ? null : EnumAdaptor.valueOf(rs.getInt("UNIT_TAB2"),Unit.class).nameId)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());


        data.put(SettingTimeZoneUtils.KMK005_120, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_120)
                .value(rs.getInt("ROUNDING_TAB2") == null ? null : rs.getInt("ROUNDING_TAB2") == 0 ? TextResource.localize("Enum_Rounding_Down") : TextResource.localize("Enum_Rounding_Up"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfAutoCalSetting(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_97, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_97)
                .value(rs.getString("TIME_ITEM_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_100, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_100)
                .value(rs.getInt("WORKING_TIMESHEET_ATR") == null ? null : rs.getInt("WORKING_TIMESHEET_ATR") == 0 ? TextResource.localize("KMK005_55") : TextResource.localize("KMK005_54"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_101, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_101)
                .value(rs.getInt("OVERTIME_TIMESHEET_ATR") == null ? null : rs.getInt("OVERTIME_TIMESHEET_ATR") == 0 ? TextResource.localize("KMK005_55") : rs.getInt("OVERTIME_TIMESHEET_ATR") == 1 ? TextResource.localize("KMK005_54") : TextResource.localize("KMK005_56"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_102, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_102)
                .value(rs.getInt("HOLIDAY_TIMESHEET_ATR") == null ? null : rs.getInt("HOLIDAY_TIMESHEET_ATR") == 0 ? TextResource.localize("KMK005_55") : rs.getInt("HOLIDAY_TIMESHEET_ATR") == 1 ? TextResource.localize("KMK005_54") : TextResource.localize("KMK005_57"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_99, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_99)
                .value(rs.getString("TIME_ITEM_NAME_TAB2"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(SettingTimeZoneUtils.KMK005_103, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_103)
                .value(rs.getInt("WORKING_TIMESHEET_ATR_TAB2") == null ? null : rs.getInt("WORKING_TIMESHEET_ATR_TAB2") == 0 ? TextResource.localize("KMK005_55") : TextResource.localize("KMK005_54"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_104, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_104)
                .value(rs.getInt("OVERTIME_TIMESHEET_ATR_TAB2") == null ? null : rs.getInt("OVERTIME_TIMESHEET_ATR_TAB2") == 0 ? TextResource.localize("KMK005_55") : rs.getInt("OVERTIME_TIMESHEET_ATR_TAB2") == 1 ? TextResource.localize("KMK005_54") : TextResource.localize("KMK005_56"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_105, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_105)
                .value(rs.getInt("HOLIDAY_TIMESHEET_ATR_TAB2") == null ? null : rs.getInt("HOLIDAY_TIMESHEET_ATR_TAB2") == 0 ? TextResource.localize("KMK005_55") : rs.getInt("HOLIDAY_TIMESHEET_ATR_TAB2") == 1 ? TextResource.localize("KMK005_54") : TextResource.localize("KMK005_57"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfSetSubUseWorkPlace(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_121, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_121)
                .value(rs.getString("WKPCD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_122, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_122)
                .value(rs.getDate("END_DATE").toString().equals("9999-12-31") ? rs.getString("WKP_NAME") : "マスタ未登録")
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_106, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_106)
                .value(rs.getString("BONUS_PAY_SET_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_107, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_107)
                .value(rs.getString("BONUS_PAY_SET_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfSetEmployees(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_123, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_123)
                .value(rs.getString("SCD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_124, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_124)
                .value(rs.getString("BUSINESS_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_106, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_106)
                .value(rs.getString("BONUS_PAY_SET_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_107, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_107)
                .value(rs.getString("BONUS_PAY_SET_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfSetUsedWorkingHours(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_125, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_125)
                .value(rs.getString("WORKING_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_126, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_126)
                .value(rs.getString("NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_106, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_106)
                .value(rs.getString("BONUS_PAY_SET_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_107, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_107)
                .value(rs.getString("BONUS_PAY_SET_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private MasterData toMasterDataOfSetUpUseCompany(NtsResultSet.NtsResultRecord rs){
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(SettingTimeZoneUtils.KMK005_106, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_106)
                .value(rs.getString("BONUS_PAY_SET_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(SettingTimeZoneUtils.KMK005_107, MasterCellData.builder()
                .columnId(SettingTimeZoneUtils.KMK005_107)
                .value(rs.getString("BONUS_PAY_SET_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        return MasterData.builder().rowData(data).build();
    }

    private String convertToTime(int param){
        int m = param / 60;
        int s = param % 60;

        return String.format("%d:%02d", m, s);

    }
}
