package nts.uk.ctx.at.function.app.query.anyperiodcorrection.formatsetting;

import lombok.Value;
import nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly.SheetCorrectedMonthlyDto;

import java.util.List;

@Value
public class AnyPeriodCorrectionFormatDto {
    private String code;
    private String name;
    private List<SheetCorrectedMonthlyDto> sheetSettings;
}
