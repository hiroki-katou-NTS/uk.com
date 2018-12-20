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
            +" SELECT T1.PATTERN_CD,T1.PATTERN_NAME,T3.NAME,T4.NAME AS NAMET4,T2.WORKING_CD,T2.DAYS,T2.WORK_TYPE_CD, "
            +" ROW_NUMBER() OVER (PARTITION BY T1.PATTERN_CD ORDER BY T1.PATTERN_CD, T1.PATTERN_NAME) AS ROW_NUMBER "
            +" FROM KSCMT_DAILY_PATTERN_SET T1 "
            +" INNER JOIN KSCMT_DAILY_PATTERN_VAL T2 ON T1.PATTERN_CD = T2.PATTERN_CD AND T2.CID = T1.CID  "
            +" INNER JOIN KSHMT_WORKTYPE T3 ON T2.WORK_TYPE_CD = T3.CD AND T3.CID = T1.CID"
            +" INNER JOIN KSHMT_WORK_TIME_SET T4 ON T2.WORKING_CD = T4.WORKTIME_CD AND T4.CID = T1.CID "
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
                .value( r.getString("WORK_TYPE_CD")+r.getString("NAME"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        data.put(DailyPatternExportImpl.KSM003_41, MasterCellData.builder()
                .columnId(DailyPatternExportImpl.KSM003_41)
                .value( r.getString("WORKING_CD")+r.getString("NAMET4"))
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