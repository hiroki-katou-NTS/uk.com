package nts.uk.ctx.at.request.app.find.application.timeleaveapplication;

import lombok.Value;
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

import static nts.uk.ctx.at.request.app.command.application.timeleaveapplication.TimeLeaveApplicationCommand.setTimeDigest;

@Value
public class TimeLeaveApplicationDto {

    private int timeDigestAppType;

    private Integer specialHdFrameNo;

    private List<TimeDigestApplicationDto> digestApplications;


    public static TimeLeaveApplication toDomain(TimeLeaveApplicationDto dto, Application app) {
        return new TimeLeaveApplication(
            app,
            dto.setDetail(dto.digestApplications)
        );
    }

    private List<TimeLeaveApplicationDetail> setDetail(List<TimeDigestApplicationDto> details) {

        List<TimeLeaveApplicationDetail> result = new ArrayList<>();

        details.forEach(x -> {
            if (x.getAppTimeType() == AppTimeType.ATWORK.value || x.getAppTimeType() == AppTimeType.OFFWORK.value ||
                x.getAppTimeType() == AppTimeType.ATWORK2.value || x.getAppTimeType() == AppTimeType.OFFWORK2.value) {
                result.add(new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(x.getAppTimeType(), AppTimeType.class),
                    Arrays.asList(new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())),
                    setTimeDigest(this.timeDigestAppType, this.specialHdFrameNo, x.getApplicationTime())
                ));
            }
        });

        List<TimeDigestApplicationDto> typePrivate = details.stream().filter(x -> x.getAppTimeType() == AppTimeType.PRIVATE.value).collect(Collectors.toList());
        if (typePrivate.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typePrivate.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.timeDigestAppType, this.specialHdFrameNo, typePrivate.get(0).getApplicationTime())
            ));
        }

        List<TimeDigestApplicationDto> typeUnion = details.stream().filter(x -> x.getAppTimeType() == AppTimeType.UNION.value).collect(Collectors.toList());
        if (typeUnion.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typeUnion.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                setTimeDigest(this.timeDigestAppType, this.specialHdFrameNo, typeUnion.get(0).getApplicationTime())
            ));
        }
        return result;
    }

}
