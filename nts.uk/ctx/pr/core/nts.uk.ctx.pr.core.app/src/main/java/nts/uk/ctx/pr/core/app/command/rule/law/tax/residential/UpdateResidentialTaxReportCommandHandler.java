package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.input.PersonResiTaxRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UpdateResidentialTaxReportCommandHandler
 * 
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class UpdateResidentialTaxReportCommandHandler extends CommandHandler<UpdateResidentialTaxReportCommand> {
	@Inject
	private ResidentialTaxRepository resiTaxRepository;
	@Inject
	private PersonResiTaxRepository personTaxRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateResidentialTaxReportCommand> context) {
		UpdateResidentialTaxReportCommand update = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		List<String> lstResiTax = update.getResiTaxCodes();
		lstResiTax.stream().forEach(c -> {
			List<?> lstResidential = this.resiTaxRepository.getAllResidentialTaxCode(companyCode, c.toString());
			lstResidential.stream().forEach(b -> {
				this.resiTaxRepository.update(companyCode, c.toString(), update.getResiTaxReportCode());
			});
			List<?> lstPerson = this.personTaxRepository.findByResidenceCode(companyCode, c, update.getYearKey());
			lstPerson.stream().forEach(personId -> {
				this.personTaxRepository.updateResendence(companyCode, update.getResiTaxReportCode(), personId.toString(),
						update.getYearKey());
			});
		});
	}
}