package nts.uk.file.at.app.export.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.PersonCounterTimesNumberCounterResult;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.WorkplaceCounterTimesNumberCounterResult;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDisplayAndAggregatedInfoDto {
    // List<個人別の回数集計結果>
    private List<PersonCounterTimesNumberCounterResult> personalCounterResult;

    // List<職場別の回数集計結果>
    private List<WorkplaceCounterTimesNumberCounterResult> workplaceCounterResult;

    // List<シフト表示情報dto>
    private List<ShiftDisplayInfoDto> shiftDisplayInfos;

    // List<回数集計>
    private List<TotalTimes> personalTotals;
    private List<TotalTimes> workplaceTotals;
}
