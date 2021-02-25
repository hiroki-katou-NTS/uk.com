package nts.uk.file.at.infra.shift.daily;

import lombok.SneakyThrows;
import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.file.at.app.export.shift.daily.DailyPatternExRepository;
import nts.uk.file.at.app.export.shift.daily.DailyPatternExportImpl;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.Stateless;
import java.sql.PreparedStatement;
import java.util.*;

@Stateless
public class JpaDailyPatternExport extends JpaRepository implements DailyPatternExRepository {

    private static final String  GET_EXPORT_EXCEL = "SELECT "
            +"CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.PATTERN_CD"
            +" ELSE NULL "
            +" END PATTERN_CD, "
            +" CASE WHEN TABLE_RESULT.ROW_NUMBER = 1 THEN TABLE_RESULT.PATTERN_NAME "
            +" ELSE NULL"
            +" END PATTERN_NAME, "
            +" TABLE_RESULT.NAME,"
            +" TABLE_RESULT.NAMET4, "
            +" TABLE_RESULT.WORKING_CD, "
            +" TABLE_RESULT.WORK_TYPE_CD, "
            +" TABLE_RESULT.DAYS FROM("
            +" SELECT T1.CD AS PATTERN_CD,T1.NAME AS PATTERN_NAME,T3.NAME,T4.NAME AS NAMET4,T2.WKTM_CD AS WORKING_CD,T2.REPEAT_DAYS AS DAYS,T2.WKTP_CD AS WORK_TYPE_CD, "
            +" ROW_NUMBER() OVER (PARTITION BY T1.CD ORDER BY T1.CD, T1.NAME,T2.CYCLE_ORDER) AS ROW_NUMBER "
            +" FROM KSCMT_WORKING_CYCLE T1 "
            +" INNER JOIN KSCMT_WORKING_CYCLE_DTL T2 ON T1.CD = T2.CD AND T2.CID = T1.CID  "
            +" LEFT JOIN KSHMT_WORKTYPE T3 ON T2.WKTP_CD = T3.CD AND T3.CID = T1.CID"
            +" LEFT JOIN KSHMT_WORK_TIME_SET T4 ON T2.WKTM_CD = T4.WORKTIME_CD AND T4.CID = T1.CID "
            +" WHERE T1.CID = ? ) "
            +" TABLE_RESULT;";

    @SneakyThrows
    @Override
    public List<MasterData> findAllDailyPattern() {
        String companyId = AppContexts.user().companyId();
        List<MasterData> resulf = new ArrayList<MasterData>();
        try (PreparedStatement stmt = this.connection().prepareStatement(
                GET_EXPORT_EXCEL)) {
            stmt.setString(1, companyId);
            resulf = new NtsResultSet(stmt.executeQuery()).getList(x -> toData(x));
        }

        return resulf;
    }
    private MasterData toData(NtsResultSet.NtsResultRecord r)

    {
        Map<String,MasterCellData> data = new HashMap<>();
        data.put(DailyPatternExportImpl.KSM003_38, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_38)
                .value(r.getString("PATTERN_CD"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(DailyPatternExportImpl.KSM003_39, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_39)
                .value(r.getString("PATTERN_NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());

        data.put(DailyPatternExportImpl.KSM003_40, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_40)
                .value( r.getString("NAME") == null ? r.getString("WORK_TYPE_CD")+"マスタ未登録" : r.getString("WORK_TYPE_CD")+r.getString("NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(DailyPatternExportImpl.KSM003_41, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_41)
                .value(  r.getString("NAMET4") == null ?  (StringUtils.isBlank(r.getString("WORKING_CD")) ? null : r.getString("WORKING_CD")+"マスタ未登録") : (StringUtils.isBlank(r.getString("WORKING_CD")) ? null : r.getString("WORKING_CD")+r.getString("NAMET4")))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(DailyPatternExportImpl.KSM003_42, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_42)
                .value(r.getString("DAYS")+I18NText.getText("KSM003_43"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
        return MasterData.builder().rowData(data).build();
    }





}