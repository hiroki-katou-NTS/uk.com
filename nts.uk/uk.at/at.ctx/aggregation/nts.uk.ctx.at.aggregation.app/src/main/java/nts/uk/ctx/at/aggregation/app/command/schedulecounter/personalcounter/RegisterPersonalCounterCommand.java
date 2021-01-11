package nts.uk.ctx.at.aggregation.app.command.schedulecounter.personalcounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPersonalCounterCommand {

    private List<Integer> personalCategory;
    
}
