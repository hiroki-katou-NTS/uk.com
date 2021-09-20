package nts.uk.file.at.app.export.schedule.personalschedulebydate;

import lombok.val;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.HolidayName;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
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

        // Dummy data
//        EmployeeWorkScheduleResultDto ob = new EmployeeWorkScheduleResultDto(
//                GeneralDate.fromString("2021/09/02", "yyyy/MM/dd"),
//                "empId01",
//                Arrays.asList(
//                        new BreakTimeSheet(
//                                new BreakFrameNo(1),
//                                new TimeWithDayAttr(710),
//                                new TimeWithDayAttr(780),
//                                new AttendanceTime(8)
//                        ),
//                        new BreakTimeSheet(
//                                new BreakFrameNo(2),
//                                new TimeWithDayAttr(1000),
//                                new TimeWithDayAttr(1080),
//                                new AttendanceTime(9)
//                        )
//                ),
//                Arrays.asList(
//                        new BreakTimeSheet(
//                                new BreakFrameNo(1),
//                                new TimeWithDayAttr(510),
//                                new TimeWithDayAttr(540),
//                                new AttendanceTime(8)
//                        ),
//                        new BreakTimeSheet(
//                                new BreakFrameNo(2),
//                                new TimeWithDayAttr(910),
//                                new TimeWithDayAttr(930),
//                                new AttendanceTime(9)
//                        )
//                ),
//                Arrays.asList(
//                        new ChangeableWorkTimeDto(1, 330, 510),
//                        new ChangeableWorkTimeDto(2, 1050, 1320)
//                ),
//                Arrays.asList(
//                        new TimeShortDto(
//                                380,
//                                480,
//                                1,
//                                1
//                        )
//                ),
//                Collections.emptyList(),
//                533,
//                886,  //886
//                220,
//                1,
//                "ty1",
//                "type1",
//                360,
//                1230,
//                null,
//                null,
//                500,
//                "ti1",
//                "time1",
//                360,  //480
//                1230,  //1050
//                null,
//                null
//        );
//        List<EmployeeWorkScheduleResultDto> lstData = new ArrayList<>();
//        for (int i = 1; i < 92; i++) { //31
//            lstData.add(ob);
//        }
//
//        PersonalScheduleByDateDataSource dataSource = new PersonalScheduleByDateDataSource(
//                new CompanyInfor("01", "3S Intersoft"),
//                new DisplayInfoOrganization("", "000001", "org", "disp_name", ""),
//                new DateInformation(
//                        GeneralDate.fromString("2021/09/02", "yyyy/MM/dd"),
//                        DayOfWeek.MONDAY,
//                        true,
//                        Optional.of(new HolidayName("2/9")),
//                        true,
//                        Optional.of(new EventName("wkp_event")),
//                        Optional.of(new EventName("com_event")),
//                        Arrays.asList(
//                                new SpecificName("wkp_spe1"),
//                                new SpecificName("wkp_spe2"),
//                                new SpecificName("wkp_spe3")
//                        ),
//                        Arrays.asList(
//                                new SpecificName("com_spe1"),
//                                new SpecificName("com_spe2"),
//                                new SpecificName("com_spe3")
//                        )
//                ),
//                Arrays.asList(
//                        new EmployeeInformationImport(
//                                "empId01",
//                                "empCd01",
//                                "empName1",
//                                null, null, null, null, null, null
//                        ),
//                        new EmployeeInformationImport(
//                                "empId02",
//                                "empCd02",
//                                "empName2",
//                                null, null, null, null, null, null
//                        )
//                ),
//                lstData,
//                new PersonalScheduleByDateQuery(
//                        1,
//                        "1",
//                        "2021/09/02",
//                        Arrays.asList("empId01", "empId02"),
//                        5,
//                        true,
//                        true,
//                        true,
//                        false,
//                        true,
//                        true,
//                        true,
//                        true
//                )
//        );

        // 1.2. create report
        this.exportGenerator.generate(exportServiceContext.getGeneratorContext(), dataSource);
    }
}
