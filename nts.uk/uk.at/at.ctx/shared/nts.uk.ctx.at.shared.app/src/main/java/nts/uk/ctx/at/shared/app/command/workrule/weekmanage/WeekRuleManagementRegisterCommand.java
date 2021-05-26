package nts.uk.ctx.at.shared.app.command.workrule.weekmanage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WeekRuleManagementRegisterCommand {
    private int dayOfWeek;
}
