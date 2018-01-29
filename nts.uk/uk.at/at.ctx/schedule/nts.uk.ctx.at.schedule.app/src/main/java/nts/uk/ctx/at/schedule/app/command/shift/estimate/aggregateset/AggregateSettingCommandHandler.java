package nts.uk.ctx.at.schedule.app.command.shift.estimate.aggregateset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset.AggregateSettingRepository;

/**
 * The Class AggregateSettingCommandHandler.
 */
@Stateless
public class AggregateSettingCommandHandler extends CommandHandler<AggregateSettingCommand> {
	
	/** The repository. */
	@Inject
	private AggregateSettingRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AggregateSettingCommand> context) {
		// get command
		AggregateSettingCommand command = context.getCommand();
		
		//convert to domain
		AggregateSetting domain = new AggregateSetting(command);
		
		//save
		this.repository.update(domain);
	}
}
