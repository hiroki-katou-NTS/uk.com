package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.personalcounter;

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
