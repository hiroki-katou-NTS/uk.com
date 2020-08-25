package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.shared.app.find.common.TimeZoneWithWorkNoDto;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

}
