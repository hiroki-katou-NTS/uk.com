package nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset;

import java.util.List;

import lombok.Value;

@Value
public class DataDisplayAverageDto {
    private AverageWageCalculationSetDto averageWageCalculationSet;
    private List<StatementDto> lstStatemetPaymentItem;
    private List<StatementDto> lstStatemetAttendanceItem;
}
