package nts.uk.ctx.at.record.app.find.holiday.roundingmonth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoundingSetOfMonthlyDto {
    /** 時間外超過の時間丸め */
    private TimeRoundingOfExcessOutsideTimeDto timeRoundingOfExcessOutsideTime;

    /** 項目丸め設定 */
    private List<RoundingMonthDto> itemRoundingSet;

    public RoundingSetOfMonthlyDto(RoundingSetOfMonthly domain) {
        // Q&A #39234 Remove 時間外超過の時間丸め
//        if (domain.getTimeRoundingOfExcessOutsideTime().isPresent()) {
//            this.timeRoundingOfExcessOutsideTime = new TimeRoundingOfExcessOutsideTimeDto(
//                    domain.getTimeRoundingOfExcessOutsideTime().get().getRoundingUnit().value,
//                    domain.getTimeRoundingOfExcessOutsideTime().get().getRoundingProcess().value
//            );
//        }
        this.itemRoundingSet = domain.getItemRoundingSet().values().stream().map(i -> new RoundingMonthDto(
                i.getAttendanceItemId(),
                i.getRoundingSet().getRoundingTime().value,
                i.getRoundingSet().getRounding().value
        )).collect(Collectors.toList());
    }
}
