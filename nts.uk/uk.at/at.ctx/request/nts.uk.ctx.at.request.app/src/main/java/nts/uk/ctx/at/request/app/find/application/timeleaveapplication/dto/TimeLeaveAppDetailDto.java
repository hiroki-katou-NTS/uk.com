package nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeDigestApplicationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication.TimeLeaveApplicationDetailShare;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TimeLeaveAppDetailDto {

    private int appTimeType;

    private List<TimeZoneDto> timeZones;

    private TimeDigestApplicationDto applyTime;

    public TimeLeaveApplicationDetail toDomain() {
        return new TimeLeaveApplicationDetail(
                EnumAdaptor.valueOf(appTimeType, AppTimeType.class),
                timeZones.stream().map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStartTime(), i.getEndTime())).collect(Collectors.toList()),
                new TimeDigestApplication(
                        new AttendanceTime(applyTime.getSuper60AppTime()),
                        new AttendanceTime(applyTime.getCareAppTime()),
                        new AttendanceTime(applyTime.getChildCareAppTime()),
                        new AttendanceTime(applyTime.getSubstituteAppTime()),
                        new AttendanceTime(applyTime.getSpecialAppTime()),
                        new AttendanceTime(applyTime.getAnnualAppTime()),
                        Optional.ofNullable(applyTime.getSpecialLeaveFrameNo())
                )
        );
    }

    public static TimeLeaveAppDetailDto fromDomain(TimeLeaveApplicationDetail domain) {
        return new TimeLeaveAppDetailDto(
                domain.getAppTimeType().value,
                domain.getTimeZoneWithWorkNoLst().stream().map(i -> new TimeZoneDto(
                        i.getWorkNo().v(),
                        i.getTimeZone().getStartTime() == null ? null : i.getTimeZone().getStartTime().v(),
                        i.getTimeZone().getEndTime() == null ? null : i.getTimeZone().getEndTime().v()
                )).collect(Collectors.toList()),
                new TimeDigestApplicationDto(
                        domain.getTimeDigestApplication().getOvertime60H().v(),
                        domain.getTimeDigestApplication().getNursingTime().v(),
                        domain.getTimeDigestApplication().getChildTime().v(),
                        domain.getTimeDigestApplication().getTimeOff().v(),
                        domain.getTimeDigestApplication().getTimeSpecialVacation().v(),
                        domain.getTimeDigestApplication().getTimeAnnualLeave().v(),
                        domain.getTimeDigestApplication().getSpecialVacationFrameNO().orElse(null)
                )
        );
    }
    
    public TimeLeaveApplicationDetailShare toShare() {
        return new TimeLeaveApplicationDetailShare(
                EnumAdaptor.valueOf(appTimeType, AppTimeType.class), 
                timeZones.stream().map(i -> new TimeZoneWithWorkNo(i.getWorkNo(), i.getStartTime(), i.getEndTime())).collect(Collectors.toList()),
                new TimeDigestApplicationShare(
                        new AttendanceTime(applyTime.getSuper60AppTime()),
                        new AttendanceTime(applyTime.getCareAppTime()),
                        new AttendanceTime(applyTime.getChildCareAppTime()),
                        new AttendanceTime(applyTime.getSubstituteAppTime()),
                        new AttendanceTime(applyTime.getSpecialAppTime()),
                        new AttendanceTime(applyTime.getAnnualAppTime()),
                        Optional.ofNullable(applyTime.getSpecialLeaveFrameNo())
                ));
    }
}
