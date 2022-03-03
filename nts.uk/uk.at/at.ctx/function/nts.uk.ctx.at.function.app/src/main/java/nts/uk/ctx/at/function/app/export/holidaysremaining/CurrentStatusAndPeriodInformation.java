package nts.uk.ctx.at.function.app.export.holidaysremaining;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CurrentStatusAndPeriodInformation {
     private boolean currentStatus;
     private OutputPeriodInformation outputPeriodInformation;
}
