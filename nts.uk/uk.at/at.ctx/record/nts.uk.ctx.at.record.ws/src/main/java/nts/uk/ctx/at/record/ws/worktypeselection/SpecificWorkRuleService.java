package nts.uk.ctx.at.record.ws.worktypeselection;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.workrule.specific.AddSpecificWorkRuleCommand;
import nts.uk.ctx.at.record.app.command.workrule.specific.AddSpecificWorkRuleCommandHandler;
import nts.uk.ctx.at.record.app.find.workrule.specific.SpecificWorkRuleDto;
import nts.uk.ctx.at.record.app.find.workrule.specific.SpecificWorkRuleFinder;

/**
 * The Class SpecificWorkRuleService.
 * @author HoangNDH
 */
@Path("record/workrule/specific")
@Produces("application/json")
public class SpecificWorkRuleService {
	
	/** The handler. */
	@Inject
	AddSpecificWorkRuleCommandHandler handler;
	
	/** The finder. */
	@Inject
	SpecificWorkRuleFinder finder;
	
	/**
	 * Find all setting.
	 *
	 * @return the specific work rule dto
	 */
	@Path("find")
	@POST
	public SpecificWorkRuleDto findAllSetting() {
		return this.finder.findSpecificWorkRule();
	}
	
	/**
	 * Register setting.
	 *
	 * @param command the command
	 */
	@Path("register")
	@POST
	public void registerSetting(AddSpecificWorkRuleCommand command) {
		this.handler.handle(command);
	}
}
