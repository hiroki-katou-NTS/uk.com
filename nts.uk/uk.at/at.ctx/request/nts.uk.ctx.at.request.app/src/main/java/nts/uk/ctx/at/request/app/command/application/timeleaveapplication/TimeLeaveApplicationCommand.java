package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.*;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class TimeLeaveApplicationCommand {

    //A3_2
    private int timeDigestAppType;

    // A5_2
    private Integer specialHdFrameNo;

    private List<TimeDigestApplicationCommand> digestApplications;

    public static TimeLeaveApplication toDomain(TimeLeaveApplicationCommand command, Application app) {
        return new TimeLeaveApplication(
            app,
            command.setDetail(command.digestApplications)
        );
    }

    private List<TimeLeaveApplicationDetail> setDetail(List<TimeDigestApplicationCommand> commands) {

        List<TimeLeaveApplicationDetail> result = new ArrayList<>();

        commands.forEach(x -> {
            if (x.getAppTimeType() != AppTimeType.PRIVATE.value && x.getAppTimeType()== AppTimeType.UNION.value ){
                result.add(new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(x.getAppTimeType(), AppTimeType.class),
                    Arrays.asList(new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())),
                    setTimeDigest(this.timeDigestAppType,this.specialHdFrameNo,x.getApplicationTime())
                ));
            }
        });

        List<TimeDigestApplicationCommand> typePrivate = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.PRIVATE.value).collect(Collectors.toList());
        if (typePrivate.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typePrivate.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.timeDigestAppType,this.specialHdFrameNo,typePrivate.get(0).getApplicationTime())
            ));
        }

        List<TimeDigestApplicationCommand> typeUnion = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.UNION.value).collect(Collectors.toList());
        if (typeUnion.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typeUnion.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.timeDigestAppType,this.specialHdFrameNo,typeUnion.get(0).getApplicationTime())
            ));
        }
        return result;
    }

    public static TimeDigestApplication setTimeDigest(int timeDigestAppType, Integer specialHdFrameNo, int applicationTime) {
        TimeDigestApplication result = new TimeDigestApplication();
        if (timeDigestAppType == TimeDigestAppType.TIME_OFF.value) {
            result.setTimeOff(new AttendanceTime(applicationTime));
        } else if (timeDigestAppType == TimeDigestAppType.TIME_ANNUAL_LEAVE.value) {
            result.setTimeAnualLeave(new AttendanceTime(applicationTime));
        } else if (timeDigestAppType == TimeDigestAppType.CHILD_NURSING_TIME.value) {
            result.setChildTime(new AttendanceTime(applicationTime));
        } else if (timeDigestAppType == TimeDigestAppType.NURSING_TIME.value) {
            result.setNursingTime(new AttendanceTime(applicationTime));
        } else if (timeDigestAppType == TimeDigestAppType.SIXTY_H_OVERTIME.value) {
            result.setOvertime60H(new AttendanceTime(applicationTime));
        } else if (timeDigestAppType == TimeDigestAppType.TIME_SPECIAL_VACATION.value) {
            result.setTimeSpecialVacation(new AttendanceTime(applicationTime));
            result.setSpecialVacationFrameNO(Optional.of(specialHdFrameNo));
        } else {
            //TODO
        }
        return result;
    }
}
