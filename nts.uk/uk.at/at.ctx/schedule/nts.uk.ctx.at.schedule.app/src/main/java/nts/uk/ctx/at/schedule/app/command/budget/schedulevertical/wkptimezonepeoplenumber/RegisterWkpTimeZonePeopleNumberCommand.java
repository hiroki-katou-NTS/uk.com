package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkptimezonepeoplenumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWkpTimeZonePeopleNumberCommand {

    private List<Integer> timeZone;
    
}
