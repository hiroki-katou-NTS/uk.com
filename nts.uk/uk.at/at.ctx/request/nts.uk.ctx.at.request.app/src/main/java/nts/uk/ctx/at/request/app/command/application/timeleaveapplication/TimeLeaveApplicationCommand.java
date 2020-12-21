package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.AttendanceTime;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHdFrameNo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class TimeLeaveApplicationCommand {

    private int typeApplicationTime;

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
            if (x.getAppTimeType()== AppTimeType.ATWORK.value || x.getAppTimeType()== AppTimeType.OFFWORK.value ||
                x.getAppTimeType()== AppTimeType.ATWORK2.value || x.getAppTimeType()== AppTimeType.OFFWORK2.value){
                result.add(new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(x.getAppTimeType(), AppTimeType.class),
                    Arrays.asList(new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())),
                    setTimeDigest(this.typeApplicationTime,this.specialHdFrameNo,x.getApplicationTime())
                ));
            }
        });

        List<TimeDigestApplicationCommand> typePrivate = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.PRIVATE.value).collect(Collectors.toList());
        if (typePrivate.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typePrivate.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.typeApplicationTime,this.specialHdFrameNo,typePrivate.get(0).getApplicationTime())
            ));
        }

        List<TimeDigestApplicationCommand> typeUnion = commands.stream().filter(x -> x.getAppTimeType() == AppTimeType.UNION.value).collect(Collectors.toList());
        if (typeUnion.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(),x.getStartTime(),x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typeUnion.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.typeApplicationTime,this.specialHdFrameNo,typeUnion.get(0).getApplicationTime())
            ));
        }
        return result;
    }

    private static TimeDigestApplication setTimeDigest(int typeApplicationTime, Integer specialHdFrameNo, int applicationTime) {
        TimeDigestApplication result = new TimeDigestApplication();
        if (typeApplicationTime == 0) {
            result.setHoursOfSubHoliday(new AttendanceTime(applicationTime));
        } else if (typeApplicationTime == 1) {
            result.setHoursOfHoliday(new AttendanceTime(applicationTime));
        } else if (typeApplicationTime == 2) {
            result.setChildNursingTime(new AttendanceTime(applicationTime));
        } else if (typeApplicationTime == 3) {
            result.setNursingTime(new AttendanceTime(applicationTime));
        } else if (typeApplicationTime == 4) {
            result.setSixtyHOvertime(new AttendanceTime(applicationTime));
        } else if (typeApplicationTime == 5) {
            result.setTimeSpecialVacation(new AttendanceTime(applicationTime));
            result.setSpecialHdFrameNo(specialHdFrameNo == null ? Optional.empty() : Optional.of(new SpecialHdFrameNo(specialHdFrameNo)));
        } else {
            //TODO
        }
        return result;
    }

}
