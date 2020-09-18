package nts.uk.ctx.at.request.dom.application.businesstrip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.shr.com.time.TimeWithDayAttr;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
// 出張申請
public class BusinessTrip extends Application {

    // 出張勤務情報
    private List<BusinessTripInfo> infos;

    // 出発時刻
    private Optional<TimeWithDayAttr> departureTime;

    // 帰着時刻
    private Optional<TimeWithDayAttr> returnTime;

    public BusinessTrip(List<BusinessTripInfo> infos, Integer departureTime, Integer returnTime, Application application) {
        super(application);
        this.infos = infos;
        this.departureTime = departureTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(departureTime));
        this.returnTime = returnTime == null ? Optional.empty() : Optional.of(new TimeWithDayAttr(returnTime));
    }

    public BusinessTrip(Application application) {
        super(application);
    }
}
