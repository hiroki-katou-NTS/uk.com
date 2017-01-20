/*
 * 
 */
package nts.uk.ctx.core.app.insurance.labor.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOfficeRepository;

@Stateless
@Transactional
public class LaborInsuranceOfficeDeleteCommandHandler extends CommandHandler<LaborInsuranceOfficeDeleteCommand> {

	/** CompanyRepository */
	@Inject
	private LaborInsuranceOfficeRepository laborInsuranceOfficeRepository;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<LaborInsuranceOfficeDeleteCommand> context) {
		LaborInsuranceOfficeDeleteCommand laborInsuranceOffice = context.getCommand();
		this.laborInsuranceOfficeRepository.remove(laborInsuranceOffice.getCode(), 1000L);
	}

}
