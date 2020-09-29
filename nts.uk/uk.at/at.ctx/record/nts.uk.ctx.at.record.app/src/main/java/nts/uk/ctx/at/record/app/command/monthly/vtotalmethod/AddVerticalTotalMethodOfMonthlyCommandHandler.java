package nts.uk.ctx.at.record.app.command.monthly.vtotalmethod;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.vtotalmethod.VerticalTotalMethodOfMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddVerticalTotalMethodOfMonthlyCommandHandler extends CommandHandler<AddVerticalTotalMethodOfMonthlyCommand> {
	@Inject
	VerticalTotalMethodOfMonthlyRepository repository;

	@Override
	protected void handle(CommandHandlerContext<AddVerticalTotalMethodOfMonthlyCommand> context) {
		AddVerticalTotalMethodOfMonthlyCommand command = context.getCommand();
		
		String companyId = AppContexts.user().companyId();
		
		VerticalTotalMethodOfMonthly setting = command.toDomain(companyId);
		
		Optional<VerticalTotalMethodOfMonthly> optSetting = repository.findByCid(companyId);
		
		if (optSetting.isPresent()) {
			repository.update(setting);
		}
		else {
			repository.insert(setting);
		}
	}
}
