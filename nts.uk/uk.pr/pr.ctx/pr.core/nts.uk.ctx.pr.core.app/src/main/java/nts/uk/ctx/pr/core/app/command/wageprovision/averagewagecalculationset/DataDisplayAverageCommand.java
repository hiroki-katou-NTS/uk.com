package nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset;

import lombok.Value;

import java.util.List;
@Value
public class DataDisplayAverageCommand {
    private AverageWageCalculationSetCommand averageWageCalculationSet;
    private List<StatementCustomCommand> lstStatemetPaymentItem;
    private List<StatementCustomCommand> lstStatemetAttendanceItem;
}
