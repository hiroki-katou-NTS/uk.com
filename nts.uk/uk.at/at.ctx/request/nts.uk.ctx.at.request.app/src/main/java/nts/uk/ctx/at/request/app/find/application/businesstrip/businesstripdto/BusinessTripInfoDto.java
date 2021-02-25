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

    // 年月日
    private String date;

    // 勤務情報.勤務種類コード
    private String wkTypeCd;

    // 勤務情報.就業時間帯コード
    private String wkTimeCd;

    // 勤務時間帯.時間帯.開始時刻
    private Integer startWorkTime;

    // 勤務時間帯.時間帯.終了時刻
    private Integer endWorkTime;

    public BusinessTripInfo toDomain() {
        Optional<List<TimeZoneWithWorkNo>> workingHours = Optional.empty();
        if (startWorkTime != null && endWorkTime != null) {
            workingHours = Optional.of(Arrays.asList(new TimeZoneWithWorkNo(1, startWorkTime, endWorkTime)));
        }
        return new BusinessTripInfo(
                new WorkInformation(this.wkTypeCd, this.wkTimeCd),
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
                domain.getWorkInformation().getWorkTimeCode() == null ? null : domain.getWorkInformation().getWorkTimeCode().v(),
                begin,
                end
        );
    }

}
