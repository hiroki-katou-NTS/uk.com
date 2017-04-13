package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential.input;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input.PersonResiTaxRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateResidenceCodeCommandHandler extends CommandHandler<UpdateResidenceCodeCommand> {
	@Inject
	PersonResiTaxRepository personResiTaxRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateResidenceCodeCommand> context) {
		UpdateResidenceCodeCommand updateResidence = context.getCommand();
		String companyCode = "";
		if (AppContexts.user() != null) {
			companyCode = AppContexts.user().companyCode();
			personResiTaxRepository.updateResendence(companyCode, updateResidence.getResidenceCode(),
					updateResidence.getPersonId(), updateResidence.getYearKey());
		} else {

			return;
		}
	}

}
