package nts.uk.ctx.pr.core.app.command.laborinsurance;

import lombok.Value;

import java.util.List;
@Value
public class AddOccAccIsPrRateCommand {
    private List<OccAccIsPrRateCommand> occAccIsPrRateCommands;
    private boolean isNewMode;
    private Integer endYearMonth;
    private Integer startYearMonth;
}
