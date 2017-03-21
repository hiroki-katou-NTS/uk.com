package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * DeleteResidentalTaxCommandHandler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class DeleteResidentialTaxCommandHandler extends CommandHandler<DeleteResidentialTaxCommand>{
	@Inject
	private ResidentialTaxRepository resiTaxRepository;
	@Override
	protected void handle(CommandHandlerContext<DeleteResidentialTaxCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		DeleteResidentialTaxCommand delete = context.getCommand();
		this.resiTaxRepository.delele(companyCode, delete.getResiTaxCode());
	}

}
