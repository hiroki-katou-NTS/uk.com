package nts.uk.ctx.at.aggregation.app.command.schedulecounter.workplacecounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWorkplaceCounterCommand {

    private List<Integer> workplaceCategory;
    
}
