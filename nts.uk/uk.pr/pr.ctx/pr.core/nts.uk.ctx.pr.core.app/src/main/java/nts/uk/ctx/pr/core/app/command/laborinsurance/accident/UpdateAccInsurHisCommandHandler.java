package nts.uk.ctx.pr.core.app.command.laborinsurance.accident;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.laborinsurance.EditMethod;
import nts.uk.ctx.pr.core.dom.laborinsurance.OccAccidentInsurService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateAccInsurHisCommandHandler extends CommandHandler<AccInsurHisCommand> {
	
	@Inject
	private OccAccidentInsurService accidentInsurService;
	
	@Override
	protected void handle(CommandHandlerContext<AccInsurHisCommand> context) {

	}

}
