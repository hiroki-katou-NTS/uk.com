package nts.uk.ctx.at.shared.app.command.calculation.holiday.roundingmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.calculation.holiday.roundingmonth.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;
import nts.uk.shr.com.context.AppContexts;

/**
 * Handler for adding exceed outside rounding data command
 * @author HoangNDH
 *
 */
@Stateless
public class AddExcoutRoundingCommandHandler extends CommandHandler<AddExcoutRoundingCommand> {
	/** The rounding set repository */
	@Inject
	RoundingSetOfMonthlyRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<AddExcoutRoundingCommand> context) {
		AddExcoutRoundingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		TimeRoundingOfExcessOutsideTime rounding = TimeRoundingOfExcessOutsideTime.createFromJavaType(companyId, 
				command.getRoundingUnit(), command.getRoundingProcess());
		repository.persistAndUpdate(rounding);
	}

}
