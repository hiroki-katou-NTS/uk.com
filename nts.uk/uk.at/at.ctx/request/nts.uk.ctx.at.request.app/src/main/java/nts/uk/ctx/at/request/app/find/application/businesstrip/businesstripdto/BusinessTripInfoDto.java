package nts.uk.ctx.at.request.app.find.application.businesstrip.businesstripdto;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.businesstrip.service.WorkingTime;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.shr.com.time.TimeWithDayAttr;

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
        Optional<List<WorkingTime>> workingHours = Optional.empty();
        workingHours = Optional.of(Arrays.asList(new WorkingTime(
                new WorkNo(1), 
                startWorkTime == null ? Optional.empty() : Optional.ofNullable(new TimeWithDayAttr(startWorkTime)), 
                        endWorkTime == null ? Optional.empty() : Optional.ofNullable(new TimeWithDayAttr(endWorkTime)))));
//        if (startWorkTime != null && endWorkTime != null) {
//        }
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
            begin = domain.getWorkingHours().get().get(0).getStartTime().map(TimeWithDayAttr::v).orElse(null);
            end = domain.getWorkingHours().get().get(0).getEndTime().map(TimeWithDayAttr::v).orElse(null);
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
