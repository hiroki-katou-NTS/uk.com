package nts.uk.ctx.at.schedule.ws.budget.premium.language;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.command.budget.premium.language.InsertPremiumItemLanguageCommand;
import nts.uk.ctx.at.schedule.app.command.budget.premium.language.InsertPremiumItemLanguageCommandHandler;
import nts.uk.ctx.at.schedule.app.command.budget.premium.language.ListPremiumItemLanguageCommand;

@Path("at/schedule/budget/premium/language")
@Produces("application/json")
public class PremiumItemLanguageWebService extends WebService {

	@Inject
	private InsertPremiumItemLanguageCommandHandler insertPremiumItemLanguageCommandHandler;

	@POST
	@Path("insert")
	public void insertOrUpdate(List<InsertPremiumItemLanguageCommand> command) {
		this.insertPremiumItemLanguageCommandHandler.handle(new ListPremiumItemLanguageCommand(command));
	}
}
