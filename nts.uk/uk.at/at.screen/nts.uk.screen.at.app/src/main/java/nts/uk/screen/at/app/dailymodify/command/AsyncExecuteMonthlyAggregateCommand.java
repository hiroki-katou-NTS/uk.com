package nts.uk.screen.at.app.dailymodify.command;

import lombok.Value;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemParent;

@Value
public class AsyncExecuteMonthlyAggregateCommand {
    DPItemParent dataParent;
}
