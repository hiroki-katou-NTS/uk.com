package nts.uk.ctx.at.function.dom.supportworklist;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.AffiliationInforOfDailyPerforImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeSheetOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class SupportWorkDataImportHelper {
    public static SupportWorkDataImport createDataImport(String employeeId, String workplaceId1, String workLocationCode1, String workplaceId2, String workLocationCode2) {
        return new SupportWorkDataImport(
                Arrays.asList(new OuenWorkTimeOfDailyImport(
                        employeeId,
                        GeneralDate.today(),
                        new ArrayList<>()
                )),
                Arrays.asList(new OuenWorkTimeSheetOfDailyImport(
                        employeeId,
                        GeneralDate.today(),
                        Arrays.asList(new OuenWorkTimeSheetOfDailyAttendance(
                                new SupportFrameNo(1),
                                SupportType.TIMEZONE,
                                WorkContent.create(
                                        WorkplaceOfWorkEachOuen.create(
                                                new WorkplaceId(workplaceId1),
                                                new WorkLocationCD(workLocationCode1)
                                        ),
                                        Optional.empty(),
                                        Optional.empty()
                                ),
                                null,
                                Optional.empty()
                        ))
                )),
                Arrays.asList(new AffiliationInforOfDailyPerforImport(
                        employeeId,
                        GeneralDate.today(),
                        new AffiliationInforOfDailyAttd(
                                null, // employmentCode
                                null, // jobTitleId
                                workplaceId2, // workplaceId
                                null, // classificationCode
                                Optional.empty(), // businessTypeCode
                                Optional.empty(), //bonusPaySettingCode
                                Optional.empty(), //workplaceGroupId
                                Optional.empty(), //nursingLicenseClass
                                Optional.empty() //isNursingManager
                        )
                ))
        );
    }
}
