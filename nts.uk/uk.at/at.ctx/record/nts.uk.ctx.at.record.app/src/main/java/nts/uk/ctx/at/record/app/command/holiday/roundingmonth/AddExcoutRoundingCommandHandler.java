package nts.uk.ctx.at.record.app.command.holiday.roundingmonth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.monthly.roundingset.RoundingSetOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.monthly.roundingset.TimeRoundingOfExcessOutsideTime;

/**
 * Handler for adding exceed outside rounding data command.
 *
 */
@Stateless
public class AddExcoutRoundingCommandHandler extends CommandHandler<AddExcoutRoundingCommand> {
	
	/**  The rounding set repository. */
	@Inject
	private RoundingSetOfMonthlyRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddExcoutRoundingCommand> context) {
		AddExcoutRoundingCommand command = context.getCommand();
		TimeRoundingOfExcessOutsideTime rounding = TimeRoundingOfExcessOutsideTime.of( 
				command.getRoundingUnit(), command.getRoundingProcess());
		repository.persistAndUpdate(rounding);
	}

}
