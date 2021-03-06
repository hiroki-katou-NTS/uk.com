package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.requestlist.PrevisionalForImp;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.AffiliationInforState;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.*;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.deviationtime.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.context.AppContexts;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ???????????????????????????????????????????????????
 */
public class CreateDailyRecordFromSpecifiedElementsService {
    /**
     * [1]????????????
     * @param params
     * @return
     */
    public static Optional<IntegrationOfDaily> create(Require require, PrevisionalForImp params) {
        Optional<IntegrationOfDaily> dailyRecord = createProvisionalDailyRecord(
                require,
                params.getEmployeeId(),
                params.getTargetDate(),
                params.getWorkTypeCode(),
                params.getWorkTimeCode(),
                params.getTimeSheets()
        );
        if (!dailyRecord.isPresent()) return dailyRecord;

        IntegrationOfDaily dailyRecord2 = replaceDeductionTimeSheet(
                dailyRecord.get(),
                params.getBreakTimeSheets(),
                params.getOutingTimeSheets(),
                params.getShortWorkingTimeSheets(),
                params.getEmployeeId(),
                params.getTargetDate()
        );

        dailyRecord2.clearEditedStates();

        return Optional.of(dailyRecord2);
    }

    /**
     * ?????????????????????????????????
     */
    private static Optional<IntegrationOfDaily> createProvisionalDailyRecord(
            Require require,
            String employeeId,
            GeneralDate ymd,
            WorkTypeCode workTypeCode,
            WorkTimeCode workTimeCode,
            Map<Integer, TimeZone> timeSheets
    ) {
        // ???????????????????????????
        String setWorkTimeCode = workTimeCode == null ? null : workTimeCode.v();

        Optional<IntegrationOfDaily> domainDaily = require.findDailyRecord(employeeId, ymd);

        WorkInfoOfDailyPerformance workInformation = new WorkInfoOfDailyPerformance(
                employeeId,
                new WorkInformation(workTypeCode.v(), setWorkTimeCode),
                CalculationState.No_Calculated,
                NotUseAttribute.Not_use,
                NotUseAttribute.Not_use,
                ymd,
                new ArrayList<>(),
                Optional.empty()
        );

        // ???????????????????????????????????????
        //Optional<ShortTimeOfDailyPerformance> ShortTimeOfDailyPerformance = Optional.empty();
        // ??????????????????????????????
        // ;
        // ????????????????????????
        List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
        for (Map.Entry<Integer, TimeZone> key : timeSheets.entrySet()) {
            WorkStamp attendance = new WorkStamp(
                    key.getValue().getStart(),
                    new WorkLocationCD("01"),
                    TimeChangeMeans.AUTOMATIC_SET,
                    null
            );
            WorkStamp leaving = new WorkStamp(
                    key.getValue().getEnd(),
                    new WorkLocationCD("01"),
                    TimeChangeMeans.AUTOMATIC_SET,
                    null
            );
            TimeActualStamp attendanceStamp = new TimeActualStamp(attendance, attendance, key.getKey());
            TimeActualStamp leavingStamp = new TimeActualStamp(leaving, leaving, key.getKey());
            TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(key.getKey()), attendanceStamp, leavingStamp);

            timeLeavingWorks.add(timeLeavingWork);
        }

        TimeLeavingOfDailyPerformance timeAttendance = new TimeLeavingOfDailyPerformance(
                employeeId,
                new WorkTimes(timeSheets.size()),
                timeLeavingWorks,
                ymd
        );

        // ?????????????????????????????????
        CalAttrOfDailyPerformance calAttrOfDailyPerformance = new CalAttrOfDailyPerformance(
                employeeId,
                ymd,
                new AutoCalFlexOvertimeSetting(new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)),
                new AutoCalRaisingSalarySetting(true, true),
                new AutoCalRestTimeSetting(
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)
                ),
                new AutoCalOvertimeSetting(
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS),
                        new AutoCalSetting(TimeLimitUpperLimitSetting.NOUPPERLIMIT, AutoCalAtrOvertime.CALCULATEMBOSS)
                ),
                new AutoCalcOfLeaveEarlySetting(true, true),
                new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.USE));
        // ?????????????????????????????????
        // ??????????????????????????????????????????????????????????????????????????????????????????
        /*-----"01"????????????  --------*/
        // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
        // ??????????????????????????????????????????????????????????????????empCalAndSumExecLogID?????????????????????????????????
        AffiliationInforState employeeState = require.createAffiliationInforOfDailyPerfor(
                AppContexts.user().companyId(),
                employeeId,
                ymd,
                "01"
        );

        if (!employeeState.getAffiliationInforOfDailyPerfor().isPresent()) {
            return Optional.empty();
        }

        return Optional.of(correctAttendanceRule(
                require,
                domainDaily,
                employeeId,
                ymd,
                calAttrOfDailyPerformance,
                employeeState,
                timeAttendance.getAttendance(),
                workInformation.getWorkInformation(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        ));
    }

    //??????????????????????????????????????????
    private static IntegrationOfDaily correctAttendanceRule(
            Require require,
            Optional<IntegrationOfDaily> data,
            String employeeId, GeneralDate ymd,
            CalAttrOfDailyPerformance calAttrOfDailyPerformance,
            AffiliationInforState employeeState,
            TimeLeavingOfDailyAttd timeAttendance,
            WorkInfoOfDailyAttendance workInformation,
            Optional<BreakTimeOfDailyAttd> breakTime,
            Optional<OutingTimeOfDailyAttd> outingTime,
            Optional<ShortTimeOfDailyAttd> shortTime
    ) {
        IntegrationOfDaily domainDaily = data.orElse(createNewDomain(
                employeeId,
                ymd,
                workInformation,
                calAttrOfDailyPerformance,
                employeeState,
                timeAttendance
        ));
        boolean changeWork = setDataIntoDomain(
                domainDaily,
                timeAttendance,
                workInformation,
                calAttrOfDailyPerformance,
                employeeState,
                breakTime,
                outingTime,
                shortTime
        );
        setEditState(domainDaily);
        domainDaily = require.process(
                domainDaily,
                new ChangeDailyAttendance(
                        data.map(x -> changeWork).orElse(true),
                        false,
                        false,
                        true,
                        ScheduleRecordClassifi.RECORD,
                        false
                )
        );
        return domainDaily;
    }

    private static IntegrationOfDaily createNewDomain(
            String employeeId,
            GeneralDate ymd,
            WorkInfoOfDailyAttendance workInformation,
            CalAttrOfDailyPerformance calAttrOfDailyPerformance,
            AffiliationInforState employeeState,
            TimeLeavingOfDailyAttd attendance
    ) {
        return new IntegrationOfDaily(
                employeeId,
                ymd,
                workInformation, //workInformation
                calAttrOfDailyPerformance.getCalcategory(),//calAttr
                employeeState.getAffiliationInforOfDailyPerfor().get(), //affiliationInfor
                Optional.empty(), //pcLogOnInfo
                new ArrayList<>(), //employeeError
                Optional.empty(), //outingTime
                new BreakTimeOfDailyAttd(), //breakTime
                Optional.empty(), //attendanceTimeOfDailyPerformance
                Optional.of(attendance),// attendanceLeave
                Optional.empty(), //shortTime
                Optional.empty(), //specDateAttr
                Optional.empty(), //attendanceLeavingGate
                Optional.empty(), //anyItemValue
                new ArrayList<>(), //editState
                Optional.empty(), //tempTime
                new ArrayList<>(),//remarks
                new ArrayList<>(),//ouenTime
                new ArrayList<>(),//ouenTimeSheet
                Optional.empty()
        );
    }

    private static boolean setDataIntoDomain(
            IntegrationOfDaily data,
            TimeLeavingOfDailyAttd timeAttendance,
            WorkInfoOfDailyAttendance workInformation,
            CalAttrOfDailyPerformance calAttrOfDailyPerformance,
            AffiliationInforState employeeState,
            Optional<BreakTimeOfDailyAttd> breakTime,
            Optional<OutingTimeOfDailyAttd> outingTime,
            Optional<ShortTimeOfDailyAttd> shortTime
    ) {
        breakTime.ifPresent(data::setBreakTime);
        data.setOutingTime(outingTime);
        data.setShortTime(shortTime);
        data.setAttendanceLeave(Optional.of(timeAttendance));
        data.setCalAttr(calAttrOfDailyPerformance.getCalcategory());
        data.setAffiliationInfor(employeeState.getAffiliationInforOfDailyPerfor().get());
        if (workInformation.getRecordInfo().isSame(data.getWorkInformation().getRecordInfo())) {
            List<ScheduleTimeSheet> scheduleTimeSheet = workInformation.getScheduleTimeSheets();
            data.getWorkInformation().setScheduleTimeSheets(scheduleTimeSheet);
            return false;
        }else {
            data.setWorkInformation(workInformation);
            return true;
        }
    }

    //??????????????????????????????
    private static void setEditState(IntegrationOfDaily domain) {
        List<Integer> ITEM = AttendanceItemIdContainer.getItemIdByDailyDomains(DailyDomainGroup.ATTENDACE_LEAVE,
                DailyDomainGroup.BREAK_TIME, DailyDomainGroup.OUTING_TIME, DailyDomainGroup.SHORT_TIME);
        ITEM.addAll(Arrays.asList(28,29));
        List<EditStateOfDailyAttd> editState = ITEM.stream()
                .map(x -> new EditStateOfDailyAttd(x, EditStateSetting.HAND_CORRECTION_MYSELF))
                .collect(Collectors.toList());
        domain.setEditState(editState);
    }

    private static IntegrationOfDaily replaceDeductionTimeSheet(
            IntegrationOfDaily provisionalRecord,
            List<BreakTimeSheet> breakTimeSheets,
            List<OutingTimeSheet> outingTimeSheets,
            List<ShortWorkingTimeSheet> shortWorkingTimeSheets,
            String employeeId,
            GeneralDate ymd
    ) {
        provisionalRecord.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(outingTimeSheets)));
        provisionalRecord.setBreakTime(new BreakTimeOfDailyAttd(breakTimeSheets));
        provisionalRecord.setShortTime(Optional.of(new ShortTimeOfDailyAttd(shortWorkingTimeSheets)));
        provisionalRecord.setEmployeeId(employeeId);
        provisionalRecord.setYmd(ymd);
        return provisionalRecord;
    }

    public interface Require extends CorrectDailyAttendanceService.Require {
        Optional<IntegrationOfDaily> findDailyRecord(String employeeId, GeneralDate ymd);

        AffiliationInforState createAffiliationInforOfDailyPerfor(String companyId, String employeeId, GeneralDate ymd, String empCalAndSumExecLogID);
    }
}
