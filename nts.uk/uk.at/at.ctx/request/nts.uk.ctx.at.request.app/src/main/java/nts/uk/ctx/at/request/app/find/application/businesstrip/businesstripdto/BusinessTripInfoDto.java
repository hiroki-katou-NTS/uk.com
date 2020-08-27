package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Value
public class BusinessTripInfoDto {

    private String date;

    private String wkTypeCd;

    private String wkTimeCd;

    private Integer startWorkTime;

    private Integer endWorkTime;

    public BusinessTripInfo toDomain() {
        Optional<List<TimeZoneWithWorkNo>> workingHours = Optional.of(Collections.emptyList());
        if (startWorkTime != null && endWorkTime != null) {
            workingHours = Optional.of(Arrays.asList(new TimeZoneWithWorkNo(0, startWorkTime, endWorkTime)));
        }
        return new BusinessTripInfo(
                new WorkInformation(this.wkTimeCd, this.wkTypeCd),
                GeneralDate.fromString(date, "yyyy/MM/dd"),
                workingHours
        );
    }

    public static BusinessTripInfoDto fromDomain(BusinessTripInfo domain) {
        Integer begin = null;
        Integer end = null;
        if (domain.getWorkingHours().isPresent() && !domain.getWorkingHours().get().isEmpty()) {
            begin = domain.getWorkingHours().get().get(0).getTimeZone().getStartTime().v();
            end = domain.getWorkingHours().get().get(0).getTimeZone().getEndTime().v();
        }
        return new BusinessTripInfoDto(
                domain.getDate().toString(),
                domain.getWorkInformation().getWorkTypeCode().v(),
                domain.getWorkInformation().getWorkTimeCode().v(),
                begin,
                end
        );
    }

}
