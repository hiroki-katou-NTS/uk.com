/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.authfuncrest;

import java.math.BigDecimal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceAuthority;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformAuthorRepo;

/**
 * @author danpv
 *
 */
@Stateless
public class AuthFuncRestrictionCommandHandler extends CommandHandler<AuthFuncRestrictionCommand> {

	@Inject
	private DailyPerformAuthorRepo daiPerAuthRepo;

	@Override
	protected void handle(CommandHandlerContext<AuthFuncRestrictionCommand> context) {
		AuthFuncRestrictionCommand commmand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		commmand.getAuthFuncRests().forEach(element -> {
			DailyPerformanceAuthority daiPerAuth = new DailyPerformanceAuthority(companyId, commmand.getRoleId(),
					new BigDecimal(element.getFunctionNo()), element.isAvailability());
			daiPerAuthRepo.save(daiPerAuth);
		});
	}

}
