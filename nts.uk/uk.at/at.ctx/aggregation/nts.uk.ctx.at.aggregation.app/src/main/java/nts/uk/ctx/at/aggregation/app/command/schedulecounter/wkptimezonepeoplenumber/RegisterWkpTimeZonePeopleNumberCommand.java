package nts.uk.ctx.at.aggregation.app.command.schedulecounter.wkptimezonepeoplenumber;

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
