package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;

@Stateless
@Transactional
public class AccidentInsuranceRateAddCommandHandler extends CommandHandler<AccidentInsuranceRateAddCommand> {

	/** CompanyRepository */	
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	/**
	 * Handle command.
	 * 
	 * @param context
	 *            context
	 */
	@Override
	protected void handle(CommandHandlerContext<AccidentInsuranceRateAddCommand> context) {
		AccidentInsuranceRate accidentInsuranceRate = context.getCommand().toDomain();
		accidentInsuranceRate.validate();
		this.accidentInsuranceRateRepository.add(accidentInsuranceRate);
	}

}
