package nts.uk.ctx.at.request.app.command.application.timeleaveapplication;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.find.application.timeleaveapplication.dto.TimeDigestApplicationDto;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class TimeLeaveAppDetailCommand {

    private int appTimeType;

    private List<TimeZoneCommand> timeZones;

    private TimeDigestApplicationCommand applyTime;

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

}
