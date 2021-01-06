package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TimeLeaveApplicationCommand {

    //A3_2
    private int timeDigestAppType;

    private List<TimeLeaveAppDetailCommand> details;

    private List<TimeDigestApplicationCommand> digestApps;

    public static TimeLeaveApplication toDomain(TimeLeaveApplicationCommand command, Application app) {
        return new TimeLeaveApplication(
            app,
            command.setDetail(command.details, command.digestApps)
        );
    }

    private List<TimeLeaveApplicationDetail> setDetail(List<TimeLeaveAppDetailCommand> commands, List<TimeDigestApplicationCommand> digestApps) {

        List<TimeLeaveApplicationDetail> result = new ArrayList<>();

        commands.forEach(x -> {
            if (x.getAppTimeType() != AppTimeType.PRIVATE.value && x.getAppTimeType() == AppTimeType.UNION.value) {
                TimeDigestApplicationCommand timeDigestAppCommand = digestApps.stream().filter(i -> i.getAppTimeType() == x.getAppTimeType()).findFirst().orElse(null);
                result.add(new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(x.getAppTimeType(), AppTimeType.class),
                    Arrays.asList(new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())),
                    timeDigestAppCommand.getTimeDigestApplication().toDomain()
                ));
            }
        });

        List<TimeLeaveAppDetailCommand> typePrivate = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.PRIVATE.value).collect(Collectors.toList());
        if (typePrivate.size() > 0) {
            TimeDigestApplicationCommand timeDigestAppCommand = digestApps.stream().filter(i -> i.getAppTimeType() == AppTimeType.PRIVATE.value).findFirst().orElse(null);
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typePrivate.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                timeDigestAppCommand.getTimeDigestApplication().toDomain()
            ));
        }

        List<TimeLeaveAppDetailCommand> typeUnion = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.UNION.value).collect(Collectors.toList());
        if (typeUnion.size() > 0) {
            TimeDigestApplicationCommand timeDigestAppCommand = digestApps.stream().filter(i -> i.getAppTimeType() == AppTimeType.PRIVATE.value).findFirst().orElse(null);
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typeUnion.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typeUnion.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                timeDigestAppCommand.getTimeDigestApplication().toDomain()
            ));
        }
        return result;
    }

//    public static TimeDigestApplication setTimeDigest(int timeDigestAppType, Integer specialHdFrameNo, TimeDigestApplicationCommand digestApp) {
//        TimeDigestApplication result = new TimeDigestApplication();
//        if (timeDigestAppType == TimeDigestAppType.USE_COMBINATION.value) {
//            result.setOvertime60H(new AttendanceTime(digestApp.getTimeDigestApplication().getOvertime60H()));
//            result.setNursingTime(new AttendanceTime(applicationTime));
//            result.setChildTime(new AttendanceTime(applicationTime));
//            result.setTimeOff(new AttendanceTime(applicationTime));
//            result.setTimeSpecialVacation(new AttendanceTime(applicationTime));
//            result.setTimeAnnualLeave(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.TIME_OFF.value) {
//            result.setTimeAnnualLeave(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.TIME_ANNUAL_LEAVE.value) {
//            result.setTimeAnnualLeave(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.CHILD_NURSING_TIME.value) {
//            result.setChildTime(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.NURSING_TIME.value) {
//            result.setNursingTime(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value) {
//            result.setOvertime60H(new AttendanceTime(applicationTime));
//        } else if (timeDigestAppType == TimeDigestAppType.TIME_SPECIAL_VACATION.value) {
//            result.setTimeSpecialVacation(new AttendanceTime(applicationTime));
//            result.setSpecialVacationFrameNO(Optional.of(specialHdFrameNo));
//        }
//        return result;
//    }
}
