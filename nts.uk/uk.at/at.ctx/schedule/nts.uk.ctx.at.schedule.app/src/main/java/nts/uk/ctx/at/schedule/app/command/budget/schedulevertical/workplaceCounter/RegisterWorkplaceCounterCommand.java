package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.workplaceCounter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.verticalsetting.VerticalCalItemCommand;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.FormulaNumerical;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalItem;
import nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting.VerticalCalSet;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterWorkplaceCounterCommand {

    private int workplaceCategory;
    
}
