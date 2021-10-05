package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.HolidayName;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
import nts.uk.file.at.app.export.schedule.personalschedulebydate.dto.EmployeeWorkScheduleResultDto;
import nts.uk.screen.at.app.ksu003.start.dto.ChangeableWorkTimeDto;
import nts.uk.screen.at.app.ksu003.start.dto.TimeShortDto;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;

/**
 * 個人スケジュール表(日付別)を出力する
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU003_個人スケジュール修正(日付別).D：出力の設定.メニュー別OCD.個人スケジュール表(日付別)印刷処理
 */
@Stateless
public class PersonalScheduleByDateExportService extends ExportService<PersonalScheduleByDateQuery> {

    @Inject
    private CreatePersonalScheduleByDateFileQuery exportQuery;

    @Inject
    private PersonalScheduleByDateExportGenerator exportGenerator;

    @Override
    protected void handle(ExportServiceContext<PersonalScheduleByDateQuery> exportServiceContext) {
        val query = exportServiceContext.getQuery();

        // 1.1. 取得する(Input.対象組織,Input. 並び順社員リスト, Input.年月日, Input.実績も取得するか)
        PersonalScheduleByDateDataSource dataSource = this.exportQuery.get(
                query.getOrgUnit(),
                query.getOrgId(),
                GeneralDate.fromString(query.getBaseDate(), "yyyy/MM/dd"),
                query.getSortedEmployeeIds(),
                query.isDisplayActual(),
                query.isGraphVacationDisplay(),
                query.isDoubleWorkDisplay()
        );
        dataSource.setQuery(query);

        // 1.2. create report
        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
