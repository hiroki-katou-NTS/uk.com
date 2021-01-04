package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

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

@Value
public class TimeLeaveApplicationDto {

    private int timeDigestAppType;

    //詳細
    private List<TimeLeaveAppDetailDto> details;

    private List<TimeDigestApplicationDto> timeDigestApps;

    public static TimeLeaveApplication toDomain(TimeLeaveApplicationDto dto, Application app) {
        return new TimeLeaveApplication(
            app,
            dto.setDetail(dto.details, dto.timeDigestApps)
        );
    }

    public static TimeLeaveApplicationDto fromDomain(TimeLeaveApplication domain) {
        List<TimeLeaveAppDetailDto> details = new ArrayList<>();
        List<TimeDigestApplicationDto> timeDigestApps = new ArrayList<>();
        int timeDigestAppType = 0; //TODO chưa biết map vs feild nào trong DB
        domain.getLeaveApplicationDetails().forEach(x -> {
            x.getTimeZoneWithWorkNoLst().forEach(i -> {
                details.add(new TimeLeaveAppDetailDto(
                    i.getWorkNo().v(),
                    x.getAppTimeType().value,
                    i.getTimeZone().getStartTime().v(),
                    i.getTimeZone().getEndTime().v()
                ));
            });
            timeDigestApps.add(new TimeDigestApplicationDto(
                x.getAppTimeType().value,
                new TimeDigestDto(
                    x.getTimeDigestApplication().getOvertime60H().v(),
                    x.getTimeDigestApplication().getNursingTime().v(),
                    x.getTimeDigestApplication().getChildTime().v(),
                    x.getTimeDigestApplication().getTimeOff().v(),
                    x.getTimeDigestApplication().getTimeSpecialVacation().v(),
                    x.getTimeDigestApplication().getTimeAnualLeave().v(),
                    x.getTimeDigestApplication().getSpecialVacationFrameNO().isPresent() ? x.getTimeDigestApplication().getSpecialVacationFrameNO().get() : null
                )
            ));
//            int count = 0;
//            if (Objects.nonNull(x.getTimeDigestApplication().getOvertime60H().v())) {
//                count++;
//                timeDigestAppType = 4 ;
//            }
//            if (Objects.nonNull(x.getTimeDigestApplication().getNursingTime().v())) {
//                count++;
//            }
//            if (Objects.nonNull(x.getTimeDigestApplication().getChildTime().v())) {
//                count++;
//            }
//            if (Objects.nonNull(x.getTimeDigestApplication().getTimeOff().v())) {
//                count++;
//            }
//            if (Objects.nonNull(x.getTimeDigestApplication().getTimeSpecialVacation().v())) {
//                count++;
//            }
//            if (Objects.nonNull(x.getTimeDigestApplication().getTimeAnualLeave().v())) {
//                count++;
//            }
//
//            if (count > 1){
//                timeDigestAppType =
//            }
        });

        return new TimeLeaveApplicationDto(timeDigestAppType, details, timeDigestApps);
    }

    private List<TimeLeaveApplicationDetail> setDetail(List<TimeLeaveAppDetailDto> details, List<TimeDigestApplicationDto> timeDigestApps) {

        List<TimeLeaveApplicationDetail> result = new ArrayList<>();

        details.forEach(x -> {
            if (x.getAppTimeType() != AppTimeType.PRIVATE.value && x.getAppTimeType() == AppTimeType.UNION.value) {
                TimeDigestApplicationDto timeDigestApp = timeDigestApps.stream().filter(i -> i.getAppTimeType() == x.getAppTimeType()).findFirst().orElse(null);
                result.add(new TimeLeaveApplicationDetail(
                    EnumAdaptor.valueOf(x.getAppTimeType(), AppTimeType.class),
                    Arrays.asList(new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())),
                    timeDigestApp.getTimeDigestDto().toDomain()
                ));
            }
        });

        List<TimeLeaveAppDetailDto> typePrivate = details.stream().filter(x -> x.getAppTimeType() == AppTimeType.PRIVATE.value).collect(Collectors.toList());
        if (typePrivate.size() > 0) {
            TimeDigestApplicationDto timeDigestApp = timeDigestApps.stream().filter(i -> i.getAppTimeType() == AppTimeType.PRIVATE.value).findFirst().orElse(null);
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typePrivate.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typePrivate.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                timeDigestApp.getTimeDigestDto().toDomain()
            ));
        }

        List<TimeLeaveAppDetailDto> typeUnion = details.stream().filter(x -> x.getAppTimeType() == AppTimeType.UNION.value).collect(Collectors.toList());
        if (typeUnion.size() > 0) {
            List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst = typeUnion.stream().map(x -> new TimeZoneWithWorkNo(x.getWorkNo(), x.getStartTime(), x.getEndTime())).collect(Collectors.toList());
            TimeDigestApplicationDto timeDigestApp = timeDigestApps.stream().filter(i -> i.getAppTimeType() == AppTimeType.PRIVATE.value).findFirst().orElse(null);

            result.add(new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(typeUnion.get(0).getAppTimeType(), AppTimeType.class),
                timeZoneWithWorkNoLst,
                timeDigestApp.getTimeDigestDto().toDomain()
            ));
        }
        return result;
    }


}
