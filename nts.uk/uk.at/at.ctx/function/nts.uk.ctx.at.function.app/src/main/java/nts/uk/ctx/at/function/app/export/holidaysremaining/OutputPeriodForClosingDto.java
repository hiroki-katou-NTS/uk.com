package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OutputPeriodForClosingDto {
    private OutputPeriodInformation periodInformation;
    private PeriodCorrespondingYm currentPeriod;
}
