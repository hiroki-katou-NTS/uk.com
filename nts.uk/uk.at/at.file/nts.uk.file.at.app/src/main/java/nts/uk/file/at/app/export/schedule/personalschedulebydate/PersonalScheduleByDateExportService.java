package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Stateless
public class PersonalScheduleByDateExportService extends ExportService<PersonalScheduleByDateQuery> {
    @Inject
    private BasicInfoPersonalScheduleExportQuery basicInfoQuery;

    @Inject
    private DailyAttendanceGettingExportQuery dailyAttendanceQuery;

    @Inject
    private PersonalScheduleByDateExportQuery exportQuery;

    @Inject
    private ScheduleInformationByDateExportQuery scheduleInfoByDateQuery;

    @Inject
    private PersonalScheduleByDateExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<PersonalScheduleByDateQuery> exportServiceContext) {
        val query = exportServiceContext.getQuery();

        // 1. 出力する(対象組織識別情報, 対象期間, List(社員ID)): param (対象組織, 年月日, 並び順社員リスト)
        val basicInformation = basicInfoQuery.getInfo(
                query.getOrgUnit(),
                query.getOrgId(),
                query.getBaseDate(),
                query.getSortedEmployeeIds());

        // 2. 取得する(): param (社員リスト, 期間, 実績も取得するか)
        Map<ScheRecGettingAtr, List<IntegrationOfDaily>> dailyAttendanceMap = dailyAttendanceQuery.get(
                query.getSortedEmployeeIds(),
                new DatePeriod(query.getBaseDate(), query.getBaseDate()),
                query.isDisplayActual());

        // 3. 作成する(List<日別勤怠(Work)>, boolean, boolean)
        val daily = scheduleInfoByDateQuery.get(
                dailyAttendanceMap.get(ScheRecGettingAtr.ONLY_RECORD),
                query.isGraphVacationDisplay(),
                query.isDoubleWorkDisplay());

        // 4.
        PersonalScheduleByDateDataSource dataSource = this.exportQuery.get(
                query.getOrgUnit(),
                query.getOrgId(),
                query.getBaseDate(),
                query.getSortedEmployeeIds()
        );

        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
