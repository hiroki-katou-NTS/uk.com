package nts.uk.screen.at.ws.ksu001.q;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ksu.ksu001q.command.RegisterExternalBudgetDailyCommand;
import nts.uk.screen.at.app.ksu.ksu001q.command.RegisterExternalBudgetDailyCommandHandler;
import nts.uk.screen.at.app.ksu.ksu001q.query.DailyExternalBudget;
import nts.uk.screen.at.app.ksu.ksu001q.query.DailyExternalBudgetDto;
import nts.uk.screen.at.app.ksu.ksu001q.query.DailyExternalBudgetQuery;
import nts.uk.screen.at.app.ksu.ksu001q.query.InitialStartupScreenQuery;
import nts.uk.screen.at.app.ksu.ksu001q.query.InitialStartupScreenRequest;
import nts.uk.screen.at.app.ksu.ksu001q.query.InitialStartupScreenResultDto;

/**
 * The Class ExternalBudgetInformationWs.
 * 
 * @author thanhlv
 */
@Path("at/schedule/budget/external/infor")
@Produces("application/json")
public class ExternalBudgetInformationWs extends WebService {

	/** The initial startup screen query. */
	@Inject
	private InitialStartupScreenQuery initialStartupScreenQuery;

	/** The daily external budget query. */
	@Inject
	private DailyExternalBudgetQuery dailyExternalBudgetQuery;

	/** The register external budget daily command handler. */
	@Inject
	private RegisterExternalBudgetDailyCommandHandler registerExternalBudgetDailyCommandHandler;

	/**
	 * Gets the all external budget.
	 *
	 * @param initialStartupScreenDto the initial startup screen dto
	 * @return the all external budget
	 */
	@POST
	@Path("findExternalBudget")
	public InitialStartupScreenResultDto getAllExternalBudget(InitialStartupScreenRequest initialStartupScreenDto) {
		return initialStartupScreenQuery.getInfomation(initialStartupScreenDto);
	}

	/**
	 * Gets the budget daily.
	 *
	 * @param dailyExternal the daily external
	 * @return the budget daily
	 */
	@POST
	@Path("findBudgetDaily")
	public List<DailyExternalBudgetDto> getBudgetDaily(DailyExternalBudget dailyExternal) {
		return dailyExternalBudgetQuery.getDailyExternalBudget(dailyExternal);
	}

	/**
	 * Register.
	 *
	 * @param command the command
	 */
	@POST
	@Path("register")
	public void register(RegisterExternalBudgetDailyCommand command) {
		registerExternalBudgetDailyCommandHandler.handle(command);
	}
}
