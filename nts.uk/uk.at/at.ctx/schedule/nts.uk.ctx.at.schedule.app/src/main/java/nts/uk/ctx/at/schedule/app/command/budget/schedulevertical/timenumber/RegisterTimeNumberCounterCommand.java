package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.timenumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTimeNumberCounterCommand {

    private int type;

    private List<Integer> selectedNoList;
    
}
