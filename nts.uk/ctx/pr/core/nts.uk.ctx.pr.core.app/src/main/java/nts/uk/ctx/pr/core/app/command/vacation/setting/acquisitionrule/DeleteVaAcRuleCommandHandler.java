package nts.uk.ctx.pr.core.app.command.vacation.setting.acquisitionrule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;

/**
 * The Class DeleteVaAcRuleCommandHandler.
 */
@Stateless
public class DeleteVaAcRuleCommandHandler extends CommandHandler<DeleteVaAcRuleCommand> {
	
	/** The va ac rule repo. */
	@Inject
	private AcquisitionRuleRepository vaAcRuleRepo;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DeleteVaAcRuleCommand> context) {
		
		this.vaAcRuleRepo.remove(context.getCommand().getCompanyId());
		
	}

}
