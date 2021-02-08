package nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkpcounterlaborcostandtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWkpLaborCostAndTimeCommand {

    private List<LaborCostAndTime> laborCostAndTimes;

}
