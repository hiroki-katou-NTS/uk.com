package nts.uk.ctx.at.function.dom.supportworklist;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.amount.AttendanceAmountDaily;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.List;
import java.util.Optional;

public class SupportWorkDetailsHelper {
    public static SupportWorkDetails createDetailData(String employeeId, GeneralDate date, List<Integer> attendanceItemIds) {
        return SupportWorkDetails.create(
                employeeId,
                date,
                "affiliationInfo",
                "workInfo",
                attendanceItemIds,
                new OuenWorkTimeSheetOfDailyAttendance(
                        new SupportFrameNo(1),
                        WorkContent.create(
                                WorkplaceOfWorkEachOuen.create(
                                        new WorkplaceId("workplace-id-00001"),
                                        new WorkLocationCD("0001")
                                ),
                                Optional.of(WorkGroup.create(
                                        "WKCD1",
                                        "WKCD2",
                                        "WKCD3",
                                        "WKCD4",
                                        "WKCD5"
                                )),
                                Optional.empty()
                        ),
                        TimeSheetOfAttendanceEachOuenSheet.create(
                                new WorkNo(1),
                                Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(100))),
                                Optional.of(new WorkTimeInformation(null, new TimeWithDayAttr(200)))
                        ),
                        Optional.empty()
                ),
                OuenWorkTimeOfDailyAttendance.create(
                        new SupportFrameNo(1),
                        OuenAttendanceTimeEachTimeSheet.create(
                                new AttendanceTime(300),
                                new AttendanceTime(400),
                                new AttendanceTime(500),
                                null,
                                null,
                                PremiumTimeOfDailyPerformance.createEmpty()
                        ),
                        OuenMovementTimeEachTimeSheet.create(
                                new AttendanceTime(600),
                                new AttendanceTime(700),
                                new AttendanceTime(800),
                                null
                        ),
                        new AttendanceAmountDaily(900)
                )
        );
    }
}
