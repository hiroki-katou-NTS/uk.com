package nts.uk.ctx.at.request.dom.application.businesstrip;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

// 出張申請の印刷内容
@Getter
@Setter
@NoArgsConstructor
public class BusinessTripPrintContent {

    // 出張勤務情報
    private List<BusinessTripInfo> infos;

    // 出発時刻
    private Optional<Integer> departureTime;

    // 帰着時刻
    private Optional<Integer> returnTime;

    public BusinessTripPrintContent(List<BusinessTripInfo> infos, Integer departureTime, Integer returnTime) {
        this.infos = infos;
        this.departureTime = Optional.ofNullable(departureTime);
        this.returnTime = Optional.ofNullable(returnTime);
    }
}
